package org.inteh.sbsecurity.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.inteh.sbsecurity.mapper.DeviceInfoDBListMapper;
import org.inteh.sbsecurity.mapper.MLPDeviceDBMapper;
import org.inteh.sbsecurity.model.DeviceInfo;
import org.inteh.sbsecurity.model.MLPData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DeviceInfoDBListDAO extends JdbcDaoSupport {

	private static final Map<Integer, DeviceInfo> devMap = new HashMap<Integer, DeviceInfo>();

	static {
		initDevs();
	}

	@Autowired
	public DeviceInfoDBListDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public List<DeviceInfo> getDevicesDBList(){
		return getDevicesDBList(null);
	}
	
	public List<DeviceInfo> getDevicesDBList(String type) {
		// Select ba.devId, ba.address, ba.type, ba.serial, ba.comment From devices ba
		String sql = DeviceInfoDBListMapper.BASE_SQL;
		
		if (type!=null) {
			if (type.equalsIgnoreCase("МЛП")) sql+=" WHERE type=150";
		}

		Object[] params = new Object[] {};
		DeviceInfoDBListMapper mapper = new DeviceInfoDBListMapper();
		try {
			List<DeviceInfo> list = this.getJdbcTemplate().query(sql, params, mapper);
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public DeviceInfo findDeviceDB(Long id) {
		// Select ba.Id, ba.Full_Name, ba.Balance From Bank_Account ba
		// Where ba.Id = ?
		String sql = DeviceInfoDBListMapper.BASE_SQL + " where ba.Id = ? ";

		Object[] params = new Object[] { id };
		DeviceInfoDBListMapper mapper = new DeviceInfoDBListMapper();
		try {
			DeviceInfo bankAccount = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			return bankAccount;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
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

	public DeviceInfo getDevice(int devAddr) {

		// FindDeviceDAO find = new FindDeviceDAO();
		// find.findModbusDevice("ttyUSB1", (short)devAddr);

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
		String sql = "DELETE FROM devices WHERE devId = ?";
		this.getJdbcTemplate().update(sql, devAddr);
	}

	/**
	 * Get data from the Database for the MLP device
	 * 
	 * @param addr
	 * @return
	 */
	public List<MLPData> getMLPData(int sn, LocalDate from, LocalDate to) {
		String sql = MLPDeviceDBMapper.BASE_SQL;// SELECT * FROM mlp_data ba

		sql = "SELECT da.date_time, da.anglex, da.angley, da.anglez, da.accel, da.anglemx, da.anglemy, da.anglemz, da.mag, da.tempcase, da.tempaccel, da.state, da.test  " + 
				"FROM mlp_data da INNER JOIN devices dev  ON da.dev_id = dev.devId " + 
				"WHERE dev.serial = ? AND da.date_time BETWEEN ? AND ? LIMIT 10000";
		
		String dateFrom = (from!=null)?from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00")):"2000-01-01 00:00:00";
		String dateTo = ((to!=null)?to:(LocalDate.now())).format(DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59"));
		System.out.println("date from = " + dateFrom);
		System.out.println("date to = " + dateTo);

		MLPDeviceDBMapper mapper = new MLPDeviceDBMapper();
		List<MLPData> data = this.getJdbcTemplate().query(sql, mapper, new Object[] {sn, dateFrom, dateTo});
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
	public void insertMLPDate(MLPData data, long devId) {
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


