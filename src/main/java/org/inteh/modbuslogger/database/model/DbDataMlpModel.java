package org.inteh.modbuslogger.database.model;

import java.util.Date;

public class DbDataMlpModel {

	/**
	 * id для данных MLP в БД. Для несохраненной в БД даных это поле имеет значение 0, после
	 * сохранения значение присваивается БД
	 */
	private int id = 0;
	
	/**
	 * id устройства MLP в БД.
	 */
	private int devId;
	
	/**
	 * Date/time read from device data
	 */
	private Date date_time;
	
	/**
	 * Angle x, y, z
	 */
	private int anglex;
	private int angley;
	private int anglez;
	
	/**
	 * Acceleration
	 */
	private int accel;
	
	/**
	 * Magnetic Angle x, y, z
	 */
	private int anglemx;
	private int anglemy;
	private int anglemz;
	
	/**
	 * Magnetic vector
	 */
	private int mag;
	
	/**
	 * Case temperature 
	 */
	private int tempcase;
	
	/**
	 * Accelerometer temperature
	 */
	private int tempaccel;
	
	/**
	 * Device state flags
	 */
	private int state;
	
	/**
	 * Device test flags
	 */
	private int test;
	
	
	
	public DbDataMlpModel(int id, int devId, Date date_time, int anglex, int angley, int anglez, int accel,
			int anglemx, int anglemy, int anglemz, int mag, int tempcase, int tempaccel, int state, int test) {
		setId(id);
		setDevId(devId);
		setDate_time(date_time);
		setAnglex(anglex);
		setAngley(angley);
		setAnglez(anglez);
		setAccel(accel);
		setAnglemx(anglemx);
		setAnglemy(anglemy);
		setAnglemz(anglemz);
		setMag(mag);
		setTempcase(tempcase);
		setTempaccel(tempaccel);
		setState(state);
		setTest(test);
	}
	
	public DbDataMlpModel(int devId, Date date_time, int anglex, int angley, int anglez, int accel,
			int anglemx, int anglemy, int anglemz, int mag, int tempcase, int tempaccel, int state, int test) {
		this(0, devId, date_time, anglex, angley, anglez, accel, anglemx, anglemy, anglemz, mag, tempcase, tempaccel, state, test);
	}
	
	public DbDataMlpModel(int anglex, int angley, int anglez, int accel,
			int anglemx, int anglemy, int anglemz, int mag, int tempcase, int tempaccel, int state, int test) {
		this(0, 0, null, anglex, angley, anglez, accel, anglemx, anglemy, anglemz, mag, tempcase, tempaccel, state, test);
	}
	
	public DbDataMlpModel() {
		this(0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDevId() {
		return devId;
	}

	public void setDevId(int devId) {
		this.devId = devId;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public int getAnglex() {
		return anglex;
	}

	public void setAnglex(int anglex) {
		this.anglex = anglex;
	}

	public int getAngley() {
		return angley;
	}

	public void setAngley(int angley) {
		this.angley = angley;
	}

	public int getAnglez() {
		return anglez;
	}

	public void setAnglez(int anglez) {
		this.anglez = anglez;
	}

	public int getAccel() {
		return accel;
	}

	public void setAccel(int accel) {
		this.accel = accel;
	}

	public int getAnglemx() {
		return anglemx;
	}

	public void setAnglemx(int anglemx) {
		this.anglemx = anglemx;
	}

	public int getAnglemy() {
		return anglemy;
	}

	public void setAnglemy(int anglemy) {
		this.anglemy = anglemy;
	}

	public int getAnglemz() {
		return anglemz;
	}

	public void setAnglemz(int anglemz) {
		this.anglemz = anglemz;
	}

	public int getMag() {
		return mag;
	}

	public void setMag(int mag) {
		this.mag = mag;
	}

	public int getTempcase() {
		return tempcase;
	}

	public void setTempcase(int tempcase) {
		this.tempcase = tempcase;
	}

	public int getTempaccel() {
		return tempaccel;
	}

	public void setTempaccel(int tempaccel) {
		this.tempaccel = tempaccel;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accel;
		result = prime * result + anglemx;
		result = prime * result + anglemy;
		result = prime * result + anglemz;
		result = prime * result + anglex;
		result = prime * result + angley;
		result = prime * result + anglez;
		result = prime * result + ((date_time == null) ? 0 : date_time.hashCode());
		result = prime * result + devId;
		result = prime * result + id;
		result = prime * result + mag;
		result = prime * result + state;
		result = prime * result + tempaccel;
		result = prime * result + tempcase;
		result = prime * result + test;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof DbDataMlpModel))
			return false;
		DbDataMlpModel other = (DbDataMlpModel) obj;
		if (accel != other.accel)
			return false;
		if (anglemx != other.anglemx)
			return false;
		if (anglemy != other.anglemy)
			return false;
		if (anglemz != other.anglemz)
			return false;
		if (anglex != other.anglex)
			return false;
		if (angley != other.angley)
			return false;
		if (anglez != other.anglez)
			return false;
		if (date_time == null) {
			if (other.date_time != null)
				return false;
		} else if (!date_time.equals(other.date_time))
			return false;
		if (devId != other.devId)
			return false;
		if (id != other.id)
			return false;
		if (mag != other.mag)
			return false;
		if (state != other.state)
			return false;
		if (tempaccel != other.tempaccel)
			return false;
		if (tempcase != other.tempcase)
			return false;
		if (test != other.test)
			return false;
		return true;
	}
	
	
}
