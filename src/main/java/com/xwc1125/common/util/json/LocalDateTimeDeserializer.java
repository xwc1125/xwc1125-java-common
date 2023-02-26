package com.xwc1125.common.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2021/3/23 12:23
 * @Copyright Copyright@2021
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctx)
            throws IOException, JsonProcessingException {
        long timestamp = p.getValueAsLong();
        if (timestamp > 0) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("+8"));
        } else {
            return null;
        }
    }

}
