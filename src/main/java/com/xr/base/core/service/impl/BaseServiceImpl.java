package com.xr.base.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xr.base.core.enums.Cluster;
import com.xr.base.core.mapper.IBaseMapper;
import com.xr.base.core.model.BaseModel;
import com.xr.base.core.page.PageData;
import com.xr.base.core.service.IBaseService;
import com.xr.base.core.util.AssertUtils;
import com.xr.base.core.util.CollectionUtils;
import com.xr.base.core.util.DateUtil;
import com.xr.base.core.util.MapUtils;
import com.xr.base.core.util.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <b>author</b>: forvoyager@outlook.com
 * <b>time</b>: 2019-06-18 08:05:38 <br>
 * <b>description</b>: 数据基础操作 服务实现
 */
public abstract class BaseServiceImpl<M extends IBaseMapper<T>, T> implements IBaseService<T> {

  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  protected M baseMapper;

  /**
   * 主键名称
   * @return
   */
  protected abstract String getPrimaryKeyName();

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  @Override
  public T insert(T entity) throws Exception {
    AssertUtils.notNull(entity, "insert failed, with invalid param value.");
    BaseModel baseModel = (BaseModel)entity;
    if(baseModel.getCreate_time() == null){
      baseModel.setCreate_time(DateUtil.currentTimeInSecond());
    }
    baseModel.setVersion(0);
    baseModel.setUpdate_time(baseModel.getCreate_time());

    this.baseMapper.insert(entity);
    return entity;
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  @Override
  public int insertBatch(List<T> entityList) throws Exception {
    AssertUtils.notEmpty(entityList, "insert batch failed, with invalid param value.");

    this.baseMapper.insertBatch(entityList);
    return entityList.size();
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  @Override
  public T upsert(T entity) throws Exception {
    AssertUtils.notNull(entity, "insert or update failed, with invalid param value.");

    String primaryKey = this.getPrimaryKeyName();
    Object idVal = ReflectUtils.getMethodValue(entity, primaryKey);
    if (idVal == null) {
      this.insert(entity);
    } else {
      long updated = update(entity);
      if(updated > 1){
        // 根据id更新，但是更新了多条记录，不正常
        throw new IllegalStateException("update by primary key failed.");
      }

      // 没有更新到，插入新数据
      if (0 == updated) {
        this.insert(entity);
      }
    }

    return this.selectOne(MapUtils.newHashMap(primaryKey, idVal), Cluster.master);
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  @Override
  public long deleteById(Serializable id) throws Exception {
    AssertUtils.notNull(id, "delete failed, with invalid primary key id.");

    return this.delete(MapUtils.newHashMap(this.getPrimaryKeyName(), id));
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  @Override
  public long deleteByIds(Collection<? extends Serializable> idList) throws Exception {
    AssertUtils.notEmpty(idList, "delete batch by id failed, with invalid param value.");

    return this.delete(MapUtils.newHashMap("idList", idList));
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  @Override
  public long delete(Map<String, Object> condition) throws Exception {
    AssertUtils.notEmpty(condition, "delete failed, with invalid condition.");

    return this.baseMapper.delete(condition);
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  @Override
  public long update(T entity) throws Exception {
    AssertUtils.notNull(entity, "update failed, with invalid param value.");
    BaseModel baseModel = (BaseModel)entity;
    if(baseModel.getUpdate_time() == null){
      baseModel.setUpdate_time(DateUtil.currentTimeInSecond());
    }

    return this.update(ReflectUtils.javaBeanToMap(entity));
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  @Override
  public long update(Map<String, Object> columnMap) throws Exception {
    AssertUtils.notEmpty(columnMap, "update failed, with invalid condition.");

    return this.baseMapper.update(columnMap);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public T selectById(Serializable id, Cluster cluster) throws Exception {
    if(id == null){ return null; }

    return this.selectOne(MapUtils.newHashMap(this.getPrimaryKeyName(), id), cluster);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public List<T> selectByIds(Collection<? extends Serializable> idList, Cluster cluster) throws Exception {
    if(CollectionUtils.isEmpty(idList)){
      return Collections.EMPTY_LIST;
    }

    return this.selectList(MapUtils.newHashMap("idList", idList), cluster);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public T selectOne(Map<String, Object> condition, Cluster cluster) throws Exception {
    return this.selectOne(condition, cluster, false);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public T selectOne(Map<String, Object> condition, Cluster cluster, boolean throwException) throws Exception {
    List<T> data = this.selectList(condition, cluster);
    if(CollectionUtils.isEmpty(data)){
      return null;
    }

    if(throwException && data.size() > 1){
      throw new IllegalStateException("select one, but found "+data.size());
    }

    return data.get(0);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public List<T> selectList(Map<String, Object> condition, Cluster cluster) throws Exception {
    AssertUtils.notEmpty(condition, "select failed, with invalid condition.");

    return this.baseMapper.selectList(condition);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public long selectCount(Map<String, Object> condition, Cluster cluster) throws Exception {
    AssertUtils.notEmpty(condition, "select failed, with invalid condition.");

    return this.baseMapper.selectCount(condition);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public PageData<T> selectPage(int pageNum, int pageSize, Map<String, Object> condition, Cluster cluster) throws Exception {

    // 默认返回第1页
    pageNum = pageNum < 1 ? 1 : pageNum;

    // 默认每页10条
    pageSize = pageSize < 1 ? 10 : pageSize;

    if(condition == null){
      condition = Collections.EMPTY_MAP;
    }

    if(condition.containsKey("orderBy")){
      PageHelper.startPage(pageNum, pageSize, condition.get("orderBy").toString());
    } else {
      PageHelper.startPage(pageNum, pageSize);
    }

    List<T> dataList = this.selectList(condition, cluster);
    PageInfo pageInfo = new PageInfo(dataList);

    PageData<T> pageData = new PageData<T>();
    pageData.setPageNum(pageInfo.getPageNum());
    pageData.setPageSize(pageInfo.getPageSize());
    pageData.setNextPage(pageInfo.getNextPage());
    pageData.setPrePage(pageInfo.getPrePage());
    pageData.setTotalRecords(pageInfo.getTotal());
    pageData.setTotalPages(pageInfo.getPages());
    pageData.setData(dataList);

    return pageData;
  }

}