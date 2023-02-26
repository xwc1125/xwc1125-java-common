package com.xwc1125.common.util.json;

import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2021/1/13 15:36
 * @Copyright Copyright@2021
 */
public class StrToIntegerConverter extends StdConverter<String, Integer> {

    @Override
    public Integer convert(String val) {
        return Integer.parseInt(val);
    }
}
