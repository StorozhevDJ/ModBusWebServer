package org.inteh.sbsecurity.model;

public class DeviceInfo {

	private int devId;
	private int address;
	private String type;
	private int serial;
	private String comment;

	public DeviceInfo() {

	}

	public DeviceInfo(int devId, int address, String type, int serial, String comment) {
		this.devId = devId;
		this.address = address;
		this.type = type;
		this.serial = serial;
		this.comment = comment;
	}
	
	public int getDevId() {
		return devId;
	}

	public void setDevId(int devId) {
		this.devId = devId;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


}
