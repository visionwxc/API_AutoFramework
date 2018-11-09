package com.qa.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.data.User;
import com.qa.restclient.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class PostApiTest extends TestBase{
    private static final Logger logger = LoggerFactory.getLogger(PostApiTest.class);
    TestBase testBase;
    String host;
    String url;
    RestClient restClient;

    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        host = prop.getProperty("HOST");
        url = host + "/api/users";
    }

    @Test
    public void postApiTest()throws ClientProtocolException,IOException {
        logger.info("开始执行post测试用例...");
        restClient = new RestClient();
        HashMap<String,String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type","application/json");
        User user = new User("wxc","tester");
        String userJsonString = JSON.toJSONString(user);
        CloseableHttpResponse response = restClient.post(url,userJsonString,headerMap);

        int statusCode = response.getStatusLine().getStatusCode();
        logger.info("开始断言状态是否是200...");
        Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200,"status code is not 200");
        String responseString = EntityUtils.toString(response.getEntity());
        JSONObject responseJson = JSON.parseObject(responseString);
        String name = TestUtil.getValueByJPath(responseJson, "name");
        String job = TestUtil.getValueByJPath(responseJson, "job");
        Assert.assertEquals(name, "wxc","name is not same");
        Assert.assertEquals(job, "tester","job is not same");
    }
}
