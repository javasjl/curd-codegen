package com.justdebugit.codegen.variables;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


public class ModelsVar {
  
  private Map<String, String> tableClassMap = Maps.newHashMap();

  private Map<String,ModelVar> tableModelMap = Maps.newHashMap();

  private List<ModelVar> models = Lists.newArrayList();
  
  private String packageName;

  public List<ModelVar> models() {
    return models;
  }

  public void setModels(List<ModelVar> models) {
    this.models = models;
  }
  
  public void addModel(ModelVar model) {
    this.models.add(model);
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public List<ModelVar> getModels() {
    return models;
  }

  public Map<String, String> getTableClassMap() {
    return tableClassMap;
  }

  public void setTableClassMap(Map<String, String> tableClassMap) {
    this.tableClassMap = tableClassMap;
  }
  
  public void addTableClass(String tableName,String modelClass){
    this.tableClassMap.put(tableName, modelClass);
  }
  
  public void  addAllTableClass(Map<String, String> map) {
    this.tableClassMap.putAll(map);
  }

  @Override
  public String toString() {
    return "ModelsMd [models=" + models + "]";
  }

  public Map<String, ModelVar> getTableModelMap() {
    return tableModelMap;
  }

  public void setTableModelMap(Map<String, ModelVar> tableModelMap) {
    this.tableModelMap = tableModelMap;
  }

  public void addTableModel(String tableName,ModelVar modelVar){
    this.tableModelMap.put(tableName,modelVar);
  }

}
