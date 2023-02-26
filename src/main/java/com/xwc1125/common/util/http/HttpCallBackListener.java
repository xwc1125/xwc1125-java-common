package com.xwc1125.common.util.http;

import okhttp3.Response;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2019-02-27 10:39
 * @Copyright Copyright@2019
 */
public interface HttpCallBackListener {
    void onResponse(Response response, String data);

    void onError(Exception e);
}
