package org.inteh.sbsecurity.dao;

import org.inteh.sbsecurity.dao.modbus.ModBusDAO;
import org.inteh.sbsecurity.mapper.MLPDeviceModbusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ghgande.j2mod.modbus.procimg.Register;

@Repository
@Transactional
public class MLPModbusDataDAO {
	public final short mapOffset = 40;
	
	@Autowired
	public MLPModbusDataDAO() {

	}
	
	public synchronized Register[] readMLPDeviceData(String port, short addr) {
		ModBusDAO modBus = ModBusDAO.getInstance();
		Register[] regs = null;
		try {
			modBus.openPort(port, 9600);
			//System.out.println("COM Port " + port + "opened");
			regs = ModBusDAO.getDeviceData(addr, MLPDeviceModbusMapper.modbusMapOffsetData, (short) MLPDeviceModbusMapper.modBusDataRegMap.HOLDING_REGS_SIZE.ordinal());
			modBus.closePort();
			//System.out.println("COM Port " + port + "closed");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("COM Port open failed");
		}
		return regs;
	}
	
	public String[] getPortNames() {
		return ModBusDAO.getPortNames();
		
	}
}
