package cn.edu.tjpu.pojo;

import java.io.Serializable;
import java.util.Date;

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
    private String state;
    private String fault;
    private Date createTime;
    private String msg;
    private Integer msgLen;

    public Integer getMsgLen() {
        return msgLen;
    }

    public void setMsgLen(Integer msgLen) {
        this.msgLen = msgLen;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
