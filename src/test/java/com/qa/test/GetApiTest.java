package com.qa.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.qa.util.TestUtil.dtt;

public class GetApiTest extends TestBase {
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
    public void getAPITest()throws ClientProtocolException,IOException {
        restClient = new RestClient();
        CloseableHttpResponse httpResponse = restClient.get(url);

        int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(responseStatusCode,RESPONSE_STATUS_CODE_200,"response status code is not 200");

        String responseString = EntityUtils.toString(httpResponse.getEntity(),"utf-8");

        JSONObject responseJson = JSON.parseObject(responseString);

        String s = TestUtil.getValueByJPath(responseJson,"data[0]/first_name");
        System.out.println(s);
        Assert.assertEquals(s,"George","first name is not George");
    }
}
