package org.inteh.modbuslogger.modbus.mapper;

import java.io.UnsupportedEncodingException;

import org.inteh.modbuslogger.database.model.DbDeviceModel;
import org.inteh.modbuslogger.modbus.model.ModbusDeviceInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ghgande.j2mod.modbus.procimg.Register;



public class ModbusMapDeviceInfoMapper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ModbusMapDeviceInfoMapper.class);

	public static enum modBusRegMap {
		// The first register starts at address 0
		// ОПИСАНИЕ ПРИБОРА
		DEV_ID, // ro Идентификационный код прибора (MLP = 150)
		HW_Version, // ro Модификация прибора
		DEV_Serial, // ro Серийный номер прибора
		MFG_Year, // ro Год выпуска прибора
		FW_Version, // ro Версия прошивки прибора (Firmware Version = REG/10 )
		MinVoltage, // ro Минимальное напряжение питания UMIN = REG/100, V (default=550)
		MaxVoltage, // ro Максимальное напряжение питания UMAX = REG/100, V (default=2400)
		Current, // ro Ток потребления INOM = REG/100, mA (default=300)
		Baudrate, // ro Поддерживаемые скорости обмена
		SubCounter, // r/w+e В прибор с SubAddress=0 записывается количество приборов с SubAddress≠0
					// на этом адресе MODBUS
		SubAddress, // r/w+e MSB: SubAddress сравнивается с полем RegAddrHi (в командах 3 и 16) и
					// SubFuncHi (в команде 8) LSB: 1…255 (0=broadcast, игнорируется)
		DataFormat, // r r/w+e
		// r/w+e Комментарии к прибору: 32 символа ASCII
		Comment0001, Comment0203, Comment0405, Comment0607, Comment0809, Comment1011, Comment1213, Comment1415,
		Comment1617, Comment1819, Comment2021, Comment2223, Comment2425, Comment2627, Comment2829, Comment3031,
		
		HOLDING_REGS_SIZE	// leave this one
	}

	// Dev Info ModBus Data
	private short id;
	private short type;
	private short hw_ver;
	private short dev_serial;
	private short mfg_year;
	private short fw_ver;
	private short min_voltage;
	private short max_voltage;
	private short current;
	private short baudrate;
	private short subCounter;
	private short subAddress;
	private short address;
	
	private short dataFormat;

	class DataFormat {
		byte baudIndex;
		int baudrate;
		boolean parity;
		boolean parity_mode;
		boolean flowCtrl;
		boolean flowCtrlMode;
		byte stopBits;
		byte protocol;

		public DataFormat() {

		}
	};

	// private DataFormat dataFormat = new DataFormat();
	private String comment;
	
	
	/**********************************************************************************************************
	 * Parse received from ModBus (from register map to variable) device info data
	 * (serial, year, comment etc.)
	 * 
	 * @param slaveResponse - ModBus register array
	 */
	public ModbusMapDeviceInfoMapper(Register[] slaveResponse) {
		// setId(this.id);
		setType(slaveResponse[modBusRegMap.DEV_ID.ordinal()].toShort()); // ro Идентификационный код прибора (default
																			// 150)
		setHw_ver(slaveResponse[modBusRegMap.HW_Version.ordinal()].toShort()); // ro Модификация прибора
		setDev_serial(slaveResponse[modBusRegMap.DEV_Serial.ordinal()].toShort());// ro Серийный номер прибора
		setMfg_year(slaveResponse[modBusRegMap.MFG_Year.ordinal()].toShort()); // ro Год выпуска прибора
		setFw_ver(slaveResponse[modBusRegMap.FW_Version.ordinal()].toShort()); // ro Версия прошивки прибора (Firmware
																				// Version = REG/10 )
		setMin_voltage(slaveResponse[modBusRegMap.MinVoltage.ordinal()].toShort());// ro Минимальное напряжение питания
																					// UMIN = REG/100, V (default=550)
		setMax_voltage(slaveResponse[modBusRegMap.MaxVoltage.ordinal()].toShort());// ro Максимальное напряжение питания
																					// UMAX = REG/100, V (default=2400)
		setCurrent(slaveResponse[modBusRegMap.Current.ordinal()].toShort()); // ro Ток потребления INOM = REG/100, mA
																			// (default=300)
		setBaudrate(slaveResponse[modBusRegMap.Baudrate.ordinal()].toShort()); // ro Поддерживаемые скорости обмена
		setSubCounter(slaveResponse[modBusRegMap.SubCounter.ordinal()].toShort()); // r/w+e В прибор с SubAddress=0
																					// записывается количество приборов
																					// с SubAddress≠0 на этом адресе
																					// MODBUS
		setSubAddress(slaveResponse[modBusRegMap.SubAddress.ordinal()].toShort());// r/w+e MSB: SubAddress сравнивается
																					// с полем RegAddrHi (в командах 3 и
																					// 16) и SubFuncHi (в команде 8)
																					// LSB: 1…255 (0=broadcast,
																					// игнорируется)
		setDataFormat(slaveResponse[modBusRegMap.DataFormat.ordinal()].toShort()); //
		// DataFormat, //r r/w+e Настройки
		// baudrate, parity etc.
		setComment(slaveResponse); // r/w+e Комментарии к прибору: 32 символа ASCII
	}
	

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public short getHw_ver() {
		return hw_ver;
	}

	public void setHw_ver(short hw_ver) {
		this.hw_ver = hw_ver;
	}

	public short getDev_serial() {
		return dev_serial;
	}

	public void setDev_serial(short dev_serial) {
		this.dev_serial = dev_serial;
	}

	public short getMfg_year() {
		return mfg_year;
	}

	public void setMfg_year(short mfg_year) {
		this.mfg_year = mfg_year;
	}

	public short getFw_ver() {
		return fw_ver;
	}

	public void setFw_ver(short fw_ver) {
		this.fw_ver = fw_ver;
	}

	public short getMin_voltage() {
		return min_voltage;
	}

	public void setMin_voltage(short min_voltage) {
		this.min_voltage = min_voltage;
	}

	public short getMax_voltage() {
		return max_voltage;
	}

	public void setMax_voltage(short max_voltage) {
		this.max_voltage = max_voltage;
	}

	public short getCurrent() {
		return current;
	}

	public void setCurrent(short current) {
		this.current = current;
	}

	public short getBaudrate() {
		return baudrate;
	}

	public void setBaudrate(short baudrate) {
		this.baudrate = baudrate;
	}

	public short getSubCounter() {
		return subCounter;
	}

	public void setSubCounter(short subCounter) {
		this.subCounter = subCounter;
	}

	public short getSubAddress() {
		return subAddress;
	}

	public void setSubAddress(short subAddress) {
		this.subAddress = (short) (subAddress & 0xFF);
	}

	public short getAddress() {
		return address;
	}

	public void setAddress(short address) {
		this.address = (short) ((subAddress >> 8) <= 247 ? (subAddress >> 8) : 247);
	}

	public short getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(short dateformat) {
		this.dataFormat = dateformat;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setComment(Register[] reg) {
		byte[] strData = new byte[32];
		for (byte i = 0; i < 16; i++) {
			strData[(i * 2) + 0] = (byte) ((reg[modBusRegMap.Comment0001.ordinal() + i].toShort()) >> 8);
			strData[(i * 2) + 1] = (byte) ((reg[modBusRegMap.Comment0001.ordinal() + i].toShort()) & 0xff);
		}
		try {
			this.comment = new String(strData, "Cp1251").trim();
		} catch (UnsupportedEncodingException e) {
			LOGGER.warn(e.getMessage());
		}
	}
	
	
	
	public static short getDeviceInfoMapSize() {
		return (short) modBusRegMap.HOLDING_REGS_SIZE.ordinal();
	}

	
	
	public ModbusDeviceInfoModel mapDeviceInfo() {
		String typeStr = new String();
		switch (getType()) {
		case 103:
			typeStr = "СКЦД-1/200";
			break;
		case 105:
			typeStr = "СКЦД-2/200";
			break;
		case 106:
			typeStr = "СКЦД-4/200";
			break;
		case 107:
			typeStr = "СКЦД-6/200";
			break;
		case 108:
			typeStr = "CKT-4-R";
			break;
		case 150:
			typeStr = "МЛП";
			break;
		case 151:
			typeStr = "MЛП";
			break;
		default:
			typeStr = "Неизвестный тип (" + String.valueOf(getType()) + ")";
		}
		
		ModbusDeviceInfoModel dev = new ModbusDeviceInfoModel(
				0,
				getSubAddress(),
				typeStr,
				getDev_serial(), 
				getHw_ver(),
				getFw_ver(),
				getMfg_year(),
				getMin_voltage(),
				getMax_voltage(),
				getCurrent(),
				getComment());
		return dev;
	}
	
	public DbDeviceModel getDeviceDbModel() {
		return new DbDeviceModel(
				getSubAddress(),
				getType(),
				getDev_serial(),
				getBaudrate(),
				getHw_ver(),
				getFw_ver(),
				getMfg_year(),
				getMin_voltage(),
				getMax_voltage(),
				getCurrent(),
				getComment(),
				true);
	}

}
