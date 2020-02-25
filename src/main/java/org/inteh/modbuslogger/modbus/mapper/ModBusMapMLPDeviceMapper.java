package org.inteh.modbuslogger.modbus.mapper;

import java.time.LocalDateTime;

import org.inteh.modbuslogger.database.model.DbDataMlpModel;
import org.inteh.modbuslogger.modbus.model.ModbusMLPDataModel;

import com.ghgande.j2mod.modbus.procimg.Register;

public class ModBusMapMLPDeviceMapper {

	public static enum modBusSettingRegMap {
		// НАСТРОЙКИ ПРИБОРА
		AngleMark_AX, AngleMark_AY, AngleMark_AZ, // r/w+e Метка угла между вектором A и осью X
		ATolerance, // r/w+e Допуск на угол отклонения вектора A от метки по каждой из осей
		AngleMark_MX, AngleMark_MY, AngleMark_MZ, // r/w+e Метка угла между вектором M и осью X
		MTolerance, // r/w+e Допуск на угол отклонения вектора M от метки по каждой из осей
		SettingsBit, //
		Command, // r/w+e Регистр команд
		
		HOLDING_REGS_SIZE //
	}

	public static enum modBusDataRegMap {
		// RTC
		RTCYear, RTCMonth, RTCDate, RTCDayWeek, RTCHour, RTCMinut, RTCSecund, // r/w

		// ДИНАМИЧЕСКИЕ ПЕРЕМЕННЫЕ
		Angle_AX, Angle_AY, Angle_AZ, // ro Угол между вектором A и осью X (^AX = REG/10000, 0…pi, rad = REG/174.533,
										// 0…180, ° )
		Accel, // ro Модуль вектора ускорения |A| = REG/10000, 0…2, g
		Angle_MX, Angle_MY, Angle_MZ, // ro Угол между вектором M и осью X (^AX = REG/10000, 0…pi, rad = REG/174.533,
										// 0…180, ° )
		Mag, // ro Модуль вектора магнитного поля (|M| = REG/10000, 0…2, gauss)
		TempCase, // ro Температура корпуса (TA = (signed)REG/100, -55…+150, °C)
		TempAccel, // ro Accelerometer Temperature

		State, // ro Регистр состояния
		Test, // ro Регистр самодиагностики
		FWCRC, // ro Контрольная сумма прошивки

		HOLDING_REGS_SIZE //
	}

	public static short modbusMapOffsetSetting = (short) (ModbusMapDeviceInfoMapper.getDeviceInfoMapSize() + 22);
	public static short modbusMapOffsetData = (short) (modbusMapOffsetSetting+modBusSettingRegMap.HOLDING_REGS_SIZE.ordinal());
	
	// Device setting
	private short angleMark_AX;
	private short angleMark_AY;
	private short angleMark_AZ;
	private short aTolerance;

	class DeviceSettingBits {
		boolean kalman_enable;
		boolean nullDriftFilter_enable;

		public DeviceSettingBits() {
		}
	}

	private DeviceSettingBits deviceSettingBits;// = new DeviceSettingBits();

	// Data measure
	private short RTCYear, RTCMonth, RTCDay;
	private short RTCHour, RTCMinut, RTCSecund;

	private short angle_AX, angle_AY, angle_AZ;
	private short accel;
	private short angle_MX, angle_MY, angle_MZ;
	private short mag;
	private short tempCase;
	private short tempAccel;

	static class State {
		boolean lax, lay, laz; // превышен допуск на угол отклонения вектора A от метки
		boolean na; // метка A не установлена
		boolean lmx, lmy, lmz; // превышен допуск на угол отклонения вектора M от метки
		boolean nm; // метка M не установлена
		boolean lta; // температура корпуса вышла за допустимые пределы
		boolean nan; // Результат А не корректен
		boolean nmn; // Результат M не корректен
		boolean ntn; // Результат Т не корректен
		boolean nrtc; // часы не установлены

