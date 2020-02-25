package org.inteh.modbuslogger.database.model;

public class DbDeviceModel {
	/**
	 * id для Device в БД. Для несохраненной в БД устройств это поле имеет значение
	 * 0, после сохранения значение присваивается БД
	 */
	private int id = 0;

	/**
	 * Адрес в сети ModBus
	 */
	private int address;

	/**
	 * Тип ModBus устройства (для МЛП = 150)
	 */
	private int type;

	/**
	 * Серйный номер устройства
	 */
	private int serial;

	/**
	 * Параметры baudrate в RS-485 сети
	 */
	private int baudrate;

	/**
	 * Device Hardvare version
	 */
	private int hw_ver;

	/**
	 * Device Firmware version
	 */
	private int fw_ver;

	/**
	 * Devise manufacture year
	 */
	private int mfg_year;

	/**
	 * Device Min voltage value
	 */
	private int min_voltage;

	/**
	 * Device Max voltage value
	 */
	private int max_voltage;

	/**
	 * Device Current consumption value
	 */
	private int current;

	/**
	 * Device comments string
	 */
	private String comment;

	/**
	 * Enabled flag
	 */
	private boolean enabled;

	public DbDeviceModel(int id, int address, int type, int serial, int baudrate, int hw_ver, int fw_ver, int mfg_year,
			int min_voltage, int max_voltage, int current, String comment, boolean enabled) {
		setId(id);
		setAddress(address);
		setType(type);
		setSerial(serial);
		setBaudrate(baudrate);
		setHw_ver(hw_ver);
		setFw_ver(fw_ver);
		setMfg_year(mfg_year);
		setMin_voltage(min_voltage);
		setMax_voltage(max_voltage);
		setCurrent(current);
		setComment(comment);
		setEnabled(enabled);
	}

	public DbDeviceModel(int address, int type, int serial, int baudrate, int hw_ver, int fw_ver, int mfg_year,
			int min_voltage, int max_voltage, int current, String comment, boolean enabled) {
		this(0, address, type, serial, baudrate, hw_ver, fw_ver, mfg_year, min_voltage, max_voltage, current, comment,
				enabled);
	}

	public DbDeviceModel() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public int getBaudrate() {
		return baudrate > 100 ? baudrate : 9600;
	}

	public void setBaudrate(int baudrate) {
		this.baudrate = baudrate;
	}

	public int getHw_ver() {
		return hw_ver;
	}

	public void setHw_ver(int hw_ver) {
		this.hw_ver = hw_ver;
	}

	public int getFw_ver() {
		return fw_ver;
	}

	public void setFw_ver(int fw_ver) {
		this.fw_ver = fw_ver;
	}

	public int getMfg_year() {
		return mfg_year;
	}

	public void setMfg_year(int mfg_year) {
		this.mfg_year = mfg_year;
	}

	public int getMin_voltage() {
		return min_voltage;
	}

	public void setMin_voltage(int min_voltage) {
		this.min_voltage = min_voltage;
	}

	public int getMax_voltage() {
		return max_voltage;
	}

	public void setMax_voltage(int max_voltage) {
		this.max_voltage = max_voltage;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + address;
		result = prime * result + baudrate;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + current;
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + fw_ver;
		result = prime * result + hw_ver;
		result = prime * result + id;
		result = prime * result + max_voltage;
		result = prime * result + mfg_year;
		result = prime * result + min_voltage;
		result = prime * result + serial;
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof DbDeviceModel))
			return false;
		DbDeviceModel other = (DbDeviceModel) obj;
		if (address != other.address)
			return false;
		if (baudrate != other.baudrate)
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (current != other.current)
			return false;
		if (enabled != other.enabled)
			return false;
		if (fw_ver != other.fw_ver)
			return false;
		if (hw_ver != other.hw_ver)
			return false;
		if (id != other.id)
			return false;
		if (max_voltage != other.max_voltage)
			return false;
		if (mfg_year != other.mfg_year)
			return false;
		if (min_voltage != other.min_voltage)
			return false;
		if (serial != other.serial)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
