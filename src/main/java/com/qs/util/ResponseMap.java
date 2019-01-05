package com.qs.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseMap {

    public static String TYPE = "type";

    public static String STATUS = "status";

    public static String CODE = "code";

    public static String MESSAGE = "message";

    public static String SUCCESS_FLAG = "success";

    public static String FAILURE_FLAG = "failure";

    public static Map<String,Object> resultMap;

    public static Map<String,Object> getResultMap(){
        resultMap = new HashMap<String,Object>();
        return resultMap;
    }


}