		public State() {
			/*
			 * lax=lay=laz=na=false; lmx=lmy=lmz=nm=false; lta=false; nan=nmn=ntn=false;
			 * nrtc=false;
			 */
		}
	}

	private short stateRegVal;

	// private State state = new State();

	static class Test {
		public enum resetFlag {
			PORF, // Power-on Reset Flag
			EXRF, // External Reset Flag
			BORF, // Brown-out Reset Flag
			WDRF, // Watchdog System Reset Flag
			BLF, // BootLoader flag
			UNDEFINED
		};

		resetFlag resetSource;
		boolean fcrc; // ошибка проверки контрольной суммы прошивки
		boolean ecrc; // ошибка проверки контрольной суммы EEPROM
		boolean gpio; // ошибка проверки выводов MCU
		boolean i2c; // ошибка проверки выводов шины I2C
		boolean adc; // сбой АЦП MCU
		boolean trc; // сбой калибровки fMCU
		boolean adt; // отказ модуля термометра
		boolean lsm; // отказ модуля акселерометра

		public Test() {
		}
	}

	private short stateTestVal;

	private short FWCRC;

	/**********************************************************************************************
	 * Parse received from ModBus (from register map to variable)measured data (AX,
	 * AY, date, etc.)
	 * 
	 * @param slaveResponse - ModBus register array
	 */
	public ModBusMapMLPDeviceMapper(Register[] slaveResponse) {
		setRTCYear(slaveResponse[modBusDataRegMap.RTCYear.ordinal()].toShort());
		setRTCMonth(slaveResponse[modBusDataRegMap.RTCMonth.ordinal()].toShort());
		setRTCDate(slaveResponse[modBusDataRegMap.RTCDate.ordinal()].toShort());
		setRTCHour(slaveResponse[modBusDataRegMap.RTCHour.ordinal()].toShort());
		setRTCMinut(slaveResponse[modBusDataRegMap.RTCMinut.ordinal()].toShort());
		setRTCSecund(slaveResponse[modBusDataRegMap.RTCSecund.ordinal()].toShort());

		setAngle_AX(slaveResponse[modBusDataRegMap.Angle_AX.ordinal()].toShort());
		setAngle_AY(slaveResponse[modBusDataRegMap.Angle_AY.ordinal()].toShort());
		setAngle_AZ(slaveResponse[modBusDataRegMap.Angle_AZ.ordinal()].toShort());
		setAccel(slaveResponse[modBusDataRegMap.Accel.ordinal()].toShort());

		setAngle_MX(slaveResponse[modBusDataRegMap.Angle_MX.ordinal()].toShort());
		setAngle_MY(slaveResponse[modBusDataRegMap.Angle_MY.ordinal()].toShort());
		setAngle_MZ(slaveResponse[modBusDataRegMap.Angle_MZ.ordinal()].toShort());

		setTempCase(slaveResponse[modBusDataRegMap.TempCase.ordinal()].toShort());
		setTempAccel(slaveResponse[modBusDataRegMap.TempAccel.ordinal()].toShort());

		setState(slaveResponse[modBusDataRegMap.State.ordinal()].toShort());
		setTest(slaveResponse[modBusDataRegMap.Test.ordinal()].toShort());

		setFWCRC(slaveResponse[modBusDataRegMap.FWCRC.ordinal()].toShort());
	}

	public short getAngleMark_AX() {
		return angleMark_AX;
	}

	public void setAngleMark_AX(short angleMark_AX) {
		this.angleMark_AX = angleMark_AX;
	}

	public short getAngleMark_AY() {
		return angleMark_AY;
	}

	public void setAngleMark_AY(short angleMark_AY) {
		this.angleMark_AY = angleMark_AY;
	}

	public short getAngleMark_AZ() {
		return angleMark_AZ;
	}

	public void setAngleMark_AZ(short angleMark_AZ) {
		this.angleMark_AZ = angleMark_AZ;
	}

