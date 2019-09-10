package cn.edu.tjpu.pojo;

import java.io.Serializable;

/**
 * @author zhuangy
 * @version V1.0
 * @Title: Log
 * @Package cn.edu.tjpu.pojo
 * @Description: (用一句话描述该文件做什么)
 * @date 2019/9/8 22:17
 **/

public class Log implements Serializable {
    private Integer id;
    private String deviceAddress;
    private Float seedsNum;
    private Float seededArea;
    private String position;
    private Integer state;
    private Integer faultNum;

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public Float getSeedsNum() {
        return seedsNum;
    }

    public void setSeedsNum(Float seedsNum) {
        this.seedsNum = seedsNum;
    }

    public Float getSeededArea() {
        return seededArea;
    }

    public void setSeededArea(Float seededArea) {
        this.seededArea = seededArea;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getFaultNum() {
        return faultNum;
    }

    public void setFaultNum(Integer faultNum) {
        this.faultNum = faultNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
