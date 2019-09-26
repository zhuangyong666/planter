package cn.edu.tjpu.serverhandler;

import cn.edu.tjpu.dao.DeviceDao;
import cn.edu.tjpu.dao.LogDao;
import cn.edu.tjpu.dao.impl.DeviceDaoImpl;
import cn.edu.tjpu.dao.impl.LogDaoImpl;
import cn.edu.tjpu.pojo.Device;
import cn.edu.tjpu.utils.ByteUtils;
import cn.edu.tjpu.utils.IEEE754Util;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PlanterServerHandler extends ChannelInboundHandlerAdapter {
    private static final Log LOGGER = LogFactory.getLog(PlanterServerHandler.class);
    private LogDao logDao = new LogDaoImpl();
    private DeviceDao deviceDao = new DeviceDaoImpl();

    /*
     * channelAction
     *
     * channel 通道 action 活跃的
     *
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     *
     */
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + " 通道已激活！");
    }

    /*
     * channelInactive
     *
     * channel 通道 Inactive 不活跃的
     *
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     *
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + " 通道不活跃！");
        // 关闭流

    }

    /**
     * 功能：读取服务器发送过来的信息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Map<String, List> messages = (Map) msg;
        List<String> heartBeatMsgList = messages.get("heartBeatMsgs");
        // 返回心跳应答消息
        if (!heartBeatMsgList.isEmpty()) {
            LOGGER.info("Receive client heart beat message : ---> "
                    + heartBeatMsgList);
            ctx.writeAndFlush("pong");
        } else {
            ctx.fireChannelRead(msg);
        }
        List<Device> deviceList = new ArrayList<>();
        for (String heartBeatMsg : heartBeatMsgList) {
            Device device = new Device();
            device.setDeviceAddress(heartBeatMsg.substring(4, 8));
            device.setState(1);
            deviceList.add(device);
        }
        if (!deviceList.isEmpty()) {
            deviceDao.batchInsertDevice(deviceList);
        }
        Date now = new Date();
        List<String> msgList = messages.get("msgs");
        List<cn.edu.tjpu.pojo.Log> logList = new ArrayList<>();
        for (String requestParam : msgList) {
            //接收字符串时的处理
            //头
            String head = requestParam.substring(0, 4);
            //数据包长度
            int len = ByteUtils.hexToDecimal(requestParam.substring(4, 6));
            //设备地址
            String deviceAddress = requestParam.substring(6, 10);
            //播种数
            Float seedsNum = IEEE754Util.hexStrToFloat(ByteUtils.reverse(requestParam.substring(10, 18)));
            //播种面积
            Float seedsArea = IEEE754Util.hexStrToFloat(ByteUtils.reverse(requestParam.substring(18, 26)));
            //经度标志
            String longitudeFlag = new String(ByteUtils.hexStr2Bytes(requestParam.substring(26, 28)));
            //经度
            Float longitude = IEEE754Util.hexStrToFloat(ByteUtils.reverse(requestParam.substring(28, 36)));
            //纬度标志
            String latitudeFlag = new String(ByteUtils.hexStr2Bytes(requestParam.substring(36, 38)));
            //纬度
            Float latitude = IEEE754Util.hexStrToFloat(ByteUtils.reverse(requestParam.substring(38, 46)));
            //播种机下种状态（0：正常下种，1：下种故障）
            String state = requestParam.substring(46, 48);
            //播种机下种故障状态累计
            String fault = requestParam.substring(48, 52);

            cn.edu.tjpu.pojo.Log log = new cn.edu.tjpu.pojo.Log();
            log.setMsg(requestParam);
            log.setMsgLen(len);
            log.setDeviceAddress(deviceAddress);
            log.setSeedsNum(seedsNum);
            log.setSeededArea(seedsArea);
            log.setPosition(longitudeFlag + " " + longitude + " " + latitudeFlag + " " + latitude);
            log.setState(state);
            log.setFault(fault);
            log.setCreateTime(now);
            logList.add(log);
            System.out.println(now.toString() + "客户端收到服务器数据:" + requestParam);
        }
        if (!logList.isEmpty()) {
            logDao.batchInsertLog(logList);
        }
    }

    /**
     * 功能：读取完毕客户端发送过来的数据之后的操作
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接收数据完毕..");
        // 第一种方法：写一个空的buf，并刷新写出区域。完成后关闭sock channel连接。
        //ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        // ctx.flush();
        // ctx.flush(); //
        // 第二种方法：在client端关闭channel连接，这样的话，会触发两次channelReadComplete方法。
        // ctx.flush().close().sync(); // 第三种：改成这种写法也可以，但是这中写法，没有第一种方法的好。
    }

    /**
     * 功能：服务端发生异常的操作
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }


    public static void main(String[] args) {
        System.out.println();
    }
}