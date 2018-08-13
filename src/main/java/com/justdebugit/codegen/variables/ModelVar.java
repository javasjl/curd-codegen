package com.justdebugit.codegen.variables;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModelVar {
  
  private String  comment;
  private String  tableName;
  private String  packageName;
  private String  clazzSimpleName; 
  private String  lclazz;
  private boolean noresult; //不生成modelList class,modelList class用于构造翻页结果
  private List<ModelPropVar> props = new ArrayList<>();
  private Set<String> imports = new HashSet<>();
  private boolean intId; //int id
  
  private String clazzFullName;
  
  public Set<String> imports() {
    return imports;
  }
  
  public void addImport(String importy){
    this.imports.add(importy);
  }
  
  public void addImports(List<String> importys){
    this.imports.addAll(importys);
  }
  
  public void setImports(Set<String> imports) {
    this.imports = imports;
  }
  
  public List<ModelPropVar> props() {
    return props;
  }
  public void setProps(List<ModelPropVar> props) {
    this.props = props;
  }
  
  public void addProp(ModelPropVar prop){
    this.props.add(prop);
  }
  
  public void addAllProp(List<ModelPropVar> props){
    this.props.addAll(props);
  }
  
  
  public String packageName() {
    return packageName;
  }
  
  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }
 
  public List<ModelPropVar> getProps() {
    return props;
  }
  
  public String clazzSimpleName() {
    return clazzSimpleName;
  }

  public String getComment() {
    return comment;
  }

  public boolean isIntId() {
	return intId;
}

public void setIntId(boolean intId) {
	this.intId = intId;
}

public void setComment(String comment) {
    this.comment = comment;
  }

  public void setClazzSimpleName(String clazzSimpleName) {
    this.clazzSimpleName = clazzSimpleName;
  }

  @Override
  public String toString() {
    return "ModelMd [packageName=" + packageName + ", clazzSimpleName=" + clazzSimpleName + ", props=" + props + "]";
  }

  public boolean isNoresult() {
    return noresult;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public void setNoresult(boolean noresult) {
    this.noresult = noresult;
  }

  public String getPackName() {
    return packageName;
  }

  public String getLclazz() {
    return lclazz;
  }

  public void setLclazz(String lclazz) {
    this.lclazz = lclazz;
  }

  public String getClazzName() {
    return clazzSimpleName;
  }

  public String getClazzFullName() {
    return clazzFullName;
  }

  public void setClazzFullName(String clazzFullName) {
    this.clazzFullName = clazzFullName;
  }

  

}
