package com.xr.base.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * <b>author</b>: forvoyager@outlook.com
 * <b>time</b>: 2019-06-18 08:05:38 <br>
 * <b>description</b>:JSON工具类
 */
public final class JsonUtils {

  public static String toJson(Object obj){
    return JSON.toJSONString(obj);
  }

  public static <T extends Object> T parseObject(String json, Class<T> clz) {
    return JSON.parseObject(json, clz);
  }

  public static JSONObject parseObject(String json) {
    return JSON.parseObject(json);
  }

  private JsonUtils(){}
}
