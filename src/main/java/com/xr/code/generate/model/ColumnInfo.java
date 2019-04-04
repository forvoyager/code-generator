package com.xr.code.generate.model;

/**
 * 字段信息
 * Created by forvoyager@outlook.com on 2019-01-31 17:28.
 */
public class ColumnInfo {
  /**
   * 字段名称
   */
  private String name;
  /**
   * 备注
   */
  private String comment;
  /**
   * java类型
   */
  private String javaTypeName;
  /**
   * 是否是主键
   */
  private boolean primary;

  public String getName() {
    return name;
  }

  public ColumnInfo setName(String name) {
    this.name = name;
    return this;
  }

  public String getComment() {
    return comment;
  }

  public ColumnInfo setComment(String comment) {
    this.comment = comment;
    return this;
  }

  public String getJavaTypeName() {
    return javaTypeName;
  }

  public ColumnInfo setJavaTypeName(String javaTypeName) {
    this.javaTypeName = javaTypeName;
    return this;
  }

  public boolean isPrimary() {
    return primary;
  }

  public ColumnInfo setPrimary(boolean primary) {
    this.primary = primary;
    return this;
  }
}
