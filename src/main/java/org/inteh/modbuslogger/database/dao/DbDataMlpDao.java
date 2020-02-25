package org.inteh.modbuslogger.database.dao;

import java.util.List;

import org.inteh.modbuslogger.database.model.DbDataMlpModel;

public interface DbDataMlpDao {
	/**
	 * вставляет данные от МЛП в базу данных
	 */
	DbDataMlpModel insert(DbDataMlpModel data);
	
	/**
	 * Получает все данные для МЛП с указанным ID устройства
	 */
	List<DbDataMlpModel> getByDevId(int id);
	
	/**
	 * Получает данные по Id записи из базы данных
	 */
	DbDataMlpModel getById(int id);
	
	/**
	 * Удаляет данные по Id из базы данных
	 */
	void delete(int id);
}
