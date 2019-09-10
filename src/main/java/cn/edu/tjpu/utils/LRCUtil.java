package cn.edu.tjpu.utils;

/**
 * @author zhuangy
 * @version V1.0
 * @Title: LRCUtil
 * @Package cn.edu.tjpu.utils
 * @Description: LRC校验工具类
 * @date 2019/9/8 15:38
 **/

public class LRCUtil {
    //已知一个字符串,求该字符串的16进制累加和，已知一段字符串和校验码，校验和累加是否合法，话不多说了直接上代码。
    //和校验结果(HEX)：16进制单字节累加和校验值（校验和,checksum）；
    public static String makeChecksum(String data) {
        if (data == null || data.equals("")) {
            return "";
        }
        int total = 0;
        int len = data.length();
        int num = 0;
        while (num < len) {
            String s = data.substring(num, num + 2);
            total += Integer.parseInt(s, 16);
            num = num + 2;
        }
        /**
         * 用256求余最大是255，即16进制的FF
         */
        int mod = total % 256;
        String hex = Integer.toHexString(mod);
        len = hex.length();
        // 如果不够校验位的长度，补0,这里用的是两位校验
        if (len < 2) {
            hex = "0" + hex;
        }
        return hex;
    }

    public static String checkLrc(String data){
        //和校验结果(HEX)：16进制单字节累加和校验值（校验和,checksum）；
        String sum = makeChecksum(data);
        //16进制单字节累加和校验值，取反+1（即：0x00-校验字节一）（LRC校验）；
        Integer decimalNum = Integer.parseInt(sum,16);//十进制
        System.out.println(~decimalNum);
        return null;
    }

    public static void main(String[] args) {
        System.out.println(checkLrc("58A11710010000000000000000451C38EA424EB2431C42000000"));
    }
}
