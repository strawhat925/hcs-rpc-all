package code.hcs.rpc.util;

import code.hcs.rpc.Constants;

/**
 *
 * 自定义tcp协议报文格式
 * -------------------------------------------------------------------------------------------------
 * header 16
 * -------------------------------------------------------------------------------------------------
 * |  标记位          |  magic    |  flag      |  version    |   requestId    |  body length   |
 * |  二进制位数bit   |   0-15    |  16-23     |    24-31    |     32-95      |     96-127     |
 * |  字节数          |   2       |    1       |     1       |      8         |       4        |
 * -------------------------------------------------------------------------------------------------
 *
 * body
 *
 * -------------------------------------------------------------------------------------------------
 */
public class ByteUtil {


    /**
     * 将short类型转换为从off开始两个字节的byte数组，高位在前
     */
    public static void short2byte(byte[] bytes, short value, int off) {
        bytes[off + 1] = (byte) value;
        bytes[off] = (byte) (value >>> 8);
    }


    /**
     * 将byte数组从off开始的两个字节转换为short类型
     */
    public static short byte2short(byte[] bytes, int off) {

        return (short) ((bytes[off] & 0xFF) + ((bytes[off + 1] & 0xFF) << 8));
    }


    /**
     * 将long类型转换为从off开始八个字节的byte数组，高位在前
     */
    public static void long2byte(byte[] bytes, long value, int off) {
        bytes[off + 7] = (byte) value;
        bytes[off + 6] = (byte) (value >>> 8);
        bytes[off + 5] = (byte) (value >>> 16);
        bytes[off + 4] = (byte) (value >>> 24);
        bytes[off + 3] = (byte) (value >>> 32);
        bytes[off + 2] = (byte) (value >>> 40);
        bytes[off + 1] = (byte) (value >>> 48);
        bytes[off] = (byte) (value >>> 56);
    }


    /**
     * 将byte数组从off开始的八个字节转换为long类型
     * @param bytes
     * @param off
     * @return
     */
    public static long byte2long(byte[] bytes, int off) {

        return ((bytes[off + 7] & 0xFF)) + ((bytes[off + 6] & 0xFF) << 8) + ((bytes[off + 5]) << 16) + ((bytes[off + 4]) << 24) + ((bytes[off + 3]) << 32) + ((bytes[off + 2]) << 40) + ((bytes[off + 1]) << 48) + ((bytes[off]) << 56);
    }


    /**
     * 将int类型转换为从off开始四个字节的byte数组，高位在前
     */
    public static void int2byte(byte[] bytes, int value, int off) {
        bytes[off + 3] = (byte) value;
        bytes[off + 2] = (byte) (value >>> 8);
        bytes[off + 1] = (byte) (value >>> 16);
        bytes[off] = (byte) (value >>> 24);
    }


    /**
     * 将byte数组从off开始的四个字节转换为int类型
     * @param bytes
     * @param off
     * @return
     */
    public static int byte2int(byte[] bytes, int off){

        return ((bytes[off + 3] & 0xFF)) + ((bytes[off + 2] & 0xFF) << 8) + ((bytes[off + 1] & 0xFF) << 16) + ((bytes[off] & 0xFF) << 24);
    }


    public static void main(String[] args) {

        /**
         * >>>: 无符号右移，忽略符号位，空位都以0补齐
         * int a = 16;
         * int b = 2;
         * System.out.println(a >>> b) 结果为4
         *
         * >>: 保留符号右移运算符，右移运算符，num >> 1,相当于num除以2
         * int a = 16;
         * int c = -16;
         * int b = 2;
         * int d = 2;
         * System.out.println(a >> b) 结果为4
         * SYstem.out.println(c >> d) 结果为-4
         *
         * <<: 保留符号左移运算符，左移运算符，num << 1,相当于num乘以2
         * int a = 16;
         * int b = 2;
         * System.out.println(a << b);*/

        int b = 16, a = 2, c = -16;
        int MAGIC_MASK = 0xFF;

        System.out.println(b >>> a);
        System.out.println(b << a);

        System.out.println(MAGIC_MASK);


        byte[] data = new byte[2];
        short magic = (short) 0xF0F0;

        //0xF0F0  --- >  0 * 16^0 + 15 * 16^1 + 0 * 16^2 + 15 * 16^3 = 61680     short result = short(61680); result = -3856

        ByteUtil.short2byte(data, magic, 0);

        short result = ByteUtil.byte2short(data, 0);
        System.out.println(magic + "=================" + result);

        byte[] data1 = new byte[8];
        long flag = 23423423;
        ByteUtil.long2byte(data1,flag,0);

        long result1 = ByteUtil.byte2long(data1,0);
        System.out.println(flag + "=================" + result1);

        byte[] data2 = new byte[4];
        int length = 26;
        ByteUtil.int2byte(data2,length,0);

        int result2 = ByteUtil.byte2int(data2,0);
        System.out.println(length + "=================" + result2);

        System.out.println((byte) Constants.FLAG_REQUEST);


        int t = 0x005;
        System.out.println("0421551375650816".length());

        String timestamp = "1483062663457";
        long oldTime = Long.parseLong(timestamp);
        long newTime = System.currentTimeMillis();
        System.out.println((newTime - oldTime) / (1000 * 60));
    }
}

