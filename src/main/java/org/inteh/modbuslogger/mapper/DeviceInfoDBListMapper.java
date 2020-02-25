package org.inteh.modbuslogger.mapper;

import java.util.ArrayList;
import java.util.List;

import org.inteh.modbuslogger.database.model.DbDeviceModel;
import org.inteh.modbuslogger.modbus.model.ModbusDeviceInfoModel;

public class DeviceInfoDBListMapper /*implements RowMapper<DeviceInfo>*/ {
	public static final String BASE_SQL //
			= "Select ba.id, ba.address, ba.type, ba.serial, ba.comment From devices ba ";

	/*@Override
	public DeviceInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

		int id = rs.getInt("id");
		int addr = rs.getInt("address");
		short type = rs.getShort("type");
		String typeStr = new String();
		switch (type) {
		case 103:
			typeStr = "СКЦД-1/200";
			break;
		case 105:
			typeStr = "СКЦД-2/200";
			break;
		case 106:
			typeStr = "СКЦД-4/200";
			break;
		case 107:
			typeStr = "СКЦД-6/200";
			break;
		case 108:
			typeStr = "CKT-4-R";
			break;
		case 150:
			typeStr = "МЛП";
			break;
		case 151:
			typeStr = "MЛП";
			break;
		default:
			typeStr = "Неизвестный тип (" + String.valueOf(type) + ")";
		}
		int serial = rs.getInt("serial");
		String comment = rs.getString("comment");

		// return new DeviceInfo(id, addr, typeStr, serial, comment);
		return null;
	}*/

	public ModbusDeviceInfoModel dbMapper(DbDeviceModel device) {
		String typeStr = new String();
		switch (device.getType()) {
		case 103:
			typeStr = "СКЦД-1/200";
			break;
		case 105:
			typeStr = "СКЦД-2/200";
			break;
		case 106:
			typeStr = "СКЦД-4/200";
			break;
		case 107:
			typeStr = "СКЦД-6/200";
			break;
		case 108:
			typeStr = "CKT-4-R";
			break;
		case 150:
			typeStr = "МЛП";
			break;
		case 151:
			typeStr = "MЛП";
			break;
		default:
			typeStr = "Неизвестный тип (" + String.valueOf(device.getType()) + ")";
		}

		return new ModbusDeviceInfoModel(device.getId(), device.getAddress(), typeStr, device.getSerial(), device.getHw_ver(),
				device.getFw_ver(), device.getMfg_year(), device.getMin_voltage(), device.getMax_voltage(),
				device.getCurrent(), device.getComment());
	}

	public List<ModbusDeviceInfoModel> dbMapper(List<DbDeviceModel> devices) {
		List<ModbusDeviceInfoModel> deviceInfo = new ArrayList<>();
		for (DbDeviceModel device : devices) {
			deviceInfo.add(dbMapper(device));
		}
		return deviceInfo;
	}

}
