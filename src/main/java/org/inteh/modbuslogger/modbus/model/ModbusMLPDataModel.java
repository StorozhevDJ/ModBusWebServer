package org.inteh.modbuslogger.modbus.model;

import java.time.LocalDateTime;

public class ModbusMLPDataModel {
	private int angleAX = 0, angleAY = 0, angleAZ = 0;
	private int accel = 0;
	private int angleMX = 0, angleMY = 0, angleMZ = 0;
	private int mag = 0;
	private int tempCase = 0;
	private int tempAccel = 0;
	private int state = 0;
	private int test = 0;



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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getTest() {
		return test;
	}

	public void setTest(int test) {
		this.test = test;
	}

	/*public int getFwCRC() {
		return fwCRC;
	}

	public void setFwCRC(int fwCRC) {
		this.fwCRC = fwCRC;
	}*/


}
