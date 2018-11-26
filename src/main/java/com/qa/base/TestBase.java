package com.qa.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 这是一个基类
 */
public class TestBase {
    public static final Logger logger = LoggerFactory.getLogger(TestBase.class);
    public Properties prop;
    public static final int RESPONSE_STATUS_CODE_200 = 200;
    public static final int RESPONSE_STATUS_CODE_201 = 201;
    public static final int RESPONSE_STATUS_CODE_400 = 400;
    public static final int RESPONSE_STATUS_CODE_404 = 404;
    public static final int RESPONSE_STATUS_CODE_500 = 500;

    //构造函数
    public TestBase(){
        try{
            prop = new Properties();
            logger.info("开始读取配置文件...");
            FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/qa/config/config.properties");
            prop.load(fileInputStream);
            fileInputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
