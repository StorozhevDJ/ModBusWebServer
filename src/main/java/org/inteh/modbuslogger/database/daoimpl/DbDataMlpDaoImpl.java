package org.inteh.modbuslogger.database.daoimpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.inteh.modbuslogger.database.dao.DbDataMlpDao;
import org.inteh.modbuslogger.database.model.DbDataMlpModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DbDataMlpDaoImpl  extends DbDaoImplBase implements DbDataMlpDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DbDataMlpDaoImpl.class);

	@Override
	public DbDataMlpModel insert(DbDataMlpModel data) {
		LOGGER.debug("DAO insert Data {} from MLP device", data);
        try (SqlSession sqlSession = getSession()) {
            try {
                getDataMlpMapper(sqlSession).insert(data);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Device {} {}", data, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
		return data;
	}

	@Override
	public DbDataMlpModel getById(int id) {
		LOGGER.debug("DAO get Data from MLP device by ID {} from DB", id);
        try (SqlSession sqlSession = getSession()) {
            return getDataMlpMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Devices by ID {}. {}", id, ex);
            throw ex;
        }
	}
	
	@Override
	public List<DbDataMlpModel> getByDevId(int id) {
		LOGGER.debug("DAO get Data from MLP device by device ID {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getDataMlpMapper(sqlSession).getByDevId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Devices by ID {}. {}", id, ex);
            throw ex;
        }
	}

	@Override
	public void delete(int id) {
		LOGGER.debug("DAO delete Device with id {} ", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getDeviceMapper(sqlSession).delete(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Device with id {} {} ", id, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
	}

}
