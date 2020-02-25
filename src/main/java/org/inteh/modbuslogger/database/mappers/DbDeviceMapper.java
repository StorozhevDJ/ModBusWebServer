package org.inteh.modbuslogger.database.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.inteh.modbuslogger.database.model.DbDeviceModel;


public interface DbDeviceMapper {

    @Insert("INSERT INTO `devices` ( address, type, serial, baudrate, hw_ver, fw_ver, mfg_year, min_voltage, max_voltage, current, comment, enabled) "
            + "VALUES ( #{address}, #{type}, #{serial}, #{baudrate}, #{hw_ver}, #{fw_ver}, #{mfg_year}, #{min_voltage}, #{max_voltage}, #{current}, #{comment}, #{enabled} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(DbDeviceModel device);

    @Update("UPDATE `devices` "
    		+ "SET address = #{address}, baudrate = #{baudrate}, comment = #{comment}, enabled = #{enabled} "
    		+ "WHERE id = #{id};")
    void update(DbDeviceModel device);

    @Select("SELECT * FROM `modbuslogger`.`devices`")
    List<DbDeviceModel> getAll();
    
    @Select("SELECT * FROM `devices` WHERE id = #{id}")
    DbDeviceModel getById(int id);

    @Select("SELECT * FROM `devices` WHERE type = #{type}")
    List<DbDeviceModel> getByType(int type);
    
    @Delete("DELETE FROM `devices` WHERE id = #{id}")
    void delete(int id);
    
    @Select("SELECT COUNT(*) FROM `devices`")
    Integer getDeviceCount();
}
