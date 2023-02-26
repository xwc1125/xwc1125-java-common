package com.xwc1125.common.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2021/3/23 12:20
 * @Copyright Copyright@2021
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            long millis = value.atStartOfDay(ZoneId.of("+8")).toInstant().toEpochMilli();
            gen.writeNumber(millis);
        }

    }
}
