package com.xwc1125.common.util.http;

import com.xwc1125.common.util.string.StringUtils;
import okhttp3.*;

import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: </p>
 * Builder:
 * <p>
 * Dispatcher dispatcher;          // 分发
 * Proxy proxy;                    // 代理
 * List<Protocol> protocols;
 * List<ConnectionSpec> connectionSpecs;
 * final List<Interceptor> interceptors = new ArrayList<>(); // 拦截器
 * final List<Interceptor> networkInterceptors = new ArrayList<>(); // 网络拦截器
 * ProxySelector proxySelector;
 * CookieJar cookieJar;
 * Cache cache;    // 缓存
 * InternalCache internalCache;
 * SocketFactory socketFactory;
 * SSLSocketFactory sslSocketFactory;
 * HostnameVerifier hostnameVerifier;
 * CertificatePinner certificatePinner;
 * Authenticator proxyAuthenticator;   // 代理证书
 * Authenticator authenticator;        // 证书
 * ConnectionPool connectionPool;
 * Dns dns;        // DNS
 * boolean followSslRedirects;
 * boolean followRedirects;
 * boolean retryOnConnectionFailure;
 * int connectTimeout;
 * int readTimeout;
 * int writeTimeout;
 * @Author: xwc1125
 * @Date: 2019-02-27 10:37
 * @Copyright Copyright@2019
 */
public class HttpUtils {

    public static final OkHttpClient httpClient = new OkHttpClient();

    /**
     * Content-Type
     */
    public static MediaType type = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");

    /**
     * Description: Get方法调用服务
     * </p>
     *
     * @param url
     * @return java.lang.String
     * @Author: xwc1125
     * @Date: 2019-02-27 10:49:36
     */
    public static Response httpGet(HttpUrl url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return syncRequest(httpClient, request);
    }

    /**
     * Description: Get方法调用服务
     * </p>
     *
     * @param url
     * @param map
     * @return java.lang.String
     * @Author: xwc1125
     * @Date: 2019-02-27 14:27:31
     */
    public static Response httpGet(String url, Map<String, Object> map) throws IOException {
        String paramString = getHttpParames(map);
        if (StringUtils.isEmpty(paramString)) {
            httpGet(HttpUrl.get(url));
        }
        return httpGet(HttpUrl.get(url + "?" + paramString));
    }

    /**
     * Description: 拼接字符串
     * </p>
     *
     * @param map
     * @return java.lang.String
     * @Author: xwc1125
     * @Date: 2019-02-27 14:27:44
     */
    public static String getHttpParames(Map<String, Object> map) {
        StringBuilder stringBuilder = null;
        try {
            if (map == null || map.size() < 1) {
                return null;
            }
            stringBuilder = new StringBuilder();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value != null && StringUtils.isNotEmpty(key)) {
                    String encodeValue = URLEncoder.encode(value.toString(), "utf-8");
                    stringBuilder.append(key).append("=").append(encodeValue).append("&");
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        } catch (Exception e) {
        }
        return stringBuilder.toString();
    }

    /**
     * Description: Post方法调用服务
     * </p>
     *
     * @param url
     * @param content
     * @return java.lang.String
     * @Author: xwc1125
     * @Date: 2019-02-27 10:49:53
     */
    public static Response httpPost(HttpUrl url, String content) throws IOException {
        RequestBody requestBody = RequestBody.create(content, type);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return syncRequest(httpClient, request);
    }

