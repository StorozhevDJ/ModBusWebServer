package org.inteh.modbuslogger.database.daoimpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.inteh.modbuslogger.database.dao.DbDeviceDao;
import org.inteh.modbuslogger.database.model.DbDeviceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DbDeviceDaoImpl  extends DbDaoImplBase implements DbDeviceDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DbDeviceDaoImpl.class);

	@Override
	public DbDeviceModel insert(DbDeviceModel device) throws Exception {
		LOGGER.debug("DAO insert Device {}", device);
        try (SqlSession sqlSession = getSession()) {
            try {
                getDeviceMapper(sqlSession).insert(device);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Device {} {}", device, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
		return device;
	}

	@Override
	public DbDeviceModel update(DbDeviceModel device) throws Exception {
		LOGGER.debug("DAO update Device {}", device);
        try (SqlSession sqlSession = getSession()) {
            try {
                getDeviceMapper(sqlSession).update(device);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update Device {} {}", device, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
		return device;
	}

	@Override
	public List<DbDeviceModel> getAll() throws Exception {
		LOGGER.debug("DAO get all Devices from DB");
        try (SqlSession sqlSession = getSession()) {
            return getDeviceMapper(sqlSession).getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all Devices {}", ex);
            throw ex;
        }
	}

	@Override
	public DbDeviceModel getById(int id) throws Exception {
		LOGGER.debug("DAO get Devices by ID {} from DB", id);
        try (SqlSession sqlSession = getSession()) {
            return getDeviceMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Devices by ID {}. {}", id, ex);
            throw ex;
        }
	}
	
	@Override
	public List<DbDeviceModel> getByType(int type) throws Exception {
		LOGGER.debug("DAO get Devices by Type {} from DB", type);
        try (SqlSession sqlSession = getSession()) {
            return getDeviceMapper(sqlSession).getByType(type);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Devices by Type {}. {}", type, ex);
            throw ex;
        }
	}

	@Override
	public void delete(int id) throws Exception {
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

	@Override
	public Integer getDeviceCount() throws Exception {
		LOGGER.debug("Get Count Device in DB {} ");
        try (SqlSession sqlSession = getSession()) {
            try {
            	return getDeviceMapper(sqlSession).getDeviceCount();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Device with id {} {} ", ex);
                throw ex;
            }
        }
	}

}
