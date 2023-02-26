package com.xwc1125.common.util.json;

import com.fasterxml.jackson.core.type.TypeReference;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2021/2/23 17:29
 * @Copyright Copyright@2021
 */
public class CustomTypeRef<T> extends TypeReference<T> {

    private Class<?> rawType, paramType;
    private Type[] actualTypes;

    /**
     * @param rawType   外层类
     *                  Type superClass = this.getClass().getGenericSuperclass();
     *                  rawType = ((ParameterizedType)superClass).getActualTypeArguments()[0];
     * @param paramType 泛型类
     */
    public CustomTypeRef(Class<?> rawType, Class<?> paramType) {
        this.rawType = rawType;
        this.paramType = paramType;
    }

    public CustomTypeRef(Class<?> rawType, List<Type> actualTypeList) {
        this.rawType = rawType;
        if (actualTypeList != null && actualTypeList.size() > 0) {
            actualTypes = new ParameterizedTypeImpl[actualTypeList.size()];
            for (int i = 0; i < actualTypeList.size(); i++) {
                actualTypes[i] = actualTypeList.get(i);
            }
        }
    }

    public CustomTypeRef(Class<?> rawType, Type[] actualTypes) {
        this.rawType = rawType;
        this.actualTypes = actualTypes;
    }

    public CustomTypeRef(Class<?> rawType, ParameterizedTypeImpl actualType) {
        this.rawType = rawType;
        if (actualType != null) {
            actualTypes = new ParameterizedTypeImpl[]{actualType};
        }
    }

    @Override
    public Type getType() {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                if (actualTypes == null) {
                    return new Type[]{paramType};
                }
                return actualTypes;
            }

            @Override
            public Type getRawType() {
                return rawType;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
}
