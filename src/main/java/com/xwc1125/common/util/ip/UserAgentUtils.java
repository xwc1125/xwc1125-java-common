package com.xwc1125.common.util.ip;

import com.xwc1125.common.util.string.StringUtils;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2019-03-08 10:32
 * @Copyright Copyright@2019
 */
public class UserAgentUtils {

    private static Logger log = LoggerFactory.getLogger(UserAgentUtils.class);

    /**
     * 获取客户端浏览器信息
     *
     * @param req
     * @return string
     */
    public static String getBrowserInfo(HttpServletRequest req) {
        String browserInfo = "other";
        try {
            String userAgent = req.getHeader("User-Agent");
            log.info("【User-Agent】" + userAgent);
            if (userAgent == null || userAgent.trim().length() == 0) {
                return browserInfo;
            }
            String ua = userAgent.toLowerCase();
            String s;
            String version;
            String msieP = "msie ([\\d.]+)";
            String ieheighP = "rv:([\\d.]+)";
            String firefoxP = "firefox\\/([\\d.]+)";
            String chromeP = "chrome\\/([\\d.]+)";
            String operaP = "opr.([\\d.]+)";
            String safariP = "version\\/([\\d.]+).*safari";

            Pattern pattern = Pattern.compile(msieP);
            Matcher mat = pattern.matcher(ua);
            if (mat.find()) {

                s = mat.group();
                version = s.split(" ")[1];
                browserInfo = "ie "
                        + version.substring(0, version.indexOf("."));
                return browserInfo;

            }

            pattern = Pattern.compile(firefoxP);
            mat = pattern.matcher(ua);
            if (mat.find()) {

                s = mat.group();
                version = s.split("/")[1];
                browserInfo = "firefox "
                        + version.substring(0, version.indexOf("."));
                return browserInfo;

            }

            pattern = Pattern.compile(ieheighP);
            mat = pattern.matcher(ua);
            if (mat.find()) {

                s = mat.group();
                version = s.split(":")[1];
                browserInfo = "ie "
                        + version.substring(0, version.indexOf("."));
                return browserInfo;

            }

            pattern = Pattern.compile(operaP);
            mat = pattern.matcher(ua);
            if (mat.find()) {

                s = mat.group();
                version = s.split("/")[1];
                browserInfo = "opera "
                        + version.substring(0, version.indexOf("."));
                return browserInfo;

            }

            pattern = Pattern.compile(chromeP);
            mat = pattern.matcher(ua);
            if (mat.find()) {

                s = mat.group();
                version = s.split("/")[1];
                browserInfo = "chrome "
                        + version.substring(0, version.indexOf("."));
                return browserInfo;

            }

            pattern = Pattern.compile(safariP);
            mat = pattern.matcher(ua);
            if (mat.find()) {

                s = mat.group();
                version = s.split("/")[1].split(" ")[0];
                browserInfo = "safari "
                        + version.substring(0, version.indexOf("."));
                return browserInfo;

            }
            return browserInfo;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return browserInfo;
    }

    /**
     * PC客户端
     */
    private static TreeMap<String, String> pcDeviceMap;
    /**
     * 移动端
     */
    private static TreeMap<String, String> mobileDeviceMap;

    private static void getKeywordsHashMap() {
        if (mobileDeviceMap == null || mobileDeviceMap.size() == 0) {
            mobileDeviceMap = new TreeMap<String, String>();
            // 移动端
            mobileDeviceMap.put("Android", "Android");
            mobileDeviceMap.put("ANDROID", "ANDROID");
            mobileDeviceMap.put("IOS", "IOS");
            mobileDeviceMap.put("iPhone", "iPhone");
            mobileDeviceMap.put("iPod", "iPod");
            mobileDeviceMap.put("iPad", "iPad");
            mobileDeviceMap.put("Windows Phone", "Windows Phone");
            mobileDeviceMap.put("MQQBrowser", "MQQBrowser");
            mobileDeviceMap.put("UCWEB", "UCWEB");
            mobileDeviceMap.put("UCBrowser", "UCWEB");
            mobileDeviceMap.put("MicroMessenger", "WeiXin");
            mobileDeviceMap.put("Opera", "Opera");
        }
        if (pcDeviceMap == null || pcDeviceMap.size() == 0) {
            pcDeviceMap = new TreeMap<String, String>();
            // PC端
            // Windows
            pcDeviceMap.put("(WinNT|Windows NT)", "WinNT");
            pcDeviceMap.put("(Windows NT 6\\.2)", "Win 8");
            pcDeviceMap.put("(Windows NT 6\\.1)", "Win 7");
            pcDeviceMap.put("(Windows NT 5\\.1|Windows XP)", "WinXP");
            pcDeviceMap.put("(Windows NT 5\\.2)", "Win2003");
            pcDeviceMap.put("(Win2000|Windows 2000|Windows NT 5\\.0)",
                    "Win2000");
            pcDeviceMap.put(
                    "(9x 4.90|Win9(5|8)|Windows 9(5|8)|95/NT|Win32|32bit)",
                    "Win9x");

            // mac
            pcDeviceMap.put("(Mac|apple|MacOS8)", "MAC");
            pcDeviceMap.put("(68k|68000)", "Mac68k");

            // Linux
            pcDeviceMap.put("Linux", "Linux");
        }
    }

    /**
     * @param @param  req
     * @param @return
     * @return String
     * @Title getClientOS
     * @Description 获取客户端操作系统信息
     * @author xwc1125
     * @date 2016年3月21日 下午12:02:23
     */
    public static String getClientOS(HttpServletRequest req) {
        getKeywordsHashMap();
        String userAgent = req.getHeader("User-Agent");
        log.info("【User-Agent】" + userAgent);
        String cos = "unknow os";
        if (StringUtils.isEmpty(userAgent)) {
            return cos;
        }
        for (Entry<String, String> en : mobileDeviceMap.entrySet()) {
            String key = en.getKey();
            String value = en.getValue();
            Pattern p = Pattern.compile(".*" + key + ".*");
            Matcher m = p.matcher(userAgent);
            if (m.find()) {
                cos = value;
                return cos;
            }
        }
        for (Entry<String, String> en : pcDeviceMap.entrySet()) {
            String key = en.getKey();
            String value = en.getValue();
            Pattern p = Pattern.compile(".*" + key + ".*");
            Matcher m = p.matcher(userAgent);
            if (m.find()) {
                cos = value;
                return cos;
            }
        }

        return cos;
    }

}
