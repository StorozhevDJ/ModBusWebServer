package org.inteh.modbuslogger.modbus.daoimpl;

import org.inteh.modbuslogger.modbus.mapper.ModBusMapMLPDeviceMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ghgande.j2mod.modbus.procimg.Register;

@Repository
@Transactional
public class ModbusMLPDataDaoImpl {
	
	
	public final short mapOffset = 40;
	
	public synchronized Register[] readMLPDeviceData(String port, int baud, short addr) {
		
		ModbusCommonDaoImpl modBus = ModbusCommonDaoImpl.getInstance();
		Register[] regs = null;
		try {
			modBus.openPort(port, baud);
			regs = new ModbusCommonDaoImpl().getDeviceData(addr, ModBusMapMLPDeviceMapper.modbusMapOffsetData, (short) ModBusMapMLPDeviceMapper.modBusDataRegMap.HOLDING_REGS_SIZE.ordinal());
		} catch (Exception e) {
			System.err.println("COM Port open failed");
		}
		finally {
			modBus.closePort();
		}
		return regs;
	}

	public synchronized Register[] readMLPDeviceData(short addr) {
		return new ModbusCommonDaoImpl().getDeviceData(addr, ModBusMapMLPDeviceMapper.modbusMapOffsetData, (short) ModBusMapMLPDeviceMapper.modBusDataRegMap.HOLDING_REGS_SIZE.ordinal());
	}
}