	public short getaTolerance() {
		return aTolerance;
	}

	public void setaTolerance(short aTolerance) {
		this.aTolerance = aTolerance;
	}

	public DeviceSettingBits getDeviceSettingBits() {
		return deviceSettingBits;
	}

	public void setDeviceSettingBits(DeviceSettingBits deviceSettingBits) {
		this.deviceSettingBits = deviceSettingBits;
	}

	public LocalDateTime getRTCDate() {
		LocalDateTime date = LocalDateTime.of(this.RTCYear, this.RTCMonth, this.RTCDay, this.RTCHour, this.RTCMinut,
				this.RTCSecund);
		return date;
	}

	public short getRTCYear() {
		return this.RTCYear;
	}

	public void setRTCYear(short RTCYear) {
		if ((RTCYear > 2017) && (RTCYear < 2050))
			this.RTCYear = RTCYear;
	}

	public short getRTCMonth() {
		return this.RTCMonth;
	}

	public void setRTCMonth(short RTCMonth) {
		if ((RTCMonth > 0) && (RTCMonth < 13))
			this.RTCMonth = RTCMonth;
	}

	public short getRTCDay() {
		return this.RTCDay;
	}

	public void setRTCDate(short RTCDay) {
		if ((RTCDay > 0) && (RTCDay < 32))
			this.RTCDay = RTCDay;
	}

	public short getRTCHour() {
		return this.RTCHour;
	}

	public void setRTCHour(short RTCHour) {
		if ((RTCHour >= 0) && (RTCHour < 24))
			this.RTCHour = RTCHour;
	}

	public short getRTCMinut() {
		return this.RTCMinut;
	}

	public void setRTCMinut(short RTCMinut) {
		if ((RTCMinut >= 0) && (RTCMinut < 60))
			this.RTCMinut = RTCMinut;
	}

	public short getRTCSecund() {
		return this.RTCSecund;
	}

	public void setRTCSecund(short RTCSecund) {
		if ((RTCSecund >= 0) && (RTCSecund < 60))
			this.RTCSecund = RTCSecund;
	}

	public short getAngle_AX() {
		return angle_AX;
	}

	public void setAngle_AX(short angle_AX) {
		this.angle_AX = angle_AX;
	}

	public short getAngle_AY() {
		return angle_AY;
	}

	public void setAngle_AY(short angle_AY) {
		this.angle_AY = angle_AY;
	}

	public short getAngle_AZ() {
		return angle_AZ;
	}

	/*public float getAngleAXDeg() {
		return (float) Math.toDegrees((float) this.angle_AX / 10000) - 90;
	}

	public float getAngleAYDeg() {
		return (float) Math.toDegrees((float) this.angle_AY / 10000) - 90;
	}

	public float getAngleAZDeg() {
		return (float) Math.toDegrees((float) this.angle_AZ / 10000) - 90;
	}*/

	public void setAngle_AZ(short angle_AZ) {
		this.angle_AZ = angle_AZ;
	}

	public short getAccel() {
		return accel;
	}

	public void setAccel(short accel) {
		this.accel = accel;
	}

	public short getAngle_MX() {
		return angle_MX;
	}

	public void setAngle_MX(short angle_MX) {
		this.angle_MX = angle_MX;
	}

	public short getAngle_MY() {
		return angle_MY;
	}

	public void setAngle_MY(short angle_MY) {
		this.angle_MY = angle_MY;
	}

	public short getAngle_MZ() {
		return angle_MZ;
	}

	public void setAngle_MZ(short angle_MZ) {
		this.angle_MZ = angle_MZ;
	}

	public short getTempCase() {
		return tempCase;
	}

	public void setTempCase(short tempCase) {
		this.tempCase = tempCase;
	}

	public short getTempAccel() {
		return tempAccel;
	}

	public void setTempAccel(short tempAccel) {
		this.tempAccel = tempAccel;
	}

