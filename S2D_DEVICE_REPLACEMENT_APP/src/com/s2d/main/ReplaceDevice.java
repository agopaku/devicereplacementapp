package com.s2d.main;
import com.s2d.manager.FiPayDatabaseManger;


public class ReplaceDevice {
	public static void main(String [] args) {
			
		String actualDevice = args[0];
		String replacementDevice = args[1];
		
		FiPayDatabaseManger manager = new FiPayDatabaseManger();
		manager.replaceDevice(actualDevice, replacementDevice);
	}
}
