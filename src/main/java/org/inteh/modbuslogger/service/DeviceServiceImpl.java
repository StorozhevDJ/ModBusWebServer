package org.inteh.modbuslogger.service;

import java.util.ArrayList;
import java.util.List;

import org.inteh.modbuslogger.database.dao.DbDataMlpDao;
import org.inteh.modbuslogger.database.dao.DbDeviceDao;
import org.inteh.modbuslogger.database.model.DbDataMlpModel;
import org.inteh.modbuslogger.database.model.DbDeviceModel;
import org.inteh.modbuslogger.modbus.daoimpl.ModbusCommonDaoImpl;
import org.inteh.modbuslogger.modbus.daoimpl.ModbusFindDeviceDaoImpl;
import org.inteh.modbuslogger.modbus.daoimpl.ModbusMLPDataDaoImpl;
import org.inteh.modbuslogger.modbus.mapper.ModBusMapMLPDeviceMapper;
import org.inteh.modbuslogger.modbus.mapper.ModbusMapDeviceInfoMapper;
import org.inteh.modbuslogger.modbus.model.ModbusDeviceInfoModel;
import org.inteh.modbuslogger.modbus.model.ModbusMLPDataModel;
import org.inteh.modbuslogger.model.ViewStatusModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghgande.j2mod.modbus.procimg.Register;

@Service
public class DeviceServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);

	@Autowired
	private ModbusFindDeviceDaoImpl FindDeviceDAO;
	@Autowired
	private ModbusMLPDataDaoImpl MLPDataDAO;
	@Autowired
	private ModbusCommonDaoImpl modbusCommonDaoImpl;
	@Autowired
	private DbDeviceDao deviceDbDaoImpl;
	@Autowired
	private DbDataMlpDao dbDataMlpDaoImpl;

	/**
	 * Load finded device in ModBus
	 * 
	 * @param port - COM Port string identifier
	 * @param addr - ModBus device address
	 * @return
	 */
	public ModbusDeviceInfoModel loadSearchedDevice(String port, short addr) {
		LOGGER.info("Запрос поиска устройства {} на порту {}", addr, port);
		Register[] regs = FindDeviceDAO.findModbusDevice(port, 9600, addr);
		if (regs != null) {
			ModbusMapDeviceInfoMapper findDev = new ModbusMapDeviceInfoMapper(regs);
			return findDev.mapDeviceInfo();
		} else {
			LOGGER.info("Устройство {} не найдено", addr);
			return null;
		}
	}

	/**
	 * Add searched device in to Database
	 * 
	 * @param port
	 * @param addr
	 * @return
	 */
	public ViewStatusModel addDeviceToDb(String port, short addr) {
		// Get and parse data from device
		ModbusMapDeviceInfoMapper deviceMapper = new ModbusMapDeviceInfoMapper(
				FindDeviceDAO.findModbusDevice(port, 9600, addr));
		// Mapping data to Database model
		DbDeviceModel deviceDb = deviceMapper.getDeviceDbModel();
		// Insert data to Database
		try {
			deviceDbDaoImpl.insert(deviceDb);
		} catch (Exception e) {
			return new ViewStatusModel(1, "Ошибка добавления устройства");
		}
		return new ViewStatusModel(0, "OK");
	}

	/**
	 * Delete device from Database By Id
	 * 
	 * @param port
	 * @param addr
	 * @return
	 */
	public ViewStatusModel deleteDeviceFromDb(short id) {
		try {
			deviceDbDaoImpl.delete(id);
		} catch (Exception e) {
			return new ViewStatusModel(1, "Ошибка удаления устройства");
		}
		return new ViewStatusModel(0, "OK");
	}

	/**
	 * Load current data from device
	 * 
	 * @param port - COM Port string identifier
	 * @param addr - ModBus device address
	 * @return readed MLP data
	 */
	public ModbusMLPDataModel loadMLPDeviceData(String port, int baud, short addr) {
		LOGGER.info("Запрос данных от устройства {} на порту {} ({})", addr, port, baud);
		Register[] regs = MLPDataDAO.readMLPDeviceData(port, baud, addr);
		if (regs != null) {
			ModBusMapMLPDeviceMapper data = new ModBusMapMLPDeviceMapper(regs);
			LOGGER.info("Устройство {} дало ответ длинной {}", addr, regs.length);
			return data.getModbusMLPData();
		} else {
			LOGGER.info("Device {} not answered", addr);
			return null;
		}
	}

	/**
	 * Reading data from the line of ModDus devices (from all ports in the system)
	 * and saving to the DataBase.
	 */
	public synchronized void mlpDateToDB() {
		List<DbDeviceModel> deviceList = new ArrayList<>();
		try {
			//Get from DB all device list
			deviceList = deviceDbDaoImpl.getAll();
		} catch (Exception e) {
			LOGGER.error("Device list not read from DataBase");
			return;
		}
		// Get all available COM ports
		String[] portnames = modbusCommonDaoImpl.getPortNames();
		for (DbDeviceModel device : deviceList) {
			for (String port : portnames) {
				switch (device.getType()) {
				case 150: {
					//Request get data from MLP device
					Register[] regs = MLPDataDAO.readMLPDeviceData(port, 9600, (short) device.getAddress());
					if (regs != null) {
						// Mapping Modbus MLP Data Model to DataBase MLP Data Model
						ModBusMapMLPDeviceMapper data = new ModBusMapMLPDeviceMapper(regs);
						DbDataMlpModel dbDataMlpModel = data.getDbMlpData();
						dbDataMlpModel.setDevId(device.getId());
						dbDataMlpDaoImpl.insert(dbDataMlpModel);
					}
					break;
				}
				}
			}
		}
	}

	
	public String[] getAvailablePorts() {
		return modbusCommonDaoImpl.getPortNames();
	}

}
