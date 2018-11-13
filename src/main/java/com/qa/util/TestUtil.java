package com.qa.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class TestUtil {

    //json 解析方法
    public static String getValueByJPath(JSONObject httpResponse,String jPath){
        Object obj = httpResponse;

        for (String s : jPath.split("/")) {
            if (!s.isEmpty()){
                if(!(s.contains("[") || s.contains("]"))){
                    obj = ((JSONObject)obj).get(s);
                }else {
                    obj = ((JSONArray)((JSONObject)obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replaceAll("]", "")));
                }
            }
        }
        return obj.toString();
    }

    //遍历excel，sheet参数
    public static Object[][] dtt(String filePath,int sheetId) throws IOException {

        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sh = wb.getSheetAt(sheetId);
        int numberrow = sh.getPhysicalNumberOfRows();
        int numbercell = sh.getRow(0).getLastCellNum();

        Object[][] dttData = new Object[numberrow][numbercell];
        for(int i=0;i<numberrow;i++){
            if(null==sh.getRow(i)||"".equals(sh.getRow(i))){
                continue;
            }
            for(int j=0;j<numbercell;j++) {
                if(null==sh.getRow(i).getCell(j)||"".equals(sh.getRow(i).getCell(j))){
                    continue;
                }
                XSSFCell cell = sh.getRow(i).getCell(j);
                cell.setCellType(CellType.STRING);
                dttData[i][j] = cell.getStringCellValue();
            }
        }

        return dttData;
    }

    //获取状态码
    public static int getStatusCode(CloseableHttpResponse closeableHttpResponse){
        int StatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        return StatusCode;
    }

    //获取token
    public static HashMap<String,String> getToken(CloseableHttpResponse httpResponse,String jsonPath)throws Exception{
        HashMap<String,String> responseToken = new HashMap<String, String>();
        String responseString = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
        ReadContext ctx = JsonPath.parse(responseString);
        String token = ctx.read(jsonPath);
        if(token == null || "".equals(token)){
            new Exception("token 不存在");
        }

        responseToken.put("token",token);
        return responseToken;
    }
}
