package com.xr.code.generate.model;

/**
 * @Time: 2019-04-15 13:15
 * @Author: forvoyager@outlook.com
 * @Description: 文件信息
 */
public class FileData {

  /**
   * 文件名
   */
  private String name;
  /**
   * 文件路径
   */
  private String path;
  /**
   * 文件内容
   */
  private String content;

  public String getName() {
    return name;
  }

  public FileData setName(String name) {
    this.name = name;
    return this;
  }

  public String getPath() {
    return path;
  }

  public FileData setPath(String path) {
    this.path = path;
    return this;
  }

  public String getContent() {
    return content;
  }

  public FileData setContent(String content) {
    this.content = content;
    return this;
  }
}
