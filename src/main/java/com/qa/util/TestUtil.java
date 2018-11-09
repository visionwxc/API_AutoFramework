package com.qa.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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
}
