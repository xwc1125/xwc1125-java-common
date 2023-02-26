package com.xwc1125.common.util.binary;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 * Title: BinaryUtils
 * </p>
 * <p>
 * Description: 二进制处理通用类
 * </p>
 * <p>
 *
 * </p>
 *
 * @author xwc1125
 * @date 2015-7-15上午9:42:17
 */
public class BinaryUtils {
    /**
     * <p>
     * Title: toHexStr
     * </p>
     * <p>
     * Description: 将字符串转化为二进制
     * </p>
     * <p>
     *
     * </p>
     *
     * @param s
     * @return
     * @author xwc1125
     * @date 2015-7-14下午4:49:48
     */
    public static String toHex(String s) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            buf.append(toHex((int) s.charAt(i)));
        }
        return buf.toString();
    }

    /**
     * <p>
     * Title: toHexStr
     * </p>
     * <p>
     * Description: 二进制转为字符串
     * </p>
     * <p>
     *
     * </p>
     *
     * @param i
     * @return
     * @author xwc1125
     * @date 2015-7-14下午4:50:16
     */
    public static String toHex(int i) {
        String s = Integer.toHexString(i);
        if (s.length() % 2 == 1) {
            s = "0" + s;
        }
        return s.toUpperCase();
    }

    public static int byte2Int(byte[] res) {
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    /**
     * 从一个byte[]数组中截取一部分
     *
     * @param src
     * @param begin
     * @param count
     * @return
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++) {
            bs[i - begin] = src[i];
        }
        return bs;
    }

    /**
     * 16进制字符串转换成byte数组
     *
     * @param hex 16进制字符串
     * @return 转换后的byte数组
     */
    public static byte[] hex2Bytes(String hex) {
        String digital = "0123456789ABCDEF";
        char[] hex2char = hex.toCharArray();
        byte[] bytes = new byte[hex.length() / 2];
        int temp;
        for (int i = 0; i < bytes.length; i++) {
            // 其实和上面的函数是一样的 multiple 16 就是右移4位 这样就成了高4位了
            // 然后和低四位相加， 相当于 位操作"|"
            // 相加后的数字 进行 位 "&" 操作 防止负数的自动扩展. {0xff byte最大表示数}
            temp = digital.indexOf(hex2char[2 * i]) * 16;
            temp += digital.indexOf(hex2char[2 * i + 1]);
            bytes[i] = (byte) (temp & 0xff);
        }
        return bytes;
    }

    /**
     * <p>
     * Title: toHexString
     * </p>
     * <p>
     * Description: 用十六进制（基数 16）参数表示的无符号整数值的字符串表示形式。
     * </p>
     * <p>
     * 转化字符串为十六进制编码
     * </p>
     *
     * @param s
     * @return
     * @author xwc1125
     * @date 2016年8月31日 下午2:05:35
     */
    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * <p>
     * Title: toStringHex
     * </p>
     * <p>
     * Description: 转化十六进制编码为字符串
     * </p>
     * <p>
     *
     * </p>
     *
     * @param s
     * @return
     * @author xwc1125
     * @date 2016年8月31日 下午2:07:57
     */
    public static String toStringHex(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public static void main(String[] args) {
        try {
            System.out.println(encode("中文", "utf-8"));
            System.out.println(decode(encode("中文", "utf-8"), "utf-8"));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /*
     * 16进制数字字符集
     */
    private static String hexString = "0123456789ABCDEF";

    /*
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     */
    public static String encode(String str, String charset)
            throws UnsupportedEncodingException {
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes(charset);
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    public static String encode(byte[] bytes)
            throws UnsupportedEncodingException {
        // 根据默认编码获取字节数组
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }


    /*
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static String decode(String bytes, String charset)
            throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                    .indexOf(bytes.charAt(i + 1))));
        return new String(baos.toByteArray(), charset);
    }

    public static byte[] decode(String bytes)
            throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                    .indexOf(bytes.charAt(i + 1))));
        return baos.toByteArray();
    }


    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
}
