package org.inteh.modbuslogger.database.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.inteh.modbuslogger.database.model.DbDataMlpModel;


public interface DbDataMlpMapper {

    @Insert("INSERT INTO `mlp_data` ( devId, date_time, anglex, angley, anglez, accel, anglemx, anglemy, anglemz, mag, tempcase, tempaccel, state, test) "
            + "VALUES ( #{devId}, current_time(), #{anglex}, #{angley}, #{anglez}, #{accel}, #{anglemx}, #{anglemy}, #{anglemz}, #{mag}, #{tempcase}, #{tempaccel}, #{state}, #{test} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(DbDataMlpModel data);

    @Select("SELECT * FROM `mlp_data` WHERE id = #{id}")
    DbDataMlpModel getById(int id);
    
    @Select("SELECT * FROM `mlp_data` WHERE devId = #{id}")
    List<DbDataMlpModel> getByDevId(int id);
    
    @Delete("DELETE FROM `mlp_data` WHERE id = #{id}")
    void delete(int id);
}
