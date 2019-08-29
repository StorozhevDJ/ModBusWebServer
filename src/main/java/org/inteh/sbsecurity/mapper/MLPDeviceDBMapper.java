package org.inteh.sbsecurity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.inteh.sbsecurity.model.MLPData;
import org.springframework.jdbc.core.RowMapper;

public class MLPDeviceDBMapper implements RowMapper<MLPData> {
	public static final String BASE_SQL //
			= "SELECT * FROM mlp_data ba ";

	@Override
	public MLPData mapRow(ResultSet rs, int rowNum) throws SQLException {

		MLPData data = new MLPData();
		data.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
		data.setAngleAX(rs.getInt("anglex"));
		data.setAngleAY(rs.getInt("angley"));
		data.setAngleAZ(rs.getInt("anglez"));
		data.setAccel(rs.getInt("accel"));
		data.setAngleMX(rs.getInt("anglemx"));
		data.setAngleMY(rs.getInt("anglemy"));
		data.setAngleMZ(rs.getInt("anglemz"));
		data.setMag(rs.getInt("mag"));
		data.setTempCase(rs.getInt("tempcase"));
		data.setTempAccel(rs.getInt("tempaccel"));

		// test
		// state
		return data;
	}
}
