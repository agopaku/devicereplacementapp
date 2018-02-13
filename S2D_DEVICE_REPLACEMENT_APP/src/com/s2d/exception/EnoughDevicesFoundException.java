package com.s2d.exception;

@SuppressWarnings("serial")
public class EnoughDevicesFoundException extends Exception{

	public EnoughDevicesFoundException(String message) {
		super(message);
	}
}
