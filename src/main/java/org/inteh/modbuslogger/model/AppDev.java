package org.inteh.sbsecurity.model;

public class AppDev {

	private short devAddr; // ModBus address
	private String devType; // String equivalent of device type
	private String serial; // S/n
	private String comment; // Comment

	public AppDev() {

	}

	public AppDev(short devAddr, String devName, String serial, String comment) {
		this.devAddr = devAddr;
		this.devType = devName;
		this.serial = serial;
		this.comment = comment;
	}

	public short getDevAddr() {
		return devAddr;
	}

	public void setDevAddr(short devNo) {
		this.devAddr = devNo;
	}

	public String getDevType() {
		return devType;
	}

	public void setDevType(String devName) {
		this.devType = devName;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}