	public short getStateRegVal() {
		return this.stateRegVal;
	}

	public State getState() {
		State state = new State();
		state.lax = ((this.stateRegVal & 0x0001) == 0x0001);
		state.lay = ((this.stateRegVal & 0x0002) == 0x0002);
		state.laz = ((this.stateRegVal & 0x0004) == 0x0004);
		state.na = ((this.stateRegVal & 0x0008) == 0x0008);
		state.lta = ((this.stateRegVal & 0x0100) == 0x0100);
		state.nan = ((this.stateRegVal & 0x1000) == 0x1000);
		state.nrtc = ((this.stateRegVal & 0x8000) == 0x8000);
		return state;
	}

	public void setState(short val) {
		this.stateRegVal = val;
	}

	public short getTestRegVal() {
		return this.stateTestVal;
	}

	public Test getTest() {
		Test test = new Test();
		if ((this.stateTestVal & 0x0001) == 0x0001)
			test.resetSource = Test.resetFlag.PORF;
		else if ((this.stateTestVal & 0x0002) == 0x0002)
			test.resetSource = Test.resetFlag.EXRF;
		else if ((this.stateTestVal & 0x0004) == 0x0004)
			test.resetSource = Test.resetFlag.BORF;
		else if ((this.stateTestVal & 0x0008) == 0x0008)
			test.resetSource = Test.resetFlag.WDRF;
		else if ((this.stateTestVal & 0x0020) != 0)
			test.resetSource = Test.resetFlag.BLF;
		else
			test.resetSource = Test.resetFlag.UNDEFINED;
		test.fcrc = ((this.stateTestVal & 0x0100) == 0x0100);
		test.ecrc = ((this.stateTestVal & 0x0200) == 0x0200);
		test.gpio = ((this.stateTestVal & 0x0400) == 0x0400);
		test.i2c = ((this.stateTestVal & 0x0800) == 0x0800);
		test.adc = ((this.stateTestVal & 0x1000) == 0x1000);
		test.trc = ((this.stateTestVal & 0x2000) == 0x2000);
		test.adt = ((this.stateTestVal & 0x4000) == 0x4000);
		test.lsm = ((this.stateTestVal & 0x8000) == 0x8000);
		return test;
	}

	public void setTest(short val) {
		this.stateTestVal = val;
	}

	public short getFWCRC() {
		return FWCRC;
	}

	public void setFWCRC(short fWCRC) {
		FWCRC = fWCRC;
	}


	/**
	 * Mapping to ModbusMLPDataModel
	 * @return ModbusMLPDataModel
	 */
	public ModbusMLPDataModel getModbusMLPData() {
		ModbusMLPDataModel dev = new ModbusMLPDataModel();
		//dev.setDateTime(LocalDateTime.of(this.RTCYear, this.RTCMonth, this.RTCDay, this.RTCHour, this.RTCMinut, this.RTCSecund));
		dev.setAngleAX(this.angle_AX);
		dev.setAngleAY(this.angle_AY);
		dev.setAngleAZ(this.angle_AZ);
		dev.setAccel(this.accel);
		dev.setAngleMX(this.angle_MX);
		dev.setAngleMY(this.angle_MY);
		dev.setAngleMZ(this.angle_MZ);
		dev.setMag(this.mag);
		dev.setTempCase(this.tempCase);
		dev.setTempAccel(this.tempAccel);
		dev.setState(getStateRegVal());
		dev.setTest(getTestRegVal());
		//String[] state=null;
		//dev.setState(state);
		return dev;
	}
	
	public DbDataMlpModel getDbMlpData() {
		DbDataMlpModel dbDataMlpModel = new DbDataMlpModel(
				getAngle_AX(),getAngle_AY(), getAngle_AZ(), getAccel(),
				getAngle_MX(), getAngle_MY(), getAngle_MZ(), getaTolerance(),
				getTempCase(), getTempAccel(), 0, 0);
		return dbDataMlpModel;
	}
}
