package com.qa.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.data.User;
import com.qa.report.PostParameters;
import com.qa.restclient.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

import static com.qa.util.TestUtil.getData;

public class PostApiTest extends TestBase{
    private static final Logger logger = LoggerFactory.getLogger(PostApiTest.class);
    private TestBase testBase;
    private String host;
    private String url;
    private RestClient restClient;
    private CloseableHttpResponse closeableHttpResponse;
    //Excel路径
    private String testCaseExcel;
    //header
    private HashMap<String ,String> postHeader = new HashMap<String, String>();
    //token路径
    private String tokenPath;

    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        restClient = new RestClient();
        postHeader.put("Content-Type","application/json");
        host = prop.getProperty("HOST");
        url = host + "/api/users";
        testCaseExcel = prop.getProperty("testCase1Data");
        tokenPath = prop.getProperty("devGuestTokenPath");
    }

    @DataProvider(name = "postData")
    public Object[][] post() throws IOException{
        return getData(testCaseExcel,0);
    }

    @DataProvider(name = "get")
    public Object[][] get() throws IOException{
        return getData(testCaseExcel,1);
    }

    @Test(dataProvider = "postData")
    public void postApiTest(String username,String password)throws ClientProtocolException,IOException {
//        logger.info("开始执行post测试用例...");
//        restClient = new RestClient();
//        HashMap<String,String> headerMap = new HashMap<String, String>();
//        headerMap.put("Content-Type","application/json");
//        User user = new User("wxc","tester");
//        String userJsonString = JSON.toJSONString(user);
//        CloseableHttpResponse response = restClient.post(url,userJsonString,headerMap);
//
//        int statusCode = response.getStatusLine().getStatusCode();
//        logger.info("开始断言状态是否是200...");
//        Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200,"status code is not 200");
//        String responseString = EntityUtils.toString(response.getEntity());
//        JSONObject responseJson = JSON.parseObject(responseString);
//        String name = TestUtil.getValueByJPath(responseJson, "name");
//        String job = TestUtil.getValueByJPath(responseJson, "job");
//        Assert.assertEquals(name, "wxc","name is not same");
//        Assert.assertEquals(job, "tester","job is not same");
        PostParameters parameters = new PostParameters(username,password);
        String userJsonString = JSON.toJSONString(parameters);
        closeableHttpResponse = restClient.post(url,userJsonString,postHeader);
        int statusCode = TestUtil.getStatusCode(closeableHttpResponse);
        Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_201,"response code is not 201");
        Reporter.log("status code ："+statusCode);
        Reporter.log("api address ："+url);
    }

    @Test(dataProvider = "get")
    public void testGet(String url)throws ClientProtocolException,IOException{
        closeableHttpResponse = restClient.get(host + url);
        int statusCode = TestUtil.getStatusCode(closeableHttpResponse);
        Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200,"状态值不是200");
        Reporter.log("status code ："+statusCode);
        Reporter.log("api address ："+ host + url);
    }

    @Test
    public void testGetGuestToken() throws Exception{
        closeableHttpResponse = restClient.get(tokenPath);
        int statusCode = TestUtil.getStatusCode(closeableHttpResponse);
        Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200);
        System.out.println("success");
        HashMap<String,String> tokenMap = TestUtil.getToken(closeableHttpResponse,"$.data.guestToken");
        Assert.assertTrue(tokenMap.containsKey("token"));
    }

    @AfterClass
    public void endTest(){
        System.out.println("test end");
    }
}
