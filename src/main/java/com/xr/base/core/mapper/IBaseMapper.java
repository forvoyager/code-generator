package com.xr.base.core.mapper;

import java.util.List;
import java.util.Map;

/**
 * <b>author</b>: forvoyager@outlook.com
 * <b>time</b>: 2019-06-18 08:05:38 <br>
 * <b>description</b>: 数据基础操作定义
 */
public interface IBaseMapper<T> {

  /**
   * <p>
   * 插入
   * </p>
   *
   * @param entity 实体对象
   */
  void insert(T entity);

  /**
   * <p>
   * 插入（批量）
   * </p>
   *
   * @param entityList 实体对象列表
   */
  void insertBatch(List<T> entityList);

  /**
   * <p>
   * 根据条件删除记录
   * </p>
   *
   * @param condition 删除条件
   * @return 删除的行数
   */
  long delete(Map<String, Object> condition);

  /**
   * 根据条件进行更新
   *
   * @param columnMap 更新参数及条件
   * @return
   */
  long update(Map<String, Object> columnMap);

  /**
   * <p>
   * 根据条件进行列表查询
   * </p>
   *
   * @param condition 查询条件
   * @return List<T>
   */
  List<T> selectList(Map<String, Object> condition);

  /**
   * <p>
   * 根据条件查询总记录数
   * </p>
   *
   * @param condition 查询条件
   * @return long
   */
  long selectCount(Map<String, Object> condition);

}
