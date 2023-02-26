package com.xwc1125.common.entity.response;

import com.xwc1125.common.util.json.JsonUtils;

/**
 * <p>
 * Title: ResponseInfo
 * </p>
 * <p>
 * Description: 服务器返回结果
 * </p>
 * <p>
 * 本服务器返回的数据基本使用此格式：{ "code":200, "msg":"成功",  "data":对象,t:1551969206000 } <br>
 * 其中字段的意义：<br>
 * code：int型。200代表成功<br>
 * msg：相应的信息<br>
 * data：Object型。如果有数据，返回的是对象，否则返回null<br>
 *
 * </p>
 *
 * @author xwc1125
 * @date 2015-7-13上午10:56:04
 */
@SuppressWarnings("serial")
public class ResponseInfo<T> extends BaseResponse<T> {

    /**
     * 对象
     */
    private T data;

    public ResponseInfo() {
        super();
    }

    /**
     * @param code    int型。200代表成功；
     * @param message
     */
    public ResponseInfo(Integer code, String message) {
        super(code, message);
    }

    /**
     * @param code    int型。200代表成功；
     * @param message
     */
    public ResponseInfo(Integer code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    /**
     * @param data
     */
    public ResponseInfo(T data) {
        this.data = data;
    }

    public ResponseInfo data(T data) {
        this.setData(data);
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
