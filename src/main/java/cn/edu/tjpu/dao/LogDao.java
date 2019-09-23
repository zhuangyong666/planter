package cn.edu.tjpu.dao;

import cn.edu.tjpu.pojo.Log;

import java.util.List;

/**
 * @author zhuangy
 * @version V1.0
 * @Title: LogMapper
 * @Package cn.edu.tjpu.dao
 * @Description: (用一句话描述该文件做什么)
 * @date 2019/9/8 22:29
 **/

public interface LogDao {
    public void batchInsertLog(List<Log> logs);
}
