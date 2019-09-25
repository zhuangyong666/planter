package cn.edu.tjpu.dao;

import cn.edu.tjpu.pojo.Device;

import java.util.List;

public interface DeviceDao {
    public void batchInsertDevice(List<Device> devices);
}
