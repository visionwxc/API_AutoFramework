package com.qa.restclient;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestClient {

    private static HttpClient client;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(32);
        cm.setDefaultMaxPerRoute(32);
        client = HttpClients.custom().setConnectionManager(cm).build();
    }

    //实现get请求
    public CloseableHttpResponse get(String url) throws ClientProtocolException,IOException {

//        //创建一个可以关闭的httpclient
//        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个HttpGet请求对象
        HttpGet httpGet = new HttpGet(url);
        //执行请求
        CloseableHttpResponse httpResponse = (CloseableHttpResponse)client.execute(httpGet);

        return httpResponse;
    }

    //get 带请求头信息
    public CloseableHttpResponse get(String url , HashMap<String,String> headMap)throws ClientProtocolException,IOException{
//        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<String, String> entry : headMap.entrySet()) {
            httpGet.addHeader(entry.getKey(),entry.getValue());
        }
        CloseableHttpResponse httpResponse = (CloseableHttpResponse)client.execute(httpGet);

        return httpResponse;
    }

    //post 方法
    public CloseableHttpResponse post(String url, String entity,HashMap<String,String> headMap) throws ClientProtocolException,IOException{
//        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new StringEntity(entity));
        for (Map.Entry<String, String> entry : headMap.entrySet()) {
            httppost.addHeader(entry.getKey(),entry.getValue());
        }
        CloseableHttpResponse httpResponse = (CloseableHttpResponse)client.execute(httppost);

        return httpResponse;
    }
    //delete方法
    public CloseableHttpResponse deleteApi(String url) throws ClientProtocolException, IOException {
//        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpdel = new HttpDelete(url);
        //发送delete请求
        CloseableHttpResponse httpResponse = (CloseableHttpResponse)client.execute(httpdel);
        return httpResponse;
    }
}
