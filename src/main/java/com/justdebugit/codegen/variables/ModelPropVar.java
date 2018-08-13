package com.justdebugit.codegen.variables;

public class ModelPropVar {
  
  private String propName;
  
  private String typeName;
  
  private String clazzName;
  
  private String comment;
  
  private String getter;
  
  private String setter;
  
  private String modelClazzName;
  
  private boolean isList;
  private boolean isSet;
  private boolean isMap;
  

  public String modelClazzName() {
    return modelClazzName;
  }

  public void setModelClazzName(String modelClazzName) {
    this.modelClazzName = modelClazzName;
  }

  public String propName() {
    return propName;
  }

  public void setPropName(String propName) {
    this.propName = propName;
  }

  public String typeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String clazzName() {
    return clazzName;
  }

  public String comment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public void setClazzName(String clazzName) {
    this.clazzName = clazzName;
  }

  public String getter() {
    return getter;
  }

  public void setGetter(String getter) {
    this.getter = getter;
  }

  public String setter() {
    return setter;
  }

  public void setSetter(String setter) {
    this.setter = setter;
  }

  public boolean isList() {
    return isList;
  }

  public void setList(boolean isList) {
    this.isList = isList;
  }

  public boolean isSet() {
    return isSet;
  }

  public void setSet(boolean isSet) {
    this.isSet = isSet;
  }

  public boolean isMap() {
    return isMap;
  }

  public void setMap(boolean isMap) {
    this.isMap = isMap;
  }

  @Override
  public String toString() {
    return "ModelPropVar [propName=" + propName + ", typeName=" + typeName + ", clazzName="
        + clazzName + ", comment=" + comment + "]";
  }
  

}
