package org.inteh.modbuslogger.database.daoimpl;


import org.apache.ibatis.session.SqlSession;
import org.inteh.modbuslogger.database.mappers.DbDataMlpMapper;
import org.inteh.modbuslogger.database.mappers.DbDeviceMapper;
import org.inteh.modbuslogger.utils.MyBatisUtils;



public class DbDaoImplBase {

    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected DbDeviceMapper getDeviceMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(DbDeviceMapper.class);
    }

    protected DbDataMlpMapper getDataMlpMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(DbDataMlpMapper.class);
    }
    

}