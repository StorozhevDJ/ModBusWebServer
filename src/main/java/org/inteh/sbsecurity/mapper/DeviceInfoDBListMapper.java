package org.inteh.sbsecurity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.inteh.sbsecurity.model.DeviceInfo;
import org.springframework.jdbc.core.RowMapper;

public class DeviceInfoDBListMapper implements RowMapper<DeviceInfo> {
	public static final String BASE_SQL //
	= "Select ba.devId, ba.address, ba.type, ba.serial, ba.comment From devices ba ";
	
	@Override
	public DeviceInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

		int id = rs.getInt("devId");
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

		return new DeviceInfo(id, addr, typeStr, serial, comment);
	}
}
