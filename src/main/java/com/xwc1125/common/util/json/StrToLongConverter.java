package com.xwc1125.common.util.json;

import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2021/1/13 15:01
 * @Copyright Copyright@2021
 */
public class StrToLongConverter extends StdConverter<String, Long> {

    @Override
    public Long convert(String val) {
        return Long.parseLong(val);
    }
}
