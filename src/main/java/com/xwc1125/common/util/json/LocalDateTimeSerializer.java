package com.xwc1125.common.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2021/3/23 12:21
 * @Copyright Copyright@2021
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            long millis = value.atZone(ZoneId.of("+8")).toInstant().toEpochMilli();
            gen.writeNumber(millis);
        }
    }

}
