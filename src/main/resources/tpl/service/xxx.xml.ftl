<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- author: ${author} -->
<!-- time: ${time} -->
<!-- description: ${comments} 数据库操作mapper -->
<mapper namespace="${basePackageName}.${moduleName}.mapper.${modelName?cap_first}Mapper">

  <sql id="Base_Column_List">
    <#list fieldList as field>`${field.name}`<#if (fieldList?size > field_index+1) >, </#if></#list>
  </sql>

  <!-- 自定义查询条件 -->
  <sql id="Custom_Where_Sql">

  </sql>

  <!-- 基础查询条件，不允许修改，自定义条件在Custom_Where_Sql中添加 -->
  <sql id="Base_Where_Sql">
    <if test="idList != null and idList.size() > 0">
      <![CDATA[ AND t.`${primaryField}` IN ]]>
      <foreach collection="idList" item="item" open="(" separator="," close=")">
        <![CDATA[ ${r"#{item}"} ]]>
      </foreach>
    </if>
  <#list fieldList as field>
    <#if (field.name == "version")>
    <if test="where_version != null">
      <![CDATA[ AND t.`version` = ${r"#{where_version}"} ]]>
    </if>
    <#else>
    <if test="${field.name} != null">
      <![CDATA[ AND t.`${field.name}` = ${r"#{"}${field.name}} ]]>
    </if>
    </#if>
  </#list>
  </sql>

  <sql id="Where_Sql">
    <where>
      <include refid="Base_Where_Sql"/>
      <include refid="Custom_Where_Sql"/>
    </where>
  </sql>

  <!-- 插入 -->
  <insert id="insert" parameterType="${basePackageName}.${moduleName}.common.model.${modelName?cap_first}Model" keyProperty="${primaryField}" useGeneratedKeys="true">
    <![CDATA[ INSERT INTO ${tableName} ]]>
    <trim prefix="(" suffix=")" suffixOverrides=",">
    <#list fieldList as field>
      <if test="${field.name} != null">
        <![CDATA[ `${field.name}`, ]]>
      </if>
    </#list>
    </trim>
    <![CDATA[ VALUES ]]>
    <trim prefix="(" suffix=")" suffixOverrides=",">
    <#list fieldList as field>
      <if test="${field.name} != null">
        <![CDATA[ ${r"#{"}${field.name}}, ]]>
      </if>
    </#list>
    </trim>
  </insert>

  <!-- 批量插入 -->
  <insert id="insertBatch" parameterType="${basePackageName}.${moduleName}.common.model.${modelName?cap_first}Model">
    <![CDATA[ INSERT INTO ${tableName} (<#list fieldList as field>`${field.name}`<#if (fieldList?size > field_index+1) >, </#if></#list>) VALUES ]]>
    <foreach collection="list" separator="," item="item">
      (<#list fieldList as field>${'#'}{item.${field.name}}<#if (fieldList?size > field_index+1) >, </#if></#list>)
    </foreach>
  </insert>

  <!-- 更新（主键/version/create_time不会被更新，version自增1） -->
  <update id="update" parameterType="map">
    <![CDATA[ UPDATE ${tableName} t SET t.`version` = t.`version` + 1, ]]>
    <trim suffixOverrides=",">
    <#list fieldList as field>
      <#if (field.name == "version" || field.name == "create_time" || field.name == "${primaryField}")>
      <#else>
      <if test="${field.name} != null">
        <![CDATA[ t.`${field.name}` = ${r"#{"}${field.name}}, ]]>
      </if>
      </#if>
    </#list>
    </trim>
    <where>
      <if test="${primaryField} != null">
        <![CDATA[ AND t.`${primaryField}` = ${r"#{"}${primaryField}} ]]>
      </if>
      <#if (optimisticLock == true)>
      <if test="where_version != null">
        <![CDATA[ AND t.`version` = ${r"#{"}where_version} ]]>
      </if>
      </#if>
    </where>
  </update>

  <!-- 删除 -->
  <delete id="delete" parameterType="map">
    <![CDATA[ DELETE t FROM ${tableName} t ]]>
    <include refid="Where_Sql"/>
  </delete>

  <!-- 查询行数 -->
  <select id="selectCount" parameterType="map" resultType="long">
    <![CDATA[ SELECT COUNT(*) FROM ${tableName} t ]]>
    <include refid="Where_Sql"/>
  </select>

  <!-- 查询记录 -->
  <select id="selectList" parameterType="map" resultType="${basePackageName}.${moduleName}.common.model.${modelName?cap_first}Model">
    <include refid="global.pageStart"/>
    <![CDATA[ SELECT ]]>
    <include refid="Base_Column_List"/>
    <![CDATA[ FROM ${tableName} t ]]>
    <include refid="Where_Sql"/>
    <include refid="global.globalSort"/>
    <include refid="global.pageEnd"/>
  </select>

</mapper>