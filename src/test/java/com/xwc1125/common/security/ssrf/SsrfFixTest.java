package com.xwc1125.common.security.ssrf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import junit.framework.TestCase;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2023/2/13 21:57
 * @Copyright Copyright@2023
 */
public class SsrfFixTest extends TestCase {

    public void testPatchInit() {
        String url = "http://127.0.0.1:8080/index.html";

        ArrayList<String> wlist = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        try {
            wlist.add("127.0.0.1");
            wlist.add("sectest.wsd.com");
            //初始化SSRF过滤器，并添加2个白名单
            SsrfFix.patchInit(wlist, null);
            //开启过滤
            SsrfFix.patch();
            //SSRF漏洞demo
            URL thisurl = new URL(url);
            HttpURLConnection openConnection = (HttpURLConnection) thisurl.openConnection();
            String protocol = thisurl.getProtocol();
            System.out.println(protocol);
            openConnection.connect();
            InputStream in = openConnection.getInputStream();
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(in));
            for (String temp = bufreader.readLine(); temp != null; temp = bufreader.readLine()) {
                builder.append(temp);
            }
            System.out.println(builder);
        } catch (SsrfException e) {
            // 捕获SSRF拦截后的异常
            builder.append("blocked by ssrf");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //关闭过滤，以免影响自身内网服务访问。
            SsrfFix.unpatch();
        }
        String s = builder.toString();
        System.out.println(s);
    }
}
