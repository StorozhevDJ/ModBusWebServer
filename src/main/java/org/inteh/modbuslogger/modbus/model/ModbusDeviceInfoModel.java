package org.inteh.modbuslogger.modbus.model;

public class ModbusDeviceInfoModel {

	private int devId;
	private int address;
	private String type;
	private int serial;
	private int hwVersion;
	private int fwVersion;
	private int mfgYear;
	private int voltageMin;
	private int voltageMax;
	private int current;
	private String comment;
	//private boolean enabled;

	/*public DeviceInfoModbus() {

	}*/

	public ModbusDeviceInfoModel(int devId, int address, String type, int serial, int hwVer, int fwVer, int mfgYear, 
			int voltageMin, int voltageMax, int current, String comment/*, boolean enabled*/) {
		setDevId(devId);
		setAddress(address);
		setType(type);
		setSerial(serial);
		setHwVersion(hwVer);
		setFwVersion(fwVer);
		setMfgYear(mfgYear);
		setVoltageMin(voltageMin);
		setVoltageMax(voltageMax);
		setCurrent(current);
		setComment(comment);
		//setEnabled(enabled);
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

	public int getHwVersion() {
		return hwVersion;
	}

	public void setHwVersion(int hwVersion) {
		this.hwVersion = hwVersion;
	}

	public int getFwVersion() {
		return fwVersion;
	}

	public void setFwVersion(int fwVersion) {
		this.fwVersion = fwVersion;
	}

	public int getMfgYear() {
		return mfgYear;
	}

	public void setMfgYear(int mfgYear) {
		this.mfgYear = mfgYear;
	}

	public int getVoltageMin() {
		return voltageMin;
	}

	public void setVoltageMin(int voltageMin) {
		this.voltageMin = voltageMin;
	}

	public int getVoltageMax() {
		return voltageMax;
	}

	public void setVoltageMax(int voltageMax) {
		this.voltageMax = voltageMax;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	/*public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}*/


}
