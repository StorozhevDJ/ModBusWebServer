package org.inteh.modbuslogger.modbus.dao;

import com.ghgande.j2mod.modbus.procimg.Register;

public interface ModbusMLPDataDao {
	
	Register[] readMLPDeviceData(String port, int baud, short addr);
	
	
}
