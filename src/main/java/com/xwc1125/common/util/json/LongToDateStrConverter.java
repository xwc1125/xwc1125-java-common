package com.xwc1125.common.util.json;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.xwc1125.common.util.date.DateFormat;
import com.xwc1125.common.util.date.DateUtils;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2021/1/13 15:01
 * @Copyright Copyright@2021
 */
public class LongToDateStrConverter extends StdConverter<Long, String> {

    @Override
    public String convert(Long val) {
        return DateUtils.FormatDate(val, DateFormat.YYYY_MM_dd8HH0mm0ss);
    }
}
