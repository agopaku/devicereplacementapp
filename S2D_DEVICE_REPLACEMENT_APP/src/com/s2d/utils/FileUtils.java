package com.s2d.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	private static final String DELETED_DEVICE_INFO_FILE = "DeletedDeviceInfo.txt";
	
	public static void addDeviceIDToDeletedDeviceInfo(int deviceId) throws IOException {

		System.out.println("Adding ID: "+deviceId+" to Deleted Info File");
		BufferedWriter writer = new BufferedWriter(new FileWriter(DELETED_DEVICE_INFO_FILE, true));
		writer.write(deviceId+"\n");
		writer.close();		
	}
}
