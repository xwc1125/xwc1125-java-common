package com.xwc1125.common.crypto.shift;

import com.xwc1125.common.util.string.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author xwc1125
 * @ClassName ShiftUtils
 * @Description TODO(describe the types)
 * @date 2016年3月29日 上午8:58:54
 */
public class ShiftUtils {

    /**
     * 密钥缓存
     */
    private static HashMap<String, String> securityCache = new HashMap<String, String>();

    /**
     * <p>
     * Title: MODE_SECURITY
     * </p>
     * <p>
     * Description: 密钥类别
     * </p>
     * <p>
     *
     * </p>
     *
     * @author xwc1125
     * @date 2016年3月24日上午11:34:22
     */
    public enum MODE_SECURITY {

        /**
         * aes加解密密钥</br>
         */
        AES {
            @Override
            public String getKey(String aesKey, int tag) {
                String keyValue = null;
                try {
                    keyValue = securityCache.get("aes" + tag);
                    if (StringUtils.isEmpty(keyValue)) {
                        keyValue = getKeyValue(aesKey, tag);
                        securityCache.put("aes" + tag, keyValue);
                    }
                    return keyValue;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        },
        /**
         * md5加解密密钥</br>
         */
        MD5 {
            @Override
            public String getKey(String md5Key, int tag) {
                String keyValue = null;
                try {
                    keyValue = securityCache.get("md5" + tag);
                    if (StringUtils.isEmpty(keyValue)) {
                        keyValue = getKeyValue(md5Key, tag);
                        securityCache.put("md5" + tag, keyValue);
                    }
                    return keyValue;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        public abstract String getKey(String key, int tag);

    }

    /**
     * <p>
     * Title: getKeyValue
     * </p>
     * <p>
     * Description:密钥处理
     * </p>
     * <p>
     *
     * </p>
     *
     * @param key 密钥
     * @param tag 位移量
     * @return
     * @author xwc1125
     * @date 2016年3月28日下午6:23:54
     */
    public static String getKeyValue(String key, int tag) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        char[] charKey = key.toCharArray();
        ArrayList<Character> aeskey = new ArrayList<Character>();
        for (int i = 0; i < charKey.length; i++) {
            aeskey.add(charKey[i]);
        }
        char[] result = new char[charKey.length];
        int k = 0;
        ArrayList<Integer> aesRemoveIndexs = new ArrayList<Integer>();
        for (int i = tag + 1; i > 0; i--) {
            int size = aeskey.size();
            aesRemoveIndexs.clear();
            for (int j = 0; j < size; j++) {
                if (j % i == 0) {
                    if (k < charKey.length) {
                        result[k++] = aeskey.get(j);
                        aesRemoveIndexs.add(j);
                    } else {
                        break;
                    }
                }
            }
            for (int l = 0; l < aesRemoveIndexs.size(); l++) {
                int removeIndex = aesRemoveIndexs.get(l);
                aeskey.remove(removeIndex - l);
            }
        }
        return new String(result);
    }
}
