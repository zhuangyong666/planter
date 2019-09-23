package cn.edu.tjpu.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IEEE754Util {
    /**
     * IEEE754标准的单精度实数转化为16进制表示
     *
     * @param a
     * @return String
     */
    public static String floatToHex(float a) {
        int c = Float.floatToIntBits(a);
        return Integer.toHexString(c);
    }

    /**
     * IEEE754标准的双精度实数转化为16进制表示
     *
     * @param a
     * @return String
     */
    public static String doubleToHex(double a) {
        long c = Double.doubleToLongBits(a);
        return Long.toHexString(c);
    }

    /**
     * 从16进制表示转化为IEEE754标准的双精度实数
     *
     * @param a
     * @return double
     */
    public static double hexToDouble(long a) {
        double b = Double.longBitsToDouble(a);
        return b;
    }

    /**
     * 从16进制表示转化为IEEE754标准的单精度实数
     *
     * @param a
     * @return float
     */
    public static float hexToFloat(int a) {
        float b = Float.intBitsToFloat(a);
        return b;
    }

    /**
     * 从16进制表示转化为IEEE754标准的单精度实数
     *
     * @param a
     * @return float
     */
    public static float hexStrToFloat(String hex) {
        Integer a = Integer.parseInt(hex,16);
        float b = Float.intBitsToFloat(a);
        return b;
    }
}