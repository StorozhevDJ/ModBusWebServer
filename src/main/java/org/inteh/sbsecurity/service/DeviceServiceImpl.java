package org.inteh.sbsecurity.service;

import org.inteh.sbsecurity.dao.FindModbusDeviceDAO;
import org.inteh.sbsecurity.dao.MLPModbusDataDAO;

import java.util.List;

import org.inteh.sbsecurity.dao.*;
import org.inteh.sbsecurity.mapper.DeviceInfoModbusMapMapper;
import org.inteh.sbsecurity.mapper.MLPDeviceModbusMapper;
import org.inteh.sbsecurity.model.DeviceInfo;
import org.inteh.sbsecurity.model.MLPData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghgande.j2mod.modbus.procimg.Register;

@Service
public class DeviceServiceImpl {

	@Autowired
	private FindModbusDeviceDAO FindDeviceDAO;
	@Autowired
	private MLPModbusDataDAO MLPDataDAO;
	@Autowired
	private DeviceInfoDBListDAO DeviceDBListDAO;

	/**
	 * Load finded device in ModBus
	 * 
	 * @param port - COM Port string identifier
	 * @param addr - ModBus device address
	 * @return
	 */
	public DeviceInfo loadSearchedDevice(String port, short addr) {
		DeviceInfoModbusMapMapper findDev = new DeviceInfoModbusMapMapper();
		Register[] regs = FindDeviceDAO.findModbusDevice(port, addr);
		if (regs != null)
			return findDev.mapDeviceInfo(regs);
		else {
			return null;
		}
	}

	/**
	 * Load current data from device
	 * 
	 * @param port - COM Port string identifier
	 * @param addr - ModBus device address
	 * @return readed MLP data
	 */
	public MLPData loadMLPDeviceData(String port, short addr) {
		MLPDeviceModbusMapper data = new MLPDeviceModbusMapper();
		Register[] regs = MLPDataDAO.readMLPDeviceData(port, addr);
		if (regs != null)
			return data.mapMLPData(regs);
		else
			return null;
	}

	/**
	 * Reading data from the line of ModDus devices (from all ports in the system) and saving to the DataBase.
	 */
	public void mlpDateToDB() {
		List<DeviceInfo> devLists = DeviceDBListDAO.getDevicesDBList();
		String[] portnames = MLPDataDAO.getPortNames();
		for (DeviceInfo devList : devLists) {// for each device in to list
			for (String port : portnames) {
				System.out.println("Finded dev #" + devList.getAddress() + " " + devList.getComment());
				switch (devList.getType()) {
				case "МЛП": {
					MLPData data = loadMLPDeviceData(port, (short) devList.getAddress());
					if (data != null) {
						System.out.println("Data is received suceesful. Accel=" + data.getAccel());
						DeviceDBListDAO.insertMLPDate(data, (short) devList.getDevId());
					} else
						System.err.println("No connected to device id: " + devList.getAddress() + ", Port: " + port);
					break;
				}
				case "СКЦД-1/200": {

				}
				default:
					System.err.println("Not defined device type");
				}

				// for all ports in system
				// Read the data from the ModBus Device
				// Add the read data to the data list and go to the next device from device list
				// Save data to the database
			}
		}

	}

}
