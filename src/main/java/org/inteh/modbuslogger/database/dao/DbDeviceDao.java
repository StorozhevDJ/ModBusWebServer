package org.inteh.modbuslogger.database.dao;

import java.util.List;

import org.inteh.modbuslogger.database.model.DbDeviceModel;


public interface DbDeviceDao {
	/**
	 * вставляет Device в базу данных
	 * @throws Exception 
	 */
	DbDeviceModel insert(DbDeviceModel device) throws Exception;

	/**
	 * Изменяет Device в базе данных
	 * @throws Exception 
	 */
	DbDeviceModel update(DbDeviceModel device) throws Exception;
	
	/**
	 * Получает все Devices из базы данных
	 * @throws Exception 
	 */
	List<DbDeviceModel> getAll() throws Exception;
	
	/**
	 * Получает Device по Id из базы данных
	 * @throws Exception 
	 */
	DbDeviceModel getById(int id) throws Exception;
	
	/**
	 * Получает Device по Id из базы данных
	 * @throws Exception 
	 */
	List<DbDeviceModel> getByType(int id) throws Exception;
	
	/**
	 * Удаляет Device по Id из базы данных
	 * @throws Exception 
	 */
	void delete(int id) throws Exception;

	/**
	 * Get Device count in DB
	 * @return
	 * @throws Exception
	 */
	Integer getDeviceCount() throws Exception;
}
