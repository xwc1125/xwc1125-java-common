# SSRF 安全库

## 简介

SSRF是一款安全函数库，适用于无法做数据验证（Validation）时，过滤恶意入参内容。

## 功能说明

基于socket的ssrf过滤函数。通过设置定制的socket实现类，在当前线程内执行对socket连接内网IP进行限制。
该方案由于在底层进行拦截，可有效防御域名变体、302跳转、DNS重绑定等常见的SSRF绕过手段。

注意：HttpSyncClients等异步HTTP请求方式暂不支持过滤
使用方式：

java demo：

```java
    @RequestMapping("/ssrf")
@ResponseBody
public String ssrf(HttpServletRequest req,HttpServletResponse res,String url)throws SQLException,IOException{
        ArrayList<String> wlist=new ArrayList<String>();
        StringBuilder builder=new StringBuilder();
        try{
        wlist.add("127.0.0.1");
        wlist.add("sectest.wsd.com");
        //初始化SSRF过滤器，并添加2个白名单
        SsrfFix.Patchinit(wlist);
        //开启过滤
        SsrfFix.patch();
        //SSRF漏洞demo
        URL thisurl=new URL(url);
        HttpURLConnection openConnection=(HttpURLConnection)thisurl.openConnection();
        String protocol=thisurl.getProtocol();
        System.out.println(protocol);
        openConnection.connect();
        InputStream in=openConnection.getInputStream();
        BufferedReader bufreader=new BufferedReader(new InputStreamReader(in));
        for(String temp=bufreader.readLine();temp!=null;temp=bufreader.readLine()){
        builder.append(temp);
        }
        System.out.println(builder);
        }catch(SSRFException e){
        //捕获SSRF拦截后的异常
        builder.append("blocked by ssrf");
        }finally{
        //关闭过滤，以免影响自身内网服务访问。
        SsrfFix.unpatch();
        }
        return builder.toString();
        }
```

简要描述：

| SsrfFix.Patchinit（）                        | ssrf socket初始化，全局通用。                            |
|--------------------------------------------|-------------------------------------------------|
| SsrfFix.Patchinit（ArrayList<String> wlist） | 初始化并设置SSRF过滤白名单，创建ArrayList<String>并添加IP地址或者域名。 |
| SsrfFix.patch();                           | 当前线程开启SSRF过滤，当请求地址命中地址内网地址黑名单则抛出SSRFException异常 |
| SsrfFix.unpatch();                         | 关闭当前线程SSRF过滤                                    |

​
当前默认过滤IP黑名单：
​ 10.255.255.255/8
​ 172.16.255.255/12
​ 192.168.0.0/16
​ 100.64.0.0/10
​ 127.0.0.0/8
​ 11.0.0.0/8
