package cn.edu.tjpu.dao.impl;

import cn.edu.tjpu.dao.LogDao;
import cn.edu.tjpu.pojo.Log;
import cn.edu.tjpu.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author zhuangy
 * @version V1.0
 * @Title: LogDaoImpl
 * @Package cn.edu.tjpu.dao.impl
 * @Description: (用一句话描述该文件做什么)
 * @date 2019/9/8 22:32
 **/

public class LogDaoImpl implements LogDao {
    private static final String namespace = "cn.edu.tjpu.dao.LogDao.";
    @Override
    public List<Log> getLogs() {
        List<Log> logs = null;
        SqlSession session = MyBatisUtil.getSession();
        try {
            logs = session.selectList(namespace + "getLogs", Log.class);
            //注意：此处有陷阱，如果做了更新、插入或删除操作，必须使用：
            //session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            MyBatisUtil.closeSession(session);
        }
        return logs;
    }
}
