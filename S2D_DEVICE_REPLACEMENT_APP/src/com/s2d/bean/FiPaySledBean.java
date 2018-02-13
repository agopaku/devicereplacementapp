package com.s2d.bean;

public class FiPaySledBean {

	private int id;
	private String deviceName;
	private int basePort;
	private int terminalPort;
	private int offset;
	private String hostAddress;
	private Boolean initDebit;
	
	public FiPaySledBean() {
		
	}
	
	public FiPaySledBean(int id, String deviceName, int basePort, int terminalPort, int offset, String hostAddress, Boolean initDebit) {
		this.id = id;
		this.deviceName = deviceName;
		this.basePort = basePort;
		this.terminalPort = terminalPort;
		this.offset = offset;
		this.hostAddress = hostAddress;
		this.initDebit = initDebit;
	}
	
	public FiPaySledBean(FiPaySledBean copyObj) {
		this.id = copyObj.id;
		this.deviceName = copyObj.deviceName;
		this.basePort = copyObj.basePort;
		this.terminalPort = copyObj.terminalPort;
		this.offset = copyObj.offset;
		this.hostAddress = copyObj.hostAddress;
		this.initDebit = copyObj.initDebit;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public int getBasePort() {
		return basePort;
	}
	public void setBasePort(int basePort) {
		this.basePort = basePort;
	}
	public int getTerminalPort() {
		return terminalPort;
	}
	public void setTerminalPort(int terminalPort) {
		this.terminalPort = terminalPort;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getHostAddress() {
		return hostAddress;
	}
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}
	public Boolean getInitDebit() {
		return initDebit;
	}
	public void setInitDebit(Boolean initDebit) {
		this.initDebit = initDebit;
	}
	
	public void printDeviceDetails() {
		System.out.println(""+this.id+"\t"+this.deviceName+"\t"+this.basePort+"\t"+this.terminalPort
				+"\t"+this.offset+"\t"+this.hostAddress+"\t"+this.initDebit);
	}
	
	public String toString() { 
        return this.deviceName;
     } 
}
