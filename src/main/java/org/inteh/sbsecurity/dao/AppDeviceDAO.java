package org.inteh.sbsecurity.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.inteh.sbsecurity.model.DeviceInfo;
import org.springframework.stereotype.Repository;

@Repository
public class AppDeviceDAO {

	private static final Map<Integer, DeviceInfo> devMap = new HashMap<Integer, DeviceInfo>();

	static {
		initDevs();
	}

	private static void initDevs() {
		/*DeviceInfo dev1 = new DeviceInfo(1, "МЛП", 28, "Comment1");
		DeviceInfo dev2 = new DeviceInfo(2, "СКЦТ", 132, "Коммент2");
		DeviceInfo rev3 = new DeviceInfo(30, "МЛП", 374, "Коммент 3");

		devMap.put(dev1.getAddress(), dev1);
		devMap.put(dev2.getAddress(), dev2);
		devMap.put(rev3.getAddress(), rev3);*/
	}

	public DeviceInfo getDevice(int devAddr) {
		
		FindModbusDeviceDAO find = new FindModbusDeviceDAO();
		find.findModbusDevice("ttyUSB1", (short)devAddr);
		
		//return 
		return devMap.get(devAddr);
	}

	public DeviceInfo addDevice(DeviceInfo devAddr) {
		devMap.put(devAddr.getAddress(), devAddr);
		return devAddr;
	}

	public DeviceInfo updateDevice(DeviceInfo devAddr) {
		devMap.put(devAddr.getAddress(), devAddr);
		return devAddr;
	}

	public void deleteDevice(int devAddr) {
		devMap.remove(devAddr);
	}

	public List<DeviceInfo> getAllDevices() {
		Collection<DeviceInfo> c = devMap.values();
		List<DeviceInfo> list = new ArrayList<DeviceInfo>();
		list.addAll(c);
		return list;
	}

}