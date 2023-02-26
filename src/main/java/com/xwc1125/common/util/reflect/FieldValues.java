package com.xwc1125.common.util.reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2021/1/22 16:43
 * @Copyright Copyright@2021
 */
public class FieldValues {

    private List<String> fields;
    private List<Object> values;

    public FieldValues() {
        fields = new ArrayList<>();
        values = new ArrayList<>();
    }

    public void addFieldValue(String fieldName, Object value) throws Exception {
        this.fields.add(fieldName);
        this.values.add(value);
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    // array
    public String[] getFieldArray() {
        String[] strings = new String[fields.size()];
        fields.toArray(strings);
        return strings;
    }

    public String[] getValueArray() {
        int size = values.size();
        String[] strings = new String[size];
        for (int i = 0; i < size; i++) {
            Object o = values.get(i);
            if (o != null) {
                strings[i] = o.toString();
            } else {
                strings[i] = "";
            }
        }
        return strings;
    }

    public Object[] getValuesObject() {
        Object[] strings = new Object[values.size()];
        values.toArray(strings);
        return strings;
    }

}
