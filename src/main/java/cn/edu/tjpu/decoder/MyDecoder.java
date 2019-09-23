package cn.edu.tjpu.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        //创建字节数组,buffer.readableBytes可读字节长度
        byte[] b = new byte[buffer.readableBytes()];
        //复制内容到字节数组b
        buffer.readBytes(b);
        //字节数组转字符串
        String outStr = bytesToHexString(b);
        StringBuilder sb = new StringBuilder();
        String[] strArr = new String[]{};
        if (outStr.contains("245F")) {
            strArr = outStr.split("245F");
        }
        List heartBeatMsgList = new ArrayList();
        List msgList = new ArrayList();
        Map msg = new HashMap<>();
        for (String msgStr : strArr) {
            if (msgStr.length() == 12) {
                heartBeatMsgList.add(msgStr);
            } else if (msgStr.length() == 54) {
                msgList.add(msgStr);
            }
        }
        msg.put("heartBeatMsgs", heartBeatMsgList);
        msg.put("msgs", msgList);
        out.add(msg);
    }

    public String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static String toHexString1(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }

    public static String toHexString1(byte b) {
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1) {
            return "0" + s;
        } else {
            return s;
        }
    }
}