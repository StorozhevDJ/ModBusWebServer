package org.inteh.sbsecurity.dao.modbus;

import com.fazecast.jSerialComm.SerialPort;
import com.ghgande.j2mod.modbus.Modbus;
import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.net.AbstractSerialConnection;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.util.SerialParameters;

public class ModBusDAO {
	private static ModBusDAO instance;

	private static ModbusSerialMaster serialMaster;

	// Variables for storying the parameters
	String portname = "COM3"; // the name of the serial port to be used
	// int unitID = 6; // the unit identifier we will be talking to
	int startingRegister = 0; // the reference, where to start reading from
	int registerCount = 50; // the count of the input registers to read
	Register[] slaveResponse = new Register[registerCount];

	ModBusDAO() {
		// OpenPort(portname, 9600);
	}

	public static synchronized ModBusDAO getInstance() {
		if (instance == null) {
			instance = new ModBusDAO();
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
	public void openPort(String portname, int baud) throws Exception {

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
		if (serialMaster.getConnection().isOpen())
			System.out.println("Уже открыт");
		else
			serialMaster.connect();
	}

	public void closePort() throws Exception {
		// Close the connection
		if (serialMaster.getConnection().isOpen() == true)
			serialMaster.disconnect();
	}

	// ----------------------------------------------------------------------------------------------------
	// Get name ports Available
	public static String[] getPortNames() {
		SerialPort[] ports = SerialPort.getCommPorts();
		String[] result = new String[ports.length];
		for (int i = 0; i < ports.length; i++) {
			result[i] = ports[i].getSystemPortName();
			System.out.println("Port=" + result[i]);
		}
		return result;
	}

	/*****************************************************
	 * Find ModBus Device
	 * 
	 * @param id - SubCounter
	 **/
	public static synchronized Register[] findDevice(short id) {
		Register[] slaveResponse = new Register[30];
		int timeout = 2000000 / serialMaster.getConnection().getBaudRate();
		if (timeout < 70)
			timeout = 70;
		serialMaster.setTimeout(timeout);
		serialMaster.setRetries(1);
		// Read the first four holding registers
		try {
			slaveResponse = serialMaster.readMultipleRegisters(id, 0, slaveResponse.length);
			System.out.println("Finded dev. id = " + String.valueOf(id));
			for (int i = 0; i < slaveResponse.length; i++) {
				System.out.println("reg" + i + " = " + slaveResponse[i]);
			}
		} catch (ModbusException e) {
			// e.printStackTrace();
			System.out.println("Dev " + id + " not found.");
			slaveResponse = null;
		}
		return slaveResponse;
	}

	/*****************************************************
	 * Find ModBus Device
	 * 
	 * @param addr      - SubCounter
	 * @param mapOffset - ModBus map start address
	 * @param len       - Number of data to read
	 **/
	public static synchronized Register[] getDeviceData(short addr, short mapOffset, short len) {
		Register[] slaveResponse = new Register[len];
		int timeout = 2000000 / serialMaster.getConnection().getBaudRate();
		if (timeout < 70)
			timeout = 70;
		serialMaster.setTimeout(timeout);
		serialMaster.setRetries(5);
		// Read the first four holding registers
		try {
			slaveResponse = serialMaster.readMultipleRegisters(addr, mapOffset, slaveResponse.length);
			System.out.println("Finded dev. id = " + String.valueOf(addr));
			//for (int i = 0; i < slaveResponse.length; i++) {
			//	System.out.println("reg" + i + " = " + slaveResponse[i]);
			//}
		} catch (ModbusException e) {
			// e.printStackTrace();
			System.err.println("Dev " + addr + " not found.");
			slaveResponse = null;
		}
		return slaveResponse;
	}

}
