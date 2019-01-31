package com.xr.code.generate.model;

import java.util.List;

/**
 * 表信息
 * Created by yangchangyan@yijiedai.com on 2019-01-31 17:25.
 */
public class TableInfo {
  private String tableName;
  /**
   * 去掉前缀之后的表名
   */
  private String name;
  /**
   * 备注
   */
  private String comments;
  /**
   * 字段列表
   */
  private List<ColumnInfo> fieldModelList;
  /**
   * 主键字段
   */
  private ColumnInfo primaryColumn;

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public List<ColumnInfo> getFieldModelList() {
    return fieldModelList;
  }

  public void setFieldModelList(List<ColumnInfo> fieldModelList) {
    this.fieldModelList = fieldModelList;
  }

  public ColumnInfo getPrimaryColumn() {
    return primaryColumn;
  }

  public void setPrimaryColumn(ColumnInfo primaryColumn) {
    this.primaryColumn = primaryColumn;
  }
}
