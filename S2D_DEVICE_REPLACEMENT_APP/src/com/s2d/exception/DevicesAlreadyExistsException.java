package com.s2d.exception;

@SuppressWarnings("serial")
public class DevicesAlreadyExistsException extends Exception{

	public DevicesAlreadyExistsException(String message) {
		super(message);
	}
}
