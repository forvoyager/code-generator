package ${basePackageName}.${moduleName}.controller;

import ${basePackageName}.${moduleName}.common.controller.I${modelName?cap_first}Controller;
import ${basePackageName}.${moduleName}.common.model.${modelName?cap_first}Model;
import ${basePackageName}.${moduleName}.service.I${modelName?cap_first}Service;
import com.xr.base.core.dto.ResultDto;
import com.xr.base.core.enums.Cluster;
import com.xr.base.core.page.PageData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
* <b>author</b>: ${author}
* <b>time</b>: ${time} <br>
* <b>description</b>: ${comments} HTTP服务 <br>
*/
@RestController
public class ${modelName?cap_first}Controller {

  @Resource
  private I${modelName?cap_first}Service ${modelName}Service;

  /**
   * <p>
   * 插入一条记录
   * </p>
   *
   * @param entity 实体对象
   * @return
   */
  @RequestMapping("/${modelName}/insert")
  public ResultDto insert(@RequestBody ${modelName?cap_first}Model entity) throws Exception {
    return ResultDto.success(${modelName}Service.insert(entity));
  }

  /**
   * <p>
   * 插入（批量）
   * </p>
   *
   * @param entityList 对象列表
   * @return 插入成功的记录数
   */
  @RequestMapping("/${modelName}/insert/batch")
  public ResultDto<Integer> insertBatch(@RequestBody List<${modelName?cap_first}Model> entityList) throws Exception {
    return ResultDto.successData(${modelName}Service.insertBatch(entityList));
  }

  /**
   * <p>
   * 存在则更新，否则插入
   * </p>
   *
   * @param entity 实体对象
   * @return ${modelName?cap_first}Model 插入/更新成功的对象
   */
  @RequestMapping("/${modelName}/upsert")
  public ResultDto<${modelName?cap_first}Model> upsert(@RequestBody ${modelName?cap_first}Model entity) throws Exception {
    return ResultDto.successData(${modelName}Service.upsert(entity));
  }

  /**
   * <p>
   * 根据 主键ID 删除
   * </p>
   *
   * @param 主键ID
   * @return 删除的行数
   */
  @RequestMapping("/${modelName}/delete/{${primaryField}}")
  public ResultDto<Integer> deleteById(@PathVariable("${primaryField}") long ${primaryField}) throws Exception {
    return ResultDto.successData(${modelName}Service.deleteById(${primaryField}));
  }

  /**
   * <p>
   * 根据 condition 条件，删除记录
   * </p>
   *
   * @param condition 删除条件
   * @return Integer 删除的行数
   */
  @RequestMapping("/${modelName}/delete")
  public ResultDto<Long> delete(@RequestBody Map<String, Object> condition) throws Exception {
    return ResultDto.successData(${modelName}Service.deleteById(${primaryField}));
  }

  /**
   * <p>
   * 根据 model 修改数据
   * </p>
   *
   * @param entity 实体对象
   * @return 更新的行数
   */
  @RequestMapping("/${modelName}/update/model")
  public ResultDto<Long> update(@RequestBody ${modelName?cap_first}Model entity) throws Exception {
    return ResultDto.successData(${modelName}Service.update(entity));
  }

  /**
   * <p>
   * 根据map条件 修改
   * </p>
   *
   * @param columnMap 更新数据/更新条件
   * @return 更新的行数
   */
  @RequestMapping("/${modelName}/update/map")
  public ResultDto<Long> updateByMap(@RequestBody Map<String, Object> columnMap) throws Exception {
    return ResultDto.successData(${modelName}Service.updateByMap(condition));
  }

  /**
   * <p>
   * 根据 主键ID 查询
   * </p>
   *
   * @param ${primaryField} 主键ID
   * @param master 主节点 or 从节点
   * @return ${modelName?cap_first}Model
   */
  @RequestMapping("/${modelName}/select/{master}/{${primaryField}}")
  public ResultDto<${modelName?cap_first}Model> selectById(@PathVariable("${primaryField}") long ${primaryField}, @PathVariable("master") Cluster master) throws Exception {
    return ResultDto.successData(${modelName}Service.selectById(${primaryField}, master));
  }

  /**
   * <p>
   * 根据 ID 批量查询
   * </p>
   *
   * @param idList 主键ID列表
   * @param master 主节点 or 从节点
   * @return List<${modelName?cap_first}Model> 列表
   */
  @RequestMapping("/${modelName}/select/{master}/batch")
  public ResultDto<List<${modelName?cap_first}Model>> selectByIds(@RequestBody Collection<? extends Serializable> idList, @PathVariable("master") Cluster master) throws Exception {
    return ResultDto.successData(${modelName}Service.selectByIds(idList, master));
  }

  /**
   * <p>
   * 查询（根据 condition 条件）
   * </p>
   *
   * @param condition 查询条件
   * @param master 主节点 or 从节点
   * @return List<${modelName?cap_first}Model> 列表
   */
  @RequestMapping("/${modelName}/select/{master}/list")
  public ResultDto<List<${modelName?cap_first}Model>> selectList(@RequestBody Map<String, Object> condition, @PathVariable("master") Cluster master) throws Exception {
    return ResultDto.successData(${modelName}Service.selectList(condition, master));
  }

  /**
   * <p>
   * 根据 condition 条件，查询一条记录
   * </p>
   *
   * @param condition 查询条件
   * @param master 主节点 or 从节点
   * @return ${modelName?cap_first}Model
   */
  @RequestMapping("/${modelName}/select/{master}/one")
  public ResultDto<${modelName?cap_first}Model> selectOne(@RequestBody Map<String, Object> condition, @PathVariable("master") Cluster master) throws Exception {
    return ResultDto.successData(${modelName}Service.selectOne(condition, master));
  }

  /**
   * <p>
   * 根据 condition 条件，查询总记录数
   * </p>
   *
   * @param condition 查询条件
   * @param master 主节点 or 从节点
   * @return long 记录数
   */
  @RequestMapping("/${modelName}/select/{master}/count")
  public ResultDto<Long> selectCount(@RequestBody Map<String, Object> condition, @PathVariable("master") Cluster master) throws Exception {
    return ResultDto.successData(${modelName}Service.selectCount(condition, master));
  }

  /**
   * <p>
   * 翻页查询
   * </p>
   *
   * @param page 第几页
   * @param size 每页记录数
   * @param condition 查询条件
   * @param master 主节点 or 从节点
   * @return
   */
  @RequestMapping("/${modelName}/select/{master}/page/{page}/{size}")
  public ResultDto<PageData<${modelName?cap_first}Model>> selectPage(@PathVariable("page") int page, @PathVariable("size") int size, @RequestBody Map<String, Object> condition, @PathVariable("master") Cluster master) throws Exception {
    return ResultDto.successData(${modelName}Service.selectPage(page, size, condition, master));
  }

}
