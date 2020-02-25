package org.inteh.modbuslogger.modbus.daoimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fazecast.jSerialComm.SerialPort;
import com.ghgande.j2mod.modbus.Modbus;
import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.net.AbstractSerialConnection;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.util.SerialParameters;

@Repository
@Transactional
public class ModbusCommonDaoImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModbusCommonDaoImpl.class);

	private static ModbusCommonDaoImpl instance;

	private static ModbusSerialMaster serialMaster;

	public static synchronized ModbusCommonDaoImpl getInstance() {
		if (instance == null) {
			instance = new ModbusCommonDaoImpl();
		}
		return instance;
	}

	/**********************************************
	 * Open COM port
	 * 
	 * @param String  portname
	 * @param integer baud
	 * @throws Exception
	 */
	public synchronized void openPort(String portname, int baud) throws Exception {

		// Setup the serial parameters
		SerialParameters parameters = new SerialParameters();
		parameters.setPortName(portname);
		parameters.setBaudRate(baud);
		parameters.setDatabits(8);
		parameters.setParity(AbstractSerialConnection.NO_PARITY);
		parameters.setStopbits(AbstractSerialConnection.ONE_STOP_BIT);
		parameters.setEncoding(Modbus.SERIAL_ENCODING_RTU);
		parameters.setEcho(false);

		// Open the connection
		serialMaster = new ModbusSerialMaster(parameters);
		if (serialMaster.getConnection().isOpen()) {
			LOGGER.debug("Com Port {} with baudrate {} are open before", portname, baud);
		} else {
			try {
				serialMaster.connect();
				LOGGER.debug("Com Port {} with baudrate {} are open successful", portname, baud);
			}
			catch (Exception e) {
				LOGGER.warn("Com Port {} open failed. {}", portname, e.getMessage());
				throw e;
			}
		}
	}

	/**
	 * Close port
	 * 
	 * @throws Exception
	 */
	public synchronized void closePort() {
		// Close the connection
		if (serialMaster.getConnection().isOpen() == true) {
			serialMaster.disconnect();
			LOGGER.debug("Com Port close successful");
		}
		else {
			LOGGER.debug("Com Port close failed");
		}
	}

	/**
	 * Get name all Available ports
	 * 
	 * @return String array
	 */
	@Autowired
	public String[] getPortNames() {
		SerialPort[] ports = SerialPort.getCommPorts();
		String[] result = new String[ports.length];
		for (int i = 0; i < ports.length; i++) {
			result[i] = ports[i].getSystemPortName();
		}
		LOGGER.debug("Available COM Ports: {}.", String.join(", ", result));
		return result;
	}

	/*****************************************************
	 * Find ModBus Device with id
	 * 
	 * @param id - SubCounter
	 **/
	public synchronized Register[] findDevice(short id) {
		Register[] slaveResponse = new Register[30];
		int timeout = 2000000 / serialMaster.getConnection().getBaudRate();
		serialMaster.setTimeout(timeout < 70 ? 70 : timeout);
		serialMaster.setRetries(1);
		// Read the first four holding registers
		try {
			slaveResponse = serialMaster.readMultipleRegisters(id, 0, slaveResponse.length);
			LOGGER.debug("Finded device with id = {}. Response length {}", id, slaveResponse.length);
			/*
			 * for (int i = 0; i < slaveResponse.length; i++) { System.out.println("reg" + i
			 * + " = " + slaveResponse[i]); }
			 */
		} catch (ModbusException e) {
			LOGGER.debug("Dev {} not found.", id);
			slaveResponse = null;
		}
		return slaveResponse;
	}

	/*****************************************************
	 * Get ModBus Device data
	 * 
	 * @param addr      - SubCounter
	 * @param mapOffset - ModBus map start address
	 * @param len       - Number of data to read
	 **/
	public synchronized Register[] getDeviceData(short addr, short mapOffset, short len) {
		Register[] slaveResponse = new Register[len];
		int timeout = 2000000 / serialMaster.getConnection().getBaudRate();
		serialMaster.setTimeout(timeout < 70 ? 70 : timeout);
		serialMaster.setRetries(4);
		// Read the first four holding registers
		try {
			slaveResponse = serialMaster.readMultipleRegisters(addr, mapOffset, slaveResponse.length);
			LOGGER.debug("Received data from device id = {}. Response length {}", addr, slaveResponse.length);
		} catch (ModbusException e) {
			LOGGER.debug("Dev {} not found.", addr);
			slaveResponse = null;
		}
		return slaveResponse;
	}

}
