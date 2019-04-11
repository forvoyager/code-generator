package com.xr.code.generate.model;

import java.util.List;

/**
 * 表信息
 * Created by forvoyager@outlook.com on 2019-01-31 17:25.
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
  private List<ColumnInfo> columnList;
  /**
   * 主键字段
   */
  private String primaryColumn;
  /**
   * 主键字段类型
   */
  private String primaryType;

  public String getTableName() {
    return tableName;
  }

  public TableInfo setTableName(String tableName) {
    this.tableName = tableName;
    return this;
  }

  public String getName() {
    return name;
  }

  public TableInfo setName(String name) {
    this.name = name;
    return this;
  }

  public String getComments() {
    return comments;
  }

  public TableInfo setComments(String comments) {
    this.comments = comments;
    return this;
  }

  public List<ColumnInfo> getColumnList() {
    return columnList;
  }

  public TableInfo setColumnList(List<ColumnInfo> columnList) {
    this.columnList = columnList;
    return this;
  }

  public String getPrimaryColumn() {
    return primaryColumn;
  }

  public TableInfo setPrimaryColumn(String primaryColumn) {
    this.primaryColumn = primaryColumn;
    return this;
  }

  public String getPrimaryType() {
    return primaryType;
  }

  public TableInfo setPrimaryType(String primaryType) {
    this.primaryType = primaryType;
    return this;
  }
}
