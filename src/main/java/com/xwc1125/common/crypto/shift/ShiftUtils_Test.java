package com.xwc1125.common.crypto.shift;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2022/1/28 11:51
 * @Copyright Copyright@2022
 */
public class ShiftUtils_Test {

    public static void main(String[] args) {
        String aa = ShiftUtils.getKeyValue("123456www.xwc1125.com", 3);
        System.out.println(aa);
    }
}
