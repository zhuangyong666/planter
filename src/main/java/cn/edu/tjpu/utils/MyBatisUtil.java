package cn.edu.tjpu.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {
    private static final String CONFIGFILE = "mybatis-config.xml";
    private static SqlSessionFactory sqlSessionFactory;
    private final static Log LOGGER = LogFactory.getLog(MyBatisUtil.class);

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    /**
     * 创建连接
     */
    public static SqlSession getSession() {
        SqlSession session = null;
        try {
            session = sqlSessionFactory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

    public static void closeSession(SqlSession session) {
        session.close();
    }

    /*
     * @Description: 初始化数据源
     * @author zhuangy
     * @time 2019/9/9 14:13
     */
    public static boolean initDataSource() {
        InputStream in = null;
        try {
            in = Resources.getResourceAsStream(CONFIGFILE);
            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("初始化数据源失败", e);
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}