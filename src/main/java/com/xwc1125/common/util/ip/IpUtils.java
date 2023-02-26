package com.xwc1125.common.util.ip;

import com.xwc1125.common.util.json.JsonUtils;
import com.xwc1125.common.util.mobile.TELOPR_TYPE;
import com.xwc1125.common.util.string.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.TreeMap;

/**
 * <p>
 * Title: CityUtil
 * </p>
 * <p>
 * Description: 通过ip获取详细地址
 * </p>
 *
 * @author xwc1125
 * @date 2015-7-13下午1:59:29
 */
public class IpUtils {
    public static void main(String[] args) {
        System.out.println(getRealAddressByIP("120.52.24.7", "utf-8"));
        // System.out.println(getAddresses2("120.52.24.7", "utf-8"));
        // System.out.println(getResult(
        // "http://www.ip138.com/ips1388.asp?ip=218.192.3.42", "", "gbk"));
        // System.out
        // .println(getResult(
        // "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.192.3.42",
        // "", "utf-8"));
        // System.out.println(getResult(
        // "http://ip.qq.com/cgi-bin/searchip?searchip1=218.192.3.42", "",
        // "gbk"));
        // System.out.println(getResult(
        // "http://ip.taobao.com/service/getIpInfo.php?ip=218.192.3.42",
        // "", "utf-8"));

    }

