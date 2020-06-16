package com.xr.base.core.page;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 分页信息
 * <b>author</b>: forvoyager@outlook.com
 * <b>time</b>: 2019-06-18 08:05:38 <br>
 * <b>description</b>: 分页查询数据
 */
public class PageData<T> implements Serializable {

  private static final long serialVersionUID = 166L;

  /**
   * 总记录数
   */
  private long totalRecords;

  /**
   * 总页数
   */
  private int totalPages;

  /**
   * 当前页（默认显示第1页）
   */
  private int pageNum = 1;

  /**
   * 每页显示条数，默认 10
   */
  private int pageSize = 10;

  /**
   * 下一页
   */
  private int nextPage;

  /**
   * 前一页
   */
  private int prePage;

  /**
   * 是否是第一页
   */
  private boolean isFirstPage;

  /**
   * 是否是最后一页
   */
  private boolean isLastPage;


  /**
   * 查询数据列表
   */
  private List<T> data = Collections.EMPTY_LIST;

  /**
   * 查询参数
   */
  private Map<String, Object> condition;

  public long getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(long totalRecords) {
    this.totalRecords = totalRecords;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getPageNum() {
    return pageNum;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getNextPage() {
    return nextPage;
  }

  public void setNextPage(int nextPage) {
    this.nextPage = nextPage;
  }

  public int getPrePage() {
    return prePage;
  }

  public void setPrePage(int prePage) {
    this.prePage = prePage;
  }

  public boolean isFirstPage() {
    return isFirstPage;
  }

  public void setFirstPage(boolean firstPage) {
    isFirstPage = firstPage;
  }

  public boolean isLastPage() {
    return isLastPage;
  }

  public void setLastPage(boolean lastPage) {
    isLastPage = lastPage;
  }

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  @Transient
  public Map<String, Object> getCondition() {
    return condition;
  }

  public void setCondition(Map<String, Object> condition) {
    this.condition = condition;
  }

}
