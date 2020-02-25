package org.inteh.modbuslogger.modbus.daoimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ghgande.j2mod.modbus.procimg.Register;

@Repository
@Transactional
public class ModbusFindDeviceDaoImpl  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ModbusFindDeviceDaoImpl.class);
	

	/*public synchronized FindDevice findModbusDevice(String port, short addr) {
		FindDevice dev = null;
		ModBusDAO modBus = ModBusDAO.getInstance();
		// String [] ports = modBus.getPortNames();
		try {
			modBus.openPort(port, 9600);
			System.out.println("COM Port " + port + "opened");
			Register[] regs = modBus.findDevice(addr);
			dev = mapDeviceInfo(regs);
			modBus.closePort();
			System.out.println("COM Port " + port + "closed");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("COM Port open failed");
		}

		return dev;
	}*/
	public synchronized Register[] findModbusDevice(String port, int baud, short addr) {
		ModbusCommonDaoImpl modBus = ModbusCommonDaoImpl.getInstance();
		Register[] regs = null;
		try {
			modBus.openPort(port, baud);
			regs = new ModbusCommonDaoImpl().findDevice(addr);
			//LOGGER.info("Устройство найдено, длинна ответа {}", regs.length);
			modBus.closePort();
		} catch (Exception e) {
			modBus.closePort();
		}
		return regs;
	}
}