    /**
     * <p>
     * Title: getAddresses
     * </p>
     * <p>
     * Description: TODO(describe the methods)
     * </p>
     * <p>
     *
     * </p>
     *
     * @param ip             ：ip 地址格式:ip=219.136.134.157
     * @param encodingString ：编码格式：utf-8
     * @return 返回省份城市 返回的值可以根据需要获取不同字段
     * @throws UnsupportedEncodingException 不支持编码异常
     * @author xwc1125
     * @date 2015-7-13下午1:59:23
     */
    public static String getRealAddressByIP2(String ip, String encodingString) {
        try {
            // 这里调用pconline的接口
            String urlStr = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip="
                    + ip.trim();
            // 从http://whois.pconline.com.cn取得IP所在的省市区信息
            String returnStr = getResult(urlStr, "",
                    encodingString == null ? "utf-8" : encodingString);
            if (returnStr != null) {
                returnStr = returnStr.replaceAll("var remote_ip_info =", "");
                List<TreeMap<String, Object>> mapList = JsonUtils.jsonToListMap(returnStr);
                // {city=北京, country=中国, desc=, district=, end=-1, isp=,
                // province=北京, ret=1, start=-1, type=}
                // 处理返回的省市区信息
                if (mapList != null && mapList.size() > 0) {
                    TreeMap<String, Object> treeMap = mapList.get(0);
                    StringBuffer buffer = new StringBuffer();
                    if (treeMap.containsKey("country")
                            && StringUtils.isNotEmpty(treeMap.get("country")
                            + "")) {
                        buffer.append(treeMap.get("country") + "-");
                    }
                    if (treeMap.containsKey("province")
                            && StringUtils.isNotEmpty(treeMap.get("province")
                            + "")) {
                        buffer.append(treeMap.get("province") + "-");
                    }
                    if (treeMap.containsKey("city")
                            && StringUtils.isNotEmpty(treeMap.get("city") + "")) {
                        buffer.append(treeMap.get("city") + "-");
                    }
                    if (treeMap.containsKey("district")
                            && StringUtils.isNotEmpty(treeMap.get("district")
                            + "")) {
                        buffer.append(treeMap.get("district") + "-");
                    }
                    if (buffer == null || buffer.length() == 0) {
                        return null;
                    }
                    return buffer.substring(0, buffer.length() - 1);
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * <p>
     * Title: getAddresses
     * </p>
     * <p>
     * Description: TODO(describe the methods)
     * </p>
     * <p>
     *
     * </p>
     *
     * @param ip             ：ip 地址格式:ip=219.136.134.157
     * @param encodingString ：编码格式：utf-8
     * @return 返回省份城市 返回的值可以根据需要获取不同字段
     * @throws UnsupportedEncodingException 不支持编码异常
     * @author xwc1125
     * @date 2015-7-13下午1:59:23
     */
    public static String getRealAddressByIP(String ip, String encodingString) {
        try {
            // 这里调用pconline的接口
            // String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
            String urlStr = "http://ip.taobao.com/service/getIpInfo.php?ip="
                    + ip.trim();
            // 从http://whois.pconline.com.cn取得IP所在的省市区信息
            String returnStr = getResult(urlStr, "",
                    encodingString == null ? "utf-8" : encodingString);
            if (returnStr != null) {
                // 处理返回的省市区信息
                System.out.println("获取IP地址：" + decodeUnicode(returnStr));
                String[] temp = returnStr.split(",");
                if (temp.length < 3) {
                    return "0";// 无效IP，局域网测试
                }
                String country = "";
                String area = "";
                String region = "";
                String city = "";
                String county = "";
                String isp = "";
                for (int i = 0; i < temp.length; i++) {
                    switch (i) {
                        case 1:
                            country = (temp[i].split(":"))[2].replaceAll("\"", "");
                            country = decodeUnicode(country);// 国家
                            break;
                        case 3:
                            area = (temp[i].split(":"))[1].replaceAll("\"", "");
                            area = decodeUnicode(area);// 地区
                            break;
                        case 5:
                            region = (temp[i].split(":"))[1].replaceAll("\"", "");
                            region = decodeUnicode(region);// 省份
                            break;
                        case 7:
                            city = (temp[i].split(":"))[1].replaceAll("\"", "");
                            city = decodeUnicode(city);// 市区
                            break;
                        case 9:
                            county = (temp[i].split(":"))[1].replaceAll("\"", "");
                            county = decodeUnicode(county);// 地区
                            break;
                        case 11:
                            isp = (temp[i].split(":"))[1].replaceAll("\"", "");
                            isp = decodeUnicode(isp);// ISP公司
                            break;
                    }
                }

                StringBuffer buffer = new StringBuffer();
                if (StringUtils.isNotEmpty(country)) {
                    buffer.append(country + "-");
                }
                if (StringUtils.isNotEmpty(area)) {
                    buffer.append(area + "-");
                }
                if (StringUtils.isNotEmpty(region)) {
                    buffer.append(region + "-");
                }
                if (StringUtils.isNotEmpty(city)) {
                    buffer.append(city + "-");
                }
                if (StringUtils.isNotEmpty(county)) {
                    buffer.append(county + "-");
                }
                if (StringUtils.isNotEmpty(isp)) {
                    buffer.append(isp + "-");
                }
                if (buffer == null || buffer.length() == 0) {
                    return null;
                }
                return buffer.substring(0, buffer.length() - 1);
                // return region + "-" + city + "-" + county;
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * <p>
     * Title: getResult
     * </p>
     * <p>
     * Description: 通过Http去访问数据,方式为POST
     * </p>
     * <p>
     *
     * </p>
     *
     * @param urlStr   请求的地址
     * @param content  请求的参数 格式为：name=xxx&pwd=xxx
     * @param encoding 服务器端请求编码。如GBK,UTF-8等
     * @return 返回访问得到的数据
     * @author xwc1125
     * @date 2015-7-13下午2:01:51
     */
    private static String getResult(String urlStr, String content, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();// 新建连接实例
            connection.setConnectTimeout(200);// 设置连接超时时间，单位毫秒
            connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
            connection.setDoOutput(true);// 是否打开输出流 true|false
            connection.setDoInput(true);// 是否打开输入流true|false
            connection.setRequestMethod("POST");// 提交方法POST|GET
            connection.setUseCaches(false);// 是否缓存true|false
            connection.connect();// 打开连接端口
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());// 打开输出流往对端服务器写数据
            out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.flush();// 刷新
            out.close();// 关闭输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
            // ,以BufferedReader流来读取
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (IOException e) {
        } finally {
            if (connection != null) {
                connection.disconnect();// 关闭连接
            }
        }
        return null;
    }

    /**
     * <p>
     * Title: decodeUnicode
     * </p>
     * <p>
     * Description: unicode 转换成 中文
     * </p>
     * <p>
     *
     * </p>
     *
     * @param theString
     * @return
     * @author xwc1125
     * @date 2015-7-13下午2:03:22
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * <p>
     * Title: getIpAddr
     * </p>
     * <p>
     * Description: 根据请求头获取访问IP
     * </p>
     * <p>
     *
     * </p>
     *
     * @param request
     * @return
     * @author xwc1125
     * @date 2015-7-13下午1:58:58
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * <p>
     * Title: getRemoteHost
     * </p>
     * <p>
     * Description: 获得真实路径
     * </p>
     * <p>
     *
     * </p>
     *
     * @param request
     * @return
     * @author xwc1125
     * @date 2015-7-13下午1:56:19
     */
    public static String getRemoteHost(
            HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    /**
     * <p>
     * Title: getRemortIP
     * </p>
     * <p>
     * Description: 获取客服端的真实IP
     * </p>
     * <p>
     *
     * </p>
     *
     * @param request
     * @return
     * @author xwc1125
     * @date 2015-7-13下午1:56:08
     */
    public static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    /**
     * @param @param ipAddress
     * @return void
     * @Title getTelOprType
     * @Description 通过网络Ip信息获取运营商信息
     * @author xwc1125
     * @date 2016年4月13日 上午11:10:23
     */
    public static TELOPR_TYPE getTelOprType(String ipAddress) {
        TELOPR_TYPE teloprType = TELOPR_TYPE.UNKNOW;
        if (StringUtils.isEmpty(ipAddress)) {
            return teloprType;
        }
        if (ipAddress.contains("联通")) {
            teloprType = TELOPR_TYPE.CUCC;
        } else if (ipAddress.contains("移动")) {
            teloprType = TELOPR_TYPE.CMCC;
        } else if (ipAddress.contains("电信")) {
            teloprType = TELOPR_TYPE.CTC;
        }
        return teloprType;
    }

    public static boolean internalIp(String ip) {
        byte[] addr = textToNumericFormatV4(ip);
        return internalIp(addr) || "127.0.0.1".equals(ip);
    }

    private static boolean internalIp(byte[] addr) {
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch (b1) {
                    case SECTION_6:
                        return true;
                }
            default:
                return false;
        }
    }

    /**
     * 将IPv4地址转换成字节
     *
     * @param text IPv4地址
     * @return byte 字节
     */
    public static byte[] textToNumericFormatV4(String text) {
        if (text.length() == 0) {
            return null;
        }

        byte[] bytes = new byte[4];
        String[] elements = text.split("\\.", -1);
        try {
            long l;
            int i;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > 4294967295L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > 255L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > 16777215L)) {
                        return null;
                    }
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i < 2; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L)) {
                        return null;
                    }
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < 4; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return bytes;
    }

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        return "127.0.0.1";
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        return "未知";
    }
}
