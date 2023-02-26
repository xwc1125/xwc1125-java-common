package com.xwc1125.common.util.emoji;

import com.xwc1125.common.util.base64.Base64Utils;

import java.io.UnsupportedEncodingException;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2019-02-20 17:14
 * @Copyright Copyright@2019
 */
public class EmojiUtils {

    /**
     * 去掉特殊符号
     *
     * @param str
     * @return
     */
    public static String removeEmojiUnicode(String str) {
        if (str == null) {
            return null;
        }
        str = str.replaceAll("[^\\u0000-\\uFFFF]", "");
        return str;
    }

    /**
     * 入库之前，使用 Base64 编码
     *
     * @param str
     * @return
     */
    public static String emojiToBase64(String str) {
        try {
            return Base64Utils.encode(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return removeEmojiUnicode(str);
        }
    }

    /**
     * 出库后，使用 Base64 解码
     *
     * @param str
     * @return
     */
    public static String emojiFromBase64(String str) {
        try {
            return new String(Base64Utils.decode(str), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return removeEmojiUnicode(str);
        }
    }
}
