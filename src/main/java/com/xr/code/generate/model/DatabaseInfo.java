package com.xr.code.generate.model;

/**
 * 数据库信息
 * Created by forvoyager@outlook.com on 2019-01-31 17:23.
 */
public class DatabaseInfo {
  private String url = "jdbc:mysql://local:3306/ms_account_db?characterEncoding=UTF-8";
  private String driver = "com.mysql.jdbc.Driver";
  private String username = "root";
  private String password = "123456";

  public String getUrl() {
    return url;
  }

  public DatabaseInfo setUrl(String url) {
    this.url = url;
    return this;
  }

  public String getDriver() {
    return driver;
  }

  public DatabaseInfo setDriver(String driver) {
    this.driver = driver;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public DatabaseInfo setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public DatabaseInfo setPassword(String password) {
    this.password = password;
    return this;
  }
}
