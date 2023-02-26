package com.xwc1125.common.util.uuid;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Title: RankUtils
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 *
 * </p>
 *
 * @author xwc1125
 * @date 2015年9月8日上午10:41:40
 */
public class RankUtils {

    private static Logger log = LoggerFactory.getLogger(RankUtils.class);
    /**
     * <p>
     * Title: getRanKNumber
     * </p>
     * <p>
     * Description: 生成随机码
     * </p>
     * <p>
     *
     * </p>
     *
     * @tags @param numberLength 生成多少为的随机码
     * @tags @return
     * @author xwc1125
     * @date 2015年9月8日上午10:43:34
     */
    public static String getRanKNumber(int numberLength) {
        String sRand = "";
        try {
            Random random = new Random();
            for (int i = 0; i < numberLength; i++) {
                String rand = String.valueOf(random.nextInt(10));
                sRand += rand;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return sRand;
    }

    /**
     * 获取数据数据
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "0123456789" +
                "~!@#$%&*()_+=";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
