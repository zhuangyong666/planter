package cn.edu.tjpu.dao.impl;

import cn.edu.tjpu.dao.DeviceDao;
import cn.edu.tjpu.pojo.Device;
import cn.edu.tjpu.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class DeviceDaoImpl implements DeviceDao {
    private static final String namespace = "cn.edu.tjpu.dao.DeviceDao.";
    @Override
    public void batchInsertDevice(List<Device> devices) {
        SqlSession session = MyBatisUtil.getSession();
        try {
            session.insert(namespace + "batchInsertDevice", devices);
            //注意：此处有陷阱，如果做了更新、插入或删除操作，必须使用：
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtil.closeSession(session);
        }
    }
}
