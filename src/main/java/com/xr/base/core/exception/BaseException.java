package com.xr.base.core.exception;

import com.xr.base.core.enums.ResultCodeEnum;

import java.util.Map;

/**
 * <b>author</b>: forvoyager@outlook.com
 * <b>time</b>: 2019-06-18 08:05:38 <br>
 * <b>description</b>:
 */
public class BaseException extends RuntimeException{
  /**
   * 代码
   * @see ResultCodeEnum
   */
  private String code;
  /**
   * 信息提示
   */
  private String message;
  /**
   * 扩展数据
   */
  private Map extData;

  public BaseException(ResultCodeEnum code, String message){
    this.code = code.getCode();
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Map getExtData() {
    return extData;
  }

  public void setExtData(Map extData) {
    this.extData = extData;
  }
}
