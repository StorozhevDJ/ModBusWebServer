package org.inteh.modbuslogger.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.inteh.modbuslogger.modbus.daoimpl.ModbusFindDeviceDaoImpl;
import org.inteh.modbuslogger.modbus.model.ModbusDeviceInfoModel;
import org.springframework.stereotype.Repository;

@Repository
public class AppDeviceDAO {

	private static final Map<Integer, ModbusDeviceInfoModel> devMap = new HashMap<Integer, ModbusDeviceInfoModel>();


	public ModbusDeviceInfoModel getDevice(int devAddr) {
		
		ModbusFindDeviceDaoImpl find = new ModbusFindDeviceDaoImpl();
		find.findModbusDevice("ttyUSB1", 9600, (short)devAddr);
		
		//return 
		return devMap.get(devAddr);
	}

	public ModbusDeviceInfoModel addDevice(ModbusDeviceInfoModel devAddr) {
		devMap.put(devAddr.getAddress(), devAddr);
		return devAddr;
	}

	public ModbusDeviceInfoModel updateDevice(ModbusDeviceInfoModel devAddr) {
		devMap.put(devAddr.getAddress(), devAddr);
		return devAddr;
	}

	public void deleteDevice(int devAddr) {
		devMap.remove(devAddr);
	}

	public List<ModbusDeviceInfoModel> getAllDevices() {
		Collection<ModbusDeviceInfoModel> c = devMap.values();
		List<ModbusDeviceInfoModel> list = new ArrayList<ModbusDeviceInfoModel>();
		list.addAll(c);
		return list;
	}

}