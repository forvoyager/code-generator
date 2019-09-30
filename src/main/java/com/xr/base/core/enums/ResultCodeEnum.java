package com.xr.base.core.enums;

/**
 * <b>author</b>: forvoyager@outlook.com
 * <b>time</b>: 2019-06-18 08:05:38 <br>
 * <b>description</b>: 返回状态码定义 <br>
 */
public enum ResultCodeEnum {

  SUCCESS("200", "成功"),
  ILLEGAL_ARGUMENT("300", "不合法的参数"),
  ILLEGAL_STATUS("400", "不合法的状态"),
  UNKNOW_SYSTEM_ERROR("500", "未知的系统错误"),
  ;

  private String code;
  private String label;

  private ResultCodeEnum(String code, String label){
    this.code = code;
    this.label = label;
  }

  public String getCode() {
    return code;
  }

  public String getLabel() {
    return label;
  }
}
