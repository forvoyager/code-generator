package com.xr.code.generate.model;

/**
 * 数据库信息
 * Created by yangchangyan@yijiedai.com on 2019-01-31 17:23.
 */
public class DatabaseInfo {
  private String url = "jdbc:mysql://local:3306/ms_account_db?characterEncoding=UTF-8";
  private String driver = "com.mysql.jdbc.Driver";
  private String username = "root";
  private String password = "123456";

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDriver() {
    return driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
