package org.inteh.sbsecurity.dao;

import org.inteh.sbsecurity.dao.modbus.ModBusDAO;
import org.inteh.sbsecurity.mapper.DeviceInfoModbusMapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ghgande.j2mod.modbus.procimg.Register;

@Repository
@Transactional
public class FindModbusDeviceDAO extends DeviceInfoModbusMapMapper {
	
	@Autowired
	public FindModbusDeviceDAO() {

	}

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
	
	
	public synchronized Register[] findModbusDevice(String port, short addr) {
		ModBusDAO modBus = ModBusDAO.getInstance();
		Register[] regs = null;
		try {
			modBus.openPort(port, 9600);
			//System.out.println("COM Port " + port + "opened");
			regs = ModBusDAO.findDevice(addr);
			modBus.closePort();
			//System.out.println("COM Port " + port + "closed");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("COM Port open failed");
		}
		return regs;
	}
}
