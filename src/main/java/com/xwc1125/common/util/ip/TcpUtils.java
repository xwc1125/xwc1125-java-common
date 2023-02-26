package com.xwc1125.common.util.ip;

import com.xwc1125.common.entity.TcpInfo;
import com.xwc1125.common.util.string.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2019-03-08 10:32
 * @Copyright Copyright@2019
 */
public class TcpUtils {
    /**
     * @param @param  request
     * @param @return
     * @Title getTCPInfo
     * @Description 接口访问的数据
     * @author xwc1125
     * @date 2016年1月26日 下午8:15:26
     */
    public static TcpInfo getTCPInfo(HttpServletRequest request, boolean isGetIpAddress) {
        String ip = IpUtils.getIpAddr(request);// IP地址
        String ipAddress = null;
        if (isGetIpAddress) {
//            LocalFlexCache flexCache = LocalFlexCache.getInstance(null);
//            if (StringUtils.isNotEmpty(ip)) {
//                ipAddress = flexCache.get(ip) + "";
//            }
            if (StringUtils.isEmpty(ipAddress)) {
                ipAddress = IpUtils.getRealAddressByIP(ip, "utf-8");
            }
//            if (StringUtils.isNotEmpty(ipAddress)) {
//                flexCache.set(ip, "IP", ipAddress, 30);
//            }
        }
        String api = request.getRequestURI();// api
        api = regexApi(api);
        // 请求头
        String ua = request.getHeader("user-agent");
        // 请求的浏览器
        String browser = UserAgentUtils.getBrowserInfo(request);
        // 客户端
        String clientOS = UserAgentUtils.getClientOS(request);
        StringBuffer url = request.getRequestURL();

        TcpInfo tcpInfo = new TcpInfo();
        tcpInfo.setIp(ip);
        tcpInfo.setIpAddress(ipAddress);
        tcpInfo.setApi(api);
        tcpInfo.setUrl(url.toString());
        tcpInfo.setBrowser(browser);
        tcpInfo.setOs(clientOS);
        tcpInfo.setUa(ua);
        System.out.println("【Note】" + tcpInfo);
        return tcpInfo;
    }

    /**
     * <p>
     * Title: regexApi
     * </p>
     * <p>
     * Description: TODO(describe the methods)
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param api
     * @return
     * @author xwc1125
     * @date 2016年9月22日 上午11:11:30
     */
    public static String regexApi(String api) {
        if (api.contains("//")) {
            api = api.replaceAll("//", "/");
        }
        if (api.contains("//")) {
            api = regexApi(api);
        }
        return api;
    }
}
