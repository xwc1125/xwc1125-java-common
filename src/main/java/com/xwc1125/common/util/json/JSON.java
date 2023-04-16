package com.xwc1125.common.util.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.xwc1125.common.util.spring.SpringUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: JSON解析处理
 * @Author: xwc1125
 * @Date: 2021/2/23 17:29
 * @Copyright Copyright@2021
 */
public class JSON {

    public static final String DEFAULT_FAIL = "\"Parse failed\"";
    private static ObjectMapper objectMapper;
    private static ObjectWriter objectWriter;
    private static HashMap<PropertyNamingStrategy, ObjectMapper> extraObjectMapper = new HashMap<>();

    public static void setObjectMapper(ObjectMapper objectMapper) {
        JSON.objectMapper = objectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            try {
                objectMapper = SpringUtils.getBean("jacksonObjectMapper");
            } catch (Exception e) {
            }
            if (objectMapper == null) {
                try {
                    objectMapper = SpringUtils.getBean("objectMapper");
                } catch (Exception e) {
                }
            }
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
            }
        }
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static ObjectMapper getObjectMapper(Boolean isSnake) {
        PropertyNamingStrategy strategy = PropertyNamingStrategy.LOWER_CAMEL_CASE;
        if (isSnake) {
            strategy = PropertyNamingStrategy.SNAKE_CASE;
        }
        return getObjectMapper(strategy);
    }

    public static ObjectMapper getObjectMapper(PropertyNamingStrategy strategy) {
        if (extraObjectMapper.containsKey(strategy)) {
            return extraObjectMapper.get(strategy);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 注意是否开启
        mapper.setPropertyNamingStrategy(strategy);
        extraObjectMapper.put(strategy, mapper);
        return mapper;
    }

    public static ObjectWriter getObjectWriter() {
        if (objectWriter == null) {
            objectWriter = getObjectMapper().writerWithDefaultPrettyPrinter();
        }
        return objectWriter;
    }

    public static void marshal(File file, Object value) throws Exception {
        try {
            getObjectWriter().writeValue(file, value);
        } catch (JsonGenerationException e) {
            throw new Exception(e);
        } catch (JsonMappingException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static void marshal(OutputStream os, Object value) throws Exception {
        try {
            getObjectWriter().writeValue(os, value);
        } catch (JsonGenerationException e) {
            throw new Exception(e);
        } catch (JsonMappingException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static String marshal(Object value) throws Exception {
        try {
            return getObjectWriter().writeValueAsString(value);
        } catch (JsonGenerationException e) {
            throw new Exception(e);
        } catch (JsonMappingException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static byte[] marshalBytes(Object value) throws Exception {
        try {
            return getObjectWriter().writeValueAsBytes(value);
        } catch (JsonGenerationException e) {
            throw new Exception(e);
        } catch (JsonMappingException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static <T> T unmarshal(File file, Class<T> valueType) throws Exception {
        try {
            return getObjectMapper().readValue(file, valueType);
        } catch (JsonParseException e) {
            throw new Exception(e);
        } catch (JsonMappingException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static <T> T unmarshal(InputStream is, Class<T> valueType) throws Exception {
        try {
            return getObjectMapper().readValue(is, valueType);
        } catch (JsonParseException e) {
            throw new Exception(e);
        } catch (JsonMappingException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static <T> List<T> unmarshalList(String strList, Class<T> valueType) throws Exception {
        try {
            return getObjectMapper().readValue(strList,
                    new TypeReference<List<T>>() {
                    });
        } catch (JsonParseException e) {
            throw new Exception(e);
        } catch (JsonMappingException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static Object unmarshal(String str, TypeReference valueType) throws Exception {
        try {
            return getObjectMapper().readValue(str, valueType);
        } catch (JsonParseException e) {
            throw new Exception(e);
        } catch (JsonMappingException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static <T> T unmarshal(String str, Class<T> valueType) throws Exception {
        try {
            return getObjectMapper().readValue(str, valueType);
        } catch (JsonParseException e) {
            throw new Exception(e);
        } catch (JsonMappingException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static <T> T unmarshal(byte[] bytes, Class<T> valueType) throws Exception {
        try {
            if (bytes == null) {
                bytes = new byte[0];
            }
            return getObjectMapper().readValue(bytes, 0, bytes.length, valueType);
        } catch (JsonParseException e) {
            throw new Exception(e);
        } catch (JsonMappingException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }
}
