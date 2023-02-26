package com.xwc1125.common.util.reflect;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2021/1/22 15:50
 * @Copyright Copyright@2021
 */
public class BeanUtils {

    /**
     * 获取所有的Field
     *
     * @param clazz
     * @return
     */
    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 获取对象的字段列表
     *
     * @param c 操作类。用于获取类中的方法
     * @return
     */
    public static String[] getFields(Class<?> c) {
        return getFields(c, null);
    }

    /**
     * 获取对象的字段列表
     *
     * @param c           操作类。用于获取类中的方法
     * @param filterField 需要过滤的字段
     * @return
     */
    public static String[] getFields(Class<?> c, String[] filterField) {
        // 获取类中的全部定义字段
        Field[] fields = getAllFields(c);
        ArrayList<String> fieldNames = new ArrayList<String>();
        // 循环遍历字段，获取字段相应的属性值
        for (Field field : fields) {
            // 假设不为空。设置可见性
            field.setAccessible(true);
            String fieldName = field.getName();
            if (fieldName.equals("serialVersionUID")) {
                continue;
            }
            boolean isFilter = false;
            if (filterField != null && filterField.length > 0) {
                for (String f : filterField) {
                    if (fieldName.equalsIgnoreCase(f)) {
                        isFilter = true;
                        break;
                    }
                }
            }
            if (!isFilter) {
                fieldNames.add(fieldName);
            }
        }

        String[] strings = new String[fieldNames.size()];
        fieldNames.toArray(strings);
        return strings;
    }

    /**
     * 获取对象的字段列表
     *
     * @param obj 操作对象
     * @return
     */
    public static FieldValues getFieldValues(Object obj) {
        return getFieldValues(obj, null);
    }

    /**
     * 获取对象的字段列表
     *
     * @param obj         操作对象
     * @param filterField 需要过滤的字段
     * @return
     */
    public static FieldValues getFieldValues(Object obj, String[] filterField) {
        // 获取类中的全部定义字段
        Class<?> cls = obj.getClass();

        Field[] fields = getAllFields(cls);
        FieldValues fieldValues = new FieldValues();

        // 循环遍历字段，获取字段相应的属性值
        for (Field field : fields) {
            // 假设不为空。设置可见性
            field.setAccessible(true);
            String fieldName = field.getName();
            boolean isFilter = false;
            if (fieldName.equals("serialVersionUID")) {
                continue;
            }
            if (filterField != null && filterField.length > 0) {
                for (String f : filterField) {
                    if (fieldName.equalsIgnoreCase(f)) {
                        isFilter = true;
                        break;
                    }
                }
            }
            if (!isFilter) {
                try {
                    Object o = field.get(obj);
                    fieldValues.addFieldValue(fieldName, o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return fieldValues;
    }

    /**
     * 将KV转换到对象上
     *
     * @param cls
     * @param fields
     * @param values
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T parseFieldValues(Class<T> cls, String[] fields, List<Object> values) throws Exception {
        FieldValues fieldValues1 = new FieldValues();
        fieldValues1.setFields(Arrays.asList(fields));
        fieldValues1.setValues(values);
        return parseFieldValues(cls, fieldValues1);
    }


    /**
     * 获取构造函数
     *
     * @param clazz
     * @param parameterTypes
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     */
    public static <T> Constructor<T> accessibleConstructor(Class<T> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        Constructor<T> ctor = clazz.getDeclaredConstructor(parameterTypes);
        ReflectionUtils.makeAccessible(ctor);
        return ctor;
    }

    /**
     * 将KV转换到对象上(只能适用于无参数构造函数)
     *
     * @param cls         被操作的类
     * @param fieldValues
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T parseFieldValues(Class<T> cls, FieldValues fieldValues) throws Exception {
        T instance = cls.newInstance();
        return parseFieldValues(instance, fieldValues);
    }

    /**
     * 将KV转换到对象上
     *
     * @param cls            被操作的类
     * @param fieldValues
     * @param parameterTypes 如果是有参数构造函数，那么需要填写其参数类型
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T parseFieldValues(Class<T> cls, FieldValues fieldValues, Class<?>... parameterTypes) throws Exception {
        Constructor con = cls.getConstructor(parameterTypes);
        con.setAccessible(true);
        T instance = null;
        if (parameterTypes == null) {
            instance = cls.newInstance();
        } else {
            int length = parameterTypes.length;
            Object[] paramsObj = new Object[length];
            for (int i = 0; i < length; i++) {
                paramsObj[i] = getDefault(parameterTypes[i]);
            }
            instance = (T) con.newInstance(paramsObj);
        }
        return parseFieldValues(instance, fieldValues);
    }

    public static Object getDefault(Class<?> type) throws ParseException {
        // 基础类型
        if (type.equals(byte.class)) {
            return 0;
        }
        if (type.equals(short.class)) {
            return 0;
        }
        if (type.equals(int.class)) {
            return 0;
        }
        if (type.equals(long.class)) {
            return 0;
        }
        if (type.equals(float.class)) {
            return 0;
        }
        if (type.equals(double.class)) {
            return 0;
        }
        return null;
    }

    /**
     * 将KV转换到对象上
     *
     * @param obj         被操作的对象
     * @param fieldValues
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T parseFieldValues(Object obj, FieldValues fieldValues) throws Exception {
        if (fieldValues == null) {
            throw new Exception("fieldValues is empty");
        }
        T instance = (T) obj;
        Class cls = obj.getClass();
        List<String> fields = fieldValues.getFields();
        List<Object> values = fieldValues.getValues();
        Integer len = fields.size();
        if (len > values.size()) {
            len = values.size();
        }
        for (int i = 0; i < len; i++) {
            try {
                Field f = ReflectionUtils.findField(cls, fields.get(i));
                ReflectionUtils.makeAccessible(f);

                //获取字段的类型
                Class<?> type = f.getType();
                // ---判断读取数据的类型
                Object val = values.get(i);
                if (type.isAssignableFrom(String.class)) {
                    ReflectionUtils.setField(f, instance, val.toString());
                } else if (type.isAssignableFrom(int.class)
                        || type.isAssignableFrom(Integer.class)) {
                    ReflectionUtils.setField(f, instance, Integer.parseInt(val.toString()));
                } else if (type.isAssignableFrom(BigInteger.class)) {
                    ReflectionUtils.setField(f, instance, new BigInteger(val.toString()));
                } else if (type.isAssignableFrom(Double.class)
                        || type.isAssignableFrom(double.class)) {
                    ReflectionUtils.setField(f, instance, Double.parseDouble(val.toString()));
                } else if (type.isAssignableFrom(Long.class)
                        || type.isAssignableFrom(long.class)) {
                    ReflectionUtils.setField(f, instance, Long.parseLong(val.toString()));
                } else if (type.isAssignableFrom(Boolean.class)
                        || type.isAssignableFrom(boolean.class)) {
                    ReflectionUtils.setField(f, instance, Boolean.parseBoolean(val.toString()));
                } else if (type.isAssignableFrom(Date.class)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    ReflectionUtils.setField(f, instance, dateFormat.parse(val.toString()));
                } else if (type.isAssignableFrom(Timestamp.class)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    ReflectionUtils.setField(f, instance, new Timestamp(dateFormat.parse(val.toString()).getTime()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
