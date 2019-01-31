package com.xr.code.generate.model;

/**
 * 字段信息
 * Created by yangchangyan@yijiedai.com on 2019-01-31 17:28.
 */
public class ColumnInfo {
  private String name;
  /**
   * 备注
   */
  private String comment;
  /**
   * 字段类型
   *
   * @see java.sql.Types
   */
  private int type;
  /**
   * java类型
   */
  private Class javaType;

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

  public void setName(String name) {
    this.name = name;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public Class getJavaType() {
    return javaType;
  }

  public void setJavaType(Class javaType) {
    this.javaType = javaType;
  }

  public String getJavaTypeName() {
    return javaTypeName;
  }

  public void setJavaTypeName(String javaTypeName) {
    this.javaTypeName = javaTypeName;
  }

  public boolean isPrimary() {
    return primary;
  }

  public void setPrimary(boolean primary) {
    this.primary = primary;
  }
}
