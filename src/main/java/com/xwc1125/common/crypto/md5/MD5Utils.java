package com.xwc1125.common.crypto.md5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * <p>
 * Title: MD5Util
 * </p>
 * <p>
 * Description: MD5加密逻辑处理类
 * </p>
 * <p>
 *
 * </p>
 *
 * @author xwc1125
 * @date 2015-7-20下午2:00:05
 */
public class MD5Utils {
    final static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};

    public MD5Utils() {
        throw new AssertionError();
    }

    public final static String compute(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }

        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    /**
     * <p>
     * Title: Encrypt
     * </p>
     * <p>
     * Description: MD5加密
     * </p>
     * <p>
     *
     * </p>
     *
     * @param inStr
     * @return
     * @author xwc1125
     * @date 2015-7-20下午2:02:23
     */
    public final static String Encrypt(String inStr) {
        try {
            byte[] strTemp = inStr.getBytes("utf-8");
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get MD5 of a file (lower case)
     *
     * @return empty string if I/O error when get MD5
     */
    public static String Encrypt(File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            return MD5(ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length()));
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            return "";
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // 关闭流产生的错误一般都可以忽略
                }
            }
        }

    }

    /**
     * MD5校验字符串
     *
     * @param s String to be MD5
     * @return 'null' if cannot get MessageDigest
     */

    private static String getStringMD5(String s) {
        MessageDigest mdInst;
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            mdInst = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

        byte[] btInput = s.getBytes();
        // 使用指定的字节更新摘要
        mdInst.update(btInput);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        int length = md.length;
        char str[] = new char[length * 2];
        int k = 0;
        for (byte b : md) {
            str[k++] = hexDigits[b >>> 4 & 0xf];
            str[k++] = hexDigits[b & 0xf];
        }
        return new String(str);
    }


    @SuppressWarnings("unused")
    private static String getSubStr(String str, int subNu, char replace) {
        int length = str.length();
        if (length > subNu) {
            str = str.substring(length - subNu, length);
        } else if (length < subNu) {
            // NOTE: padding字符填充在字符串的右侧，和服务器的算法是一致的
            str += createPaddingString(subNu - length, replace);
        }
        return str;
    }


    private static String createPaddingString(int n, char pad) {
        if (n <= 0) {
            return "";
        }

        char[] paddingArray = new char[n];
        Arrays.fill(paddingArray, pad);
        return new String(paddingArray);
    }

    /**
     * 计算MD5校验
     *
     * @param buffer
     * @return 空串，如果无法获得 MessageDigest实例
     */

    private static String MD5(ByteBuffer buffer) {
        String s = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(buffer);
            byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, >>>,
                // 逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            s = new String(str); // 换后的结果转换为字符串

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static void main(String[] args) {
        String string = "%=od4niTmh~)~f~i/app/eth/balance/0x0e10d1b1ae10ae124939ff657f96836c34b42f10?app=64218dc2f062c2445f43f26db3853b00645bba4ff5307f5f47b24a5528198520d81e0c90f5c6f2ec693d1d1245b5faf086a185fc460c58755511056076a8681967dca314f62c310212b37efbdba8f64123748ea63aa889fa4233398efa790fc9eadefb634feff35bd79c327b8be0668f2904bc76437e9a6f4712317d69fe5bcf27fd33d9ef6ab04af149c151ba50cecb&data=487036f870dff7d4e320516fefc8ae7beea0def8964f652cef2655813d0a8cf1a8388497f1ff32ce1eb72932078ddf72&sim=8222707cf170c50ca0e01453972fa1c66b3a37fb7a98c4c716e4dac8d1a41489b64cbbd156c1305f18d6d7e5de9b029bad36e454da2f57f029739da3c48877282783307ecc2dc2c3479846f1122ca1a9";
        String md5 = MD5Utils.Encrypt(string);
        System.out.print(md5);
    }
}