    /**
     * Description: Post方法调用服务
     * </p>
     *
     * @param url
     * @param map
     * @return java.lang.String
     * @Author: xwc1125
     * @Date: 2019-02-27 14:30:24
     */
    public static Response httpPost(String url, Map<String, ?> map) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            builder.add(key, value + "");
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        return syncRequest(httpClient, request);
    }

    /***
     * Description: 同步请求
     * </p>
     * @param request
     *
     * @Author: xwc1125
     * @Date: 2019-02-27 11:08:57
     */
    public static Response syncRequest(OkHttpClient httpClient, Request request) throws IOException {
        return httpClient.newCall(request).execute();
    }

    /**
     * Description: Get异步请求
     * </p>
     *
     * @param url
     * @param callBack
     * @return void
     * @Author: xwc1125
     * @Date: 2019-02-27 11:06:37
     */
    public static void httpGetAsync(HttpUrl url, final HttpCallBackListener callBack) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        asyncRequest(httpClient, request, callBack);
    }

    /***
     * Description: Post异步请求
     * </p>
     * @param url
     * @param content
     * @param callBack
     *
     * @return void
     * @Author: xwc1125
     * @Date: 2019-02-27 11:15:17
     */
    public static void httpPostAsync(HttpUrl url, String content, final HttpCallBackListener callBack) {
        RequestBody requestBody = RequestBody.create(content, type);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        asyncRequest(httpClient, request, callBack);
    }

    /**
     * Description: 异步请求
     * </p>
     *
     * @param request
     * @param callBack
     * @return void
     * @Author: xwc1125
     * @Date: 2019-02-27 11:09:05
     */
    private static void asyncRequest(OkHttpClient httpClient, Request request, final HttpCallBackListener callBack) {
        httpClient.newCall(request).enqueue(new Callback() {
            //asynch call
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onError(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        if (callBack != null) {
                            callBack.onResponse(response, null);
                        }
                    }
                    if (callBack != null) {
                        callBack.onResponse(response, responseBody.string());
                    }

                } catch (Exception e) {
                    if (callBack != null) {
                        callBack.onResponse(response, null);
                    }
                }
            }
        });
    }

    /***
     * Description: HttpsGet
     * </p>
     * @param url
     *
     * @return java.lang.String
     * @Author: xwc1125
     * @Date: 2019-02-27 11:15:56
     */
    public static Response httpsGet(HttpUrl url) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        OkHttpClient httpClient = sslHttpsClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        return syncRequest(httpClient, request);
    }

    /**
     * Description: HttpsPost
     * </p>
     *
     * @param url
     * @param content
     * @return java.lang.String
     * @Author: xwc1125
     * @Date: 2019-02-27 11:16:31
     */
    public static Response httpsPost(HttpUrl url, String content) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        OkHttpClient httpClient = sslHttpsClient();
        RequestBody requestBody = RequestBody.create(content, type);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return syncRequest(httpClient, request);
    }

    public static Response httpsPost(String url, Map<String, ?> map) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            builder.add(key, value + "");
        }
        OkHttpClient httpClient = sslHttpsClient();
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        return syncRequest(httpClient, request);
    }

    /**
     * Description: Get异步请求
     * </p>
     *
     * @param url
     * @param callBack
     * @return void
     * @Author: xwc1125
     * @Date: 2019-02-27 11:06:37
     */
    public static void httpsGetAsync(HttpUrl url, final HttpCallBackListener callBack) throws KeyManagementException, NoSuchAlgorithmException {
        OkHttpClient httpClient = sslHttpsClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        asyncRequest(httpClient, request, callBack);
    }

    /***
     * Description: Post异步请求
     * </p>
     * @param url
     * @param content
     * @param callBack
     *
     * @return void
     * @Author: xwc1125
     * @Date: 2019-02-27 11:15:17
     */
    public static void httpsPostAsync(HttpUrl url, String content, final HttpCallBackListener callBack) throws KeyManagementException, NoSuchAlgorithmException {
        OkHttpClient httpClient = sslHttpsClient();
        RequestBody requestBody = RequestBody.create(content, type);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        asyncRequest(httpClient, request, callBack);
    }

    /***
     * Description: 创建httpsClient
     * </p>
     * @param
     *
     * @return okhttp3.OkHttpClient
     * @Author: xwc1125
     * @Date: 2019-02-27 11:14:27
     */
    private static OkHttpClient sslHttpsClient() throws NoSuchAlgorithmException, KeyManagementException {
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        final HostnameVerifier verifiedAllHostname = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        return new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                .hostnameVerifier(verifiedAllHostname)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    /**
     * 使用okhttp请求
     *
     * @param url
     * @param callback
     */
    public static void sendOkHttpRequest(String url, okhttp3.Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        httpClient.newCall(request).enqueue(callback);
    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public static Response httpUpload(String url, Map<String, ?> map) throws IOException {
        Request request = getUploadRequest(url, map);
        return syncRequest(httpClient, request);
    }

    public static Response httpsUpload(String url, Map<String, ?> map) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        OkHttpClient httpClient = sslHttpsClient();
        Request request = getUploadRequest(url, map);
        return syncRequest(httpClient, request);
    }

    private static Request getUploadRequest(String url, Map<String, ?> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof File) {
                File file = (File) value;
                RequestBody fileBody = RequestBody.create(file, MEDIA_TYPE_PNG);
                builder.addFormDataPart(key, file.getName(), fileBody);
            } else {
                if (value != null) {
                    builder.addFormDataPart(key, value.toString());
                }
            }
        }
        return new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
    }
}
