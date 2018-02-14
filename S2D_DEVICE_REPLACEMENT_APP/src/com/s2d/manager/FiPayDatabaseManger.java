package com.s2d.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.s2d.bean.FiPaySledBean;
import com.s2d.exception.DevicesAlreadyExistsException;
import com.s2d.exception.EnoughDevicesFoundException;
import com.s2d.exception.NoDeviceFoundException;
import com.s2d.service.FiPayDatabaseConnect;

public class FiPayDatabaseManger {

	private ArrayList<FiPaySledBean> getDevicesForHost(ArrayList<FiPaySledBean> listOfDevices, String hostAddress) {

		ArrayList<FiPaySledBean> devicesOfHost = new ArrayList<FiPaySledBean>();
		for(FiPaySledBean deviceObj : listOfDevices) {
			if(deviceObj.getHostAddress().equalsIgnoreCase(hostAddress))
				devicesOfHost.add(deviceObj);
		}
		return devicesOfHost;
	}
	
	private ArrayList<FiPaySledBean> getDevicesForStore(ArrayList<FiPaySledBean> listOfDevices, String actualDevice) {

		ArrayList<FiPaySledBean> devicesOfHost = new ArrayList<FiPaySledBean>();
		for(FiPaySledBean deviceObj : listOfDevices) {
			if(deviceObj.getDeviceName().contains(actualDevice.substring(0, 8)))
				devicesOfHost.add(deviceObj);
		}
		return devicesOfHost;
	}
	
	private int findAvailableId(ArrayList<FiPaySledBean> listOfDevices) {

		Collections.sort(listOfDevices, new Comparator<FiPaySledBean>() {
			@Override
			public int compare(FiPaySledBean o1, FiPaySledBean o2) {
				return o1.getId() - o2.getId();
			}
		});
		
		int missingId = 1;
		for(FiPaySledBean obj : listOfDevices) {
			if(missingId != obj.getId()) break;
			missingId++;
		}
		return missingId;
	}

	private FiPaySledBean createReplacementDeviceBean(FiPaySledBean actualDeviceBean, String replacementDevice) {

		FiPaySledBean replacementDeviceBean = new FiPaySledBean(actualDeviceBean);
		replacementDeviceBean.setDeviceName(replacementDevice);
		replacementDeviceBean.setInitDebit(true);

		return replacementDeviceBean;
	}
	
	private FiPaySledBean createNewDeviceBean(int deviceId, String deviceName, String hostName) {

		return new FiPaySledBean(deviceId, deviceName, 24900, (24900 + deviceId), deviceId, hostName, true);
	}

	public void replaceDevice(String actualDevice, String replacementDevice) {
		ArrayList<FiPaySledBean> listOfDevices = new ArrayList<>();

		listOfDevices = FiPayDatabaseConnect.getDeviceList();
		Map<String, FiPaySledBean> deviceMap = listOfDevices.stream().collect(Collectors.toMap(FiPaySledBean::getDeviceName, Function.identity()));

		FiPaySledBean actualDeviceBean = deviceMap.get(actualDevice);

		try {
			if(null == actualDeviceBean)			
				throw new NoDeviceFoundException("Device "+actualDevice+" Not Found");
			if(getDevicesForStore(listOfDevices, actualDevice).size() > 3)
				throw new EnoughDevicesFoundException("Enough devices found for store "+actualDevice.substring(2, 6));
				
			FiPaySledBean replacementDeviceBean = createReplacementDeviceBean(actualDeviceBean, replacementDevice);
			FiPayDatabaseConnect.updateDeviceInfo(actualDeviceBean, replacementDeviceBean);
		}
		catch (NoDeviceFoundException e) {
			e.printStackTrace();
		} catch (EnoughDevicesFoundException e) {
			FiPayDatabaseConnect.removeDeviceInfo(actualDeviceBean);
			e.printStackTrace();
		}
	}	
	
	public void addNewDevice(String deviceName, String hostAddress) {
		ArrayList<FiPaySledBean> listOfDevices = new ArrayList<>();

		listOfDevices = FiPayDatabaseConnect.getDeviceList();
		Map<String, FiPaySledBean> deviceMap = listOfDevices.stream().collect(Collectors.toMap(FiPaySledBean::getDeviceName, Function.identity()));

		try {
			if(null != deviceMap.get(deviceName))			
				throw new DevicesAlreadyExistsException("Device already exists for store "+deviceName.substring(2, 6));
			if(getDevicesForStore(listOfDevices, deviceName).size() > 3)
				throw new EnoughDevicesFoundException("Enough devices found for store "+deviceName.substring(2, 6));
				
			FiPaySledBean newDeviceBean = createNewDeviceBean(findAvailableId(getDevicesForHost(listOfDevices, hostAddress)), deviceName, hostAddress);
			FiPayDatabaseConnect.insertDeviceInfo(newDeviceBean);
		}
		catch (DevicesAlreadyExistsException e) {
			e.printStackTrace();
		} catch (EnoughDevicesFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void removeDevice(String deviceName) {
		ArrayList<FiPaySledBean> listOfDevices = new ArrayList<>();

		listOfDevices = FiPayDatabaseConnect.getDeviceList();
		Map<String, FiPaySledBean> deviceMap = listOfDevices.stream().collect(Collectors.toMap(FiPaySledBean::getDeviceName, Function.identity()));

		FiPaySledBean deviceToBeRemovedBean = deviceMap.get(deviceName);
		try {
			if(null == deviceToBeRemovedBean)			
				throw new NoDeviceFoundException("Device "+deviceName+" Not Found");
				
			FiPayDatabaseConnect.removeDeviceInfo(deviceToBeRemovedBean);
		}
		catch (NoDeviceFoundException e) {
			e.printStackTrace();
		}
	}	
}
