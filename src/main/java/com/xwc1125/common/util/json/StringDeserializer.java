package com.xwc1125.common.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2021/3/23 12:21
 * @Copyright Copyright@2021
 */
public class StringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx)
            throws IOException, JsonProcessingException {
        String text = p.getText();
        if ("".equals(text)) {
            return null;
        }
        return text;
    }
}
