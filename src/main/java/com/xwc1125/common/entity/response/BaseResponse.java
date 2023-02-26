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
public class BaseResponse<T> implements java.io.Serializable {

    private Integer code = 200;
    private String msg = "Success";
    private long t = System.currentTimeMillis();

    public BaseResponse() {
        super();
    }

    /**
     * @param code    int型。200代表成功；
     * @param message
     */
    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
