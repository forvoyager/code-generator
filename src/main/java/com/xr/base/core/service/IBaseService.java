package com.xr.base.core.service;

import com.xr.base.core.enums.Cluster;
import com.xr.base.core.page.PageData;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <b>author</b>: forvoyager@outlook.com
 * <b>time</b>: 2019-06-18 08:05:38 <br>
 * <b>description</b>: 数据基础操作 服务定义
 */
public interface IBaseService<T> extends IService {

  /**
   * <p>
   * 插入
   * </p>
   *
   * @param entity 实体对象
   * @return 主键id
   */
  T insert(T entity) throws Exception;

  /**
   * <p>
   * 插入（批量）
   * </p>
   *
   * @param entityList 实体对象列表
   * @return 插入成功的记录数
   */
  int insertBatch(List<T> entityList) throws Exception;

  /**
   * <p>
   * 根据主键id进行判断，存在则更新，否则插入
   * </p>
   *
   * @param entity 实体对象
   * @return 主键id
   */
  T upsert(T entity) throws Exception;

  /**
   * <p>
   * 根据 ID 删除
   * </p>
   *
   * @param id 主键ID
   * @return 删除的行数
   */
  long deleteById(Serializable id) throws Exception;

  /**
   * <p>
   * 根据ID 批量删除
   * </p>
   *
   * @param idList 主键ID列表
   * @return 删除的行数
   */
  long deleteByIds(Collection<? extends Serializable> idList) throws Exception;

  /**
   * <p>
   * 根据条件删除记录
   * </p>
   *
   * @param condition 条件
   * @return 删除的行数
   */
  long delete(Map<String, Object> condition) throws Exception;

  /**
   * <p>
   * 修改
   * </p>
   *
   * @param entity 实体对象
   * @return 更新的行数
   */
  long update(T entity) throws Exception;

  /**
   * <p>
   * 根据条件修改记录
   * </p>
   *
   * @param columnMap 更新条件及参数
   * @return 更新的行数
   */
  long update(Map<String, Object> columnMap) throws Exception;

  /**
   * <p>
   * 根据 ID 查询
   * </p>
   *
   * @param id 主键ID
   * @param cluster 主节点 or 从节点
   * @return 查询的对象
   */
  T selectById(Serializable id, Cluster cluster) throws Exception;

  /**
   * <p>
   * 根据 ID 批量查询
   * </p>
   *
   * @param idList 主键ID列表
   * @param cluster 主节点 or 从节点
   * @return 查询的对象列表
   */
  List<T> selectByIds(Collection<? extends Serializable> idList, Cluster cluster) throws Exception;

  /**
   * <p>
   * 根据条件查询一条记录（存在多条时不会报错，返回第一条）
   * </p>
   *
   * @param condition 表字段 map 对象
   * @param cluster 主节点 or 从节点
   * @return 查询的对象
   */
  T selectOne(Map<String, Object> condition, Cluster cluster) throws Exception;

  /**
   * <p>
   * 根据 condition条件，查询一条记录
   * </p>
   *
   * @param condition 查询条件
   * @param cluster 主节点 or 从节点
   * @param throwException true存在多条抛异常 false不抛异常（返回第一条）
   * @return 查询的对象
   */
  T selectOne(Map<String, Object> condition, Cluster cluster, boolean throwException) throws Exception;

  /**
   * <p>
   * 根据条件查询
   * </p>
   *
   * @param condition 查询条件
   * @param cluster 主节点 or 从节点
   * @return 查询的对象列表
   */
  List<T> selectList(Map<String, Object> condition, Cluster cluster) throws Exception;

  /**
   * <p>
   * 根据条件查询总记录数
   * </p>
   *
   * @param condition 查询条件
   * @param cluster 主节点 or 从节点
   * @return 记录数
   */
  long selectCount(Map<String, Object> condition, Cluster cluster) throws Exception;

  /**
   * 分页查询
   * @param currentPage 第几页
   * @param pageSize 每页行数
   * @param condition 查询条件
   * @param cluster 主节点 or 从节点
   * @return 分页查询结果
   * @throws Exception
   */
  PageData<T> selectPage(int currentPage, int pageSize, Map<String, Object> condition, Cluster cluster) throws Exception;

}
