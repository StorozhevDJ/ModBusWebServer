package org.inteh.sbsecurity.model;

import java.time.LocalDateTime;

public class MLPData {
	//private DeviceInfo info = new DeviceInfo();
	private LocalDateTime dateTime = LocalDateTime.now();
	private int angleAX = 0, angleAY = 0, angleAZ = 0;
	private int accel = 0;
	private int angleMX = 0, angleMY = 0, angleMZ = 0;
	private int mag = 0;
	private int tempCase = 0;
	private int tempAccel = 0;
	private String[] state = null;
	private String[] test = null;
	//private int fwCRC = 0;

	public MLPData() {

	}

	/*public DeviceInfo getInfo() {
		return info;
	}

	public void setInfo(DeviceInfo info) {
		this.info = info;
	}*/

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public int getAngleAX() {
		return angleAX;
	}
	
	public float getAngleAXDeg() {
		return (float) Math.toDegrees((float)this.angleAX/10000)-90;
	}

	public void setAngleAX(int angleAX) {
		this.angleAX = angleAX;
	}

	public int getAngleAY() {
		return angleAY;
	}
	
	public float getAngleAYDeg() {
		return (float) Math.toDegrees((float)this.angleAY/10000)-90;
	}

	public void setAngleAY(int angleAY) {
		this.angleAY = angleAY;
	}

	public int getAngleAZ() {
		return angleAZ;
	}
	
	public float getAngleAZDeg() {
		return (float) Math.toDegrees((float)this.angleAZ/10000)-90;
	}

	public void setAngleAZ(int angleAZ) {
		this.angleAZ = angleAZ;
	}

	public int getAccel() {
		return accel;
	}

	public void setAccel(int accel) {
		this.accel = accel;
	}

	public int getAngleMX() {
		return angleMX;
	}

	public void setAngleMX(int angleMX) {
		this.angleMX = angleMX;
	}

	public int getAngleMY() {
		return angleMY;
	}

	public void setAngleMY(int angleMY) {
		this.angleMY = angleMY;
	}

	public int getAngleMZ() {
		return angleMZ;
	}

	public void setAngleMZ(int angleMZ) {
		this.angleMZ = angleMZ;
	}

	public int getMag() {
		return mag;
	}

	public void setMag(int mag) {
		this.mag = mag;
	}

	public int getTempCase() {
		return tempCase;
	}

	public void setTempCase(int tempCase) {
		this.tempCase = tempCase;
	}

	public int getTempAccel() {
		return tempAccel;
	}

	public void setTempAccel(int tempAccel) {
		this.tempAccel = tempAccel;
	}

	public String[] getState() {
		return state;
	}

	public void setState(String[] state) {
		this.state = state;
	}

	public String[] getTest() {
		return test;
	}

	public void setTest(String[] test) {
		this.test = test;
	}

	/*public int getFwCRC() {
		return fwCRC;
	}

	public void setFwCRC(int fwCRC) {
		this.fwCRC = fwCRC;
	}*/


}
