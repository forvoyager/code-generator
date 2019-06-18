package com.xr.base.core.enums;

/**
 * <b>author</b>: forvoyager@outlook.com
 * <b>time</b>: 2019-06-18 08:05:38 <br>
 * <b>description</b>: 集群节点属性 <br>
 */
public enum Cluster {

  master("主节点"),
  slave("从节点"),
  ;

  private String label;

  private Cluster(String label){
    this.label = label;
  }
}
