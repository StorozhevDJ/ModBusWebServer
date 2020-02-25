package org.inteh.modbuslogger.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.inteh.modbuslogger.database.dao.DbDataMlpDao;
import org.inteh.modbuslogger.database.dao.DbDeviceDao;
import org.inteh.modbuslogger.database.daoimpl.DbDataMlpDaoImpl;
import org.inteh.modbuslogger.database.daoimpl.DbDeviceDaoImpl;
import org.inteh.modbuslogger.database.model.DbDataMlpModel;
import org.inteh.modbuslogger.mapper.DeviceInfoDBListMapper;
import org.inteh.modbuslogger.mapper.ViewMlpDataMapper;
import org.inteh.modbuslogger.modbus.model.ModbusDeviceInfoModel;
import org.inteh.modbuslogger.modbus.model.ModbusMLPDataModel;
import org.inteh.modbuslogger.utils.MyBatisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DeviceInfoDBListDAO extends JdbcDaoSupport {

	private static final Map<Integer, ModbusDeviceInfoModel> devMap = new HashMap<Integer, ModbusDeviceInfoModel>();
	
	private DbDeviceDao deviceDao = new DbDeviceDaoImpl();
	private DbDataMlpDao dbDataMlpDao = new DbDataMlpDaoImpl();

	static {
		initDevs();
		MyBatisUtils.initSqlSessionFactory();
	}

	@Autowired
	public DeviceInfoDBListDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	/**
	 * Get all Device info list from DB
	 * @return
	 */
	public List<ModbusDeviceInfoModel> getDevicesDBList(){
		try {
			return new DeviceInfoDBListMapper().dbMapper(deviceDao.getAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get Device info list by device type
	 * @param type
	 * @return
	 */
	public List<ModbusDeviceInfoModel> getDevicesDBList(int type) {
		try {
			return new DeviceInfoDBListMapper().dbMapper(deviceDao.getByType(type));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get Device info by device id
	 * @param type
	 * @return
	 */
	public ModbusDeviceInfoModel getDeviceDB(int id) {
		try {
			new DeviceInfoDBListMapper().dbMapper(deviceDao.getById(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static void initDevs() {
		/*
		 * DeviceInfo dev1 = new DeviceInfo(1, "МЛП", 28, "Comment1"); DeviceInfo dev2 =
		 * new DeviceInfo(2, "СКЦТ", 132, "Коммент2"); DeviceInfo rev3 = new
		 * DeviceInfo(30, "МЛП", 374, "Коммент 3");
		 * 
		 * devMap.put(dev1.getAddress(), dev1); devMap.put(dev2.getAddress(), dev2);
		 * devMap.put(rev3.getAddress(), rev3);
		 */
	}

	public ModbusDeviceInfoModel getDevice(int devAddr) {

		// FindDeviceDAO find = new FindDeviceDAO();
		// find.findModbusDevice("ttyUSB1", (short)devAddr);

		return devMap.get(devAddr);
	}

	public ModbusDeviceInfoModel addDevice(ModbusDeviceInfoModel devAddr) {
		DbDeviceDao deviceDao;
		
		
		
		devMap.put(devAddr.getAddress(), devAddr);
		return devAddr;
	}

	public ModbusDeviceInfoModel updateDevice(ModbusDeviceInfoModel devAddr) {
		devMap.put(devAddr.getAddress(), devAddr);
		return devAddr;
	}

	public void deleteDeviceDB(int id) {
		try {
			deviceDao.delete(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get data from the Database for the MLP device
	 * 
	 * @param addr
	 * @return
	 */
	public List<ViewMlpDataMapper> getMLPData(int id, LocalDate from, LocalDate to) {
		List<DbDataMlpModel> dataDbList = dbDataMlpDao.getByDevId(id);
		List<ViewMlpDataMapper> data = new ArrayList<>();
		for (DbDataMlpModel dataDb : dataDbList) {
			data.add(new ViewMlpDataMapper(dataDb));
		}
		return data;
	}

	/**
	 * Insert data from the MLP device into the DataBase
	 * 
	 * @param data
	 * @param devId
	 */
	// MANDATORY: Transaction must be created before.
	// @Transactional(propagation = Propagation.MANDATORY)
	public void insertMLPDate(ModbusMLPDataModel data, long devId) {
		String sqlInsert = "INSERT INTO `mydatabase`.`mlp_data` " //
				+ "SET dev_id=?," //
				+ "anglex=?, angley=?, anglez=?, accel=?, " //
				+ "anglemx=?, anglemy=?, anglemz=?, mag=?," //
				+ "tempcase=?, tempaccel=?, test=?, state=?";
		this.getJdbcTemplate().update(sqlInsert, devId, data.getAngleAX(), data.getAngleAY(), data.getAngleAZ(),
				data.getAccel(), data.getAngleMX(), data.getAngleMY(), data.getAngleMZ(), data.getMag(),
				data.getTempCase(), data.getTempAccel(), data.getTest(), data.getState());
	}

}


