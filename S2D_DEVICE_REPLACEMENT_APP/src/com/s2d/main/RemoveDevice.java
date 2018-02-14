package com.s2d.main;
import com.s2d.manager.FiPayDatabaseManger;

public class RemoveDevice {
	public static void main(String [] args) {
		
		String deviceName = args[0];
		
		FiPayDatabaseManger manager = new FiPayDatabaseManger();
		manager.removeDevice(deviceName);
	}
}
