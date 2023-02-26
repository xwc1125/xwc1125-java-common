package com.xwc1125.common.entity;

import com.xwc1125.common.constant.ClientOsType;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 回调函数
 *
 * @author xwc1125
 * @date 2016年4月5日 下午9:52:23
 */
public abstract class Callback<T> {

    private static Logger log = LoggerFactory.getLogger(Callback.class);

    /**
     * Description: 直接回调
     * </p>
     *
     * @return T
     * @Author: xwc1125
     * @Date: 2019-04-29 19:49:23
     */
    public T onCallback() {
        return null;
    }

    /**
     * @param @param  Msg
     * @param @return
     * @return T
     * @Title onFailure
     * @Description Failure的返回信息
     * @author xwc1125
     * @date 2016年4月5日 下午10:13:54
     */
    public abstract T onFailure(T Msg);

    /**
     * Description: 成功的回调接口
     * </p>
     *
     * @param Msg
     * @return T
     * @Author: xwc1125
     * @Date: 2019-02-21 14:34:15
     */
    public T onSuccess(T Msg) {
        log.info("成功进入逻辑处理...");
        return null;
    }

    /**
     * Description: 成功的回调接口
     * </p>
     *
     * @param tcpInfo
     * @param clientInfo
     * @param appInfo
     * @param sdkInfo
     * @param phoneInfo
     * @param deviceInfo
     * @param requestDataInfo
     * @param treeMap
     * @param fileMaps
     * @param osType
     * @return T
     * @Author: xwc1125
     * @Date: 2019-02-21 14:34:06
     */
    public T onSuccess(TcpInfo tcpInfo,
                       ClientInfo clientInfo, AppInfo appInfo, SdkInfo sdkInfo,
                       PhoneInfo phoneInfo, DeviceInfo deviceInfo,
                       CoreDataInfo requestDataInfo, TreeMap<String, String> treeMap,
                       Map<String, MultipartFile> fileMaps, ClientOsType osType) {
        log.info("成功进入逻辑处理...");
        return null;
    }

    /***
     * Description: 成功的回调接口
     * </p>
     * @param tcpInfo
     * @param treeMap
     * @param osType
     *
     * @return T
     * @Author: xwc1125
     * @Date: 2019-02-21 14:33:54
     */
    public T onSuccess(TcpInfo tcpInfo,
                       TreeMap<String, String> treeMap, ClientOsType osType) {
        log.info("成功进入逻辑处理...");
        return null;
    }

    public T onSuccess(RequestDataObj dataObj) {
        log.info("成功进入逻辑处理...");
        return null;
    }

    public T onSuccess(RequestDataObj dataObj, Map<String, MultipartFile> fileMaps) {
        log.info("成功进入逻辑处理...");
        return null;
    }
}
