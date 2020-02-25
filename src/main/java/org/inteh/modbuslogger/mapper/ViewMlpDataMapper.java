package org.inteh.modbuslogger.mapper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.inteh.modbuslogger.database.model.DbDataMlpModel;

public class ViewMlpDataMapper {

	/**
	 * id устройства MLP в БД.
	 */
	private int devId;
	/**
	 * Date/time read data
	 */
	private Date date;
	/**
	 * Angle x, y, z in deg
	 */
	private int ax;
	private int ay;
	private int az;
	
	/**
	 * Acceleration
	 */
	private int accel;
	/**
	 * Case temperature 
	 */
	private int tempCase;
	
	
	
	public ViewMlpDataMapper(int devId, Date date, int ax, int ay, int az, int accel, int tempcase) {
		setDevId(devId);
		setDate(date);
		setAx(ax);
		setAy(ay);
		setAz(az);
		setAccel(accel);
		setTempCase(tempcase);
	}
	
	public ViewMlpDataMapper(DbDataMlpModel dbDataMlpModel) {
		setDevId(dbDataMlpModel.getDevId());
		setDate(dbDataMlpModel.getDate_time());
		setAx(dbDataMlpModel.getAnglex());
		setAy(dbDataMlpModel.getAngley());
		setAz(dbDataMlpModel.getAnglez());
		setAccel(dbDataMlpModel.getAccel());
		setTempCase(dbDataMlpModel.getTempcase());
	}
	
	
	
	public int getDevId() {
		return devId;
	}
	public void setDevId(int devId) {
		this.devId = devId;
	}
	public String getDate() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format( date );
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAx() {
		return new DecimalFormat ("0.###").format (Math.toDegrees((float)this.ax/10000)-90);
	}
	public void setAx(int ax) {
		this.ax = ax;
	}
	public String getAy() {
		return new DecimalFormat ("0.###").format (Math.toDegrees((float)this.ay/10000)-90);
	}
	public void setAy(int ay) {
		this.ay = ay;
	}
	public String getAz() {
		return new DecimalFormat ("0.###").format (Math.toDegrees((float)this.az/10000)-90);
	}
	public void setAz(int az) {
		this.az = az;
	}
	public int getAccel() {
		return accel;
	}
	public void setAccel(int accel) {
		this.accel = accel;
	}
	public int getTempCase() {
		return tempCase;
	}
	public void setTempCase(int tempcase) {
		this.tempCase = tempcase;
	}
	
	
}
