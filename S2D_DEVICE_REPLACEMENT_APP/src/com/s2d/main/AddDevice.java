package com.s2d.main;
import com.s2d.manager.FiPayDatabaseManger;


public class AddDevice {
	public static void main(String [] args) {
		
		String deviceName = args[0];
		String hostAddress = args[1];
		
		FiPayDatabaseManger manager = new FiPayDatabaseManger();
		manager.addNewDevice(deviceName, hostAddress);
	}
}
