package org.inteh.modbuslogger.modbus.dao;

import com.ghgande.j2mod.modbus.procimg.Register;

public interface ModbusCommonDao {
	
	/**
	 * Open COM port
	 * 
	 * @param String  portname
	 * @param integer baud
	 * @throws Exception
	 */
	void openPort(String portname, int baud) throws Exception;
	
	/**
	 * Close port
	 * 
	 * @throws Exception
	 */
	void closePort() throws Exception;
	
	/**
	 * Get name all Available ports
	 * 
	 * @return String array
	 */
	String[] getPortNames();
	
	/**
	 * Find ModBus Device with id
	 * 
	 * @param id - SubCounter
	 **/
	Register[] findDevice(short id);
	
	/*****************************************************
	 * Get ModBus Device data
	 * 
	 * @param addr      - SubCounter
	 * @param mapOffset - ModBus map start address
	 * @param len       - Number of data to read
	 **/
	Register[] getDeviceData(short addr, short mapOffset, short len);
}
