package com.justdebugit.codegen.variables;

import java.util.List;

import com.google.common.collect.Lists;

public class MybatisVar {

  public MybatisConfigVar mybatisConfigVar;
  public MybatisMapperVar mybatisMapperVar;
  public MybatisSqlVar mybatisSqlVar;
  
  public OtherVar  otherVar;
  
  public List<ControlVar> controlVars = Lists.newArrayList();
  
  public List<ModelMethod> serviceVars = Lists.newArrayList(); 
  
  public static class OtherVar{
    public String appName;
    public String rootPack;
    public String commonPack;
    
    public String controlPack;
    public String servicePack;
    public String mapperPack;
    public String demoType;
    public String demoClazz;
    public String demoField;
    
    public String lappName;
    public String mainClazz;
    
    public String configPack;
    public String mapperType;
    
    public String namespace;
    public String pack;
    public String tag;
    public List<ModelVar> models = Lists.newArrayList();
    
    
    public String getAppName() {
      return appName;
    }
    public void setAppName(String appName) {
      this.appName = appName;
    }
    public String getRootPack() {
      return rootPack;
    }
    public void setRootPack(String rootPack) {
      this.rootPack = rootPack;
      this.pack = rootPack;
    }
    public String getCommonPack() {
      return commonPack;
    }
    public void setCommonPack(String commonPack) {
      this.commonPack = commonPack;
    }
    public String getControlPack() {
      return controlPack;
    }
    public void setControlPack(String controlPack) {
      this.controlPack = controlPack;
    }
    public String getServicePack() {
      return servicePack;
    }
    public void setServicePack(String servicePack) {
      this.servicePack = servicePack;
    }
    public String getMapperPack() {
      return mapperPack;
    }
    public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public void setMapperPack(String mapperPack) {
      this.mapperPack = mapperPack;
    }
    public String getDemoType() {
      return demoType;
    }
    public void setDemoType(String demoType) {
      this.demoType = demoType;
    }
    public String getDemoClazz() {
      return demoClazz;
    }
    public void setDemoClazz(String demoClazz) {
      this.demoClazz = demoClazz;
    }
    public String getDemoField() {
      return demoField;
    }
    public void setDemoField(String demoField) {
      this.demoField = demoField;
    }
    public String getLappName() {
      return lappName;
    }
    public void setLappName(String lappName) {
      this.lappName = lappName;
    }
    public String getMainClazz() {
      return mainClazz;
    }
    public void setMainClazz(String mainClazz) {
      this.mainClazz = mainClazz;
    }
    public String getConfigPack() {
      return configPack;
    }
    public void setConfigPack(String configPack) {
      this.configPack = configPack;
    }
    public String getMapperType() {
      return mapperType;
    }
    public void setMapperType(String mapperType) {
      this.mapperType = mapperType;
    }
    public String getNamespace() {
      return namespace;
    }
    public void setNamespace(String namespace) {
      this.namespace = namespace;
    }
    public String getTag() {
      return tag;
    }
    public void setTag(String tag) {
      this.tag = tag;
    }
    public List<ModelVar> getModels() {
      return models;
    }
    public void setModels(List<ModelVar> models) {
      this.models = models;
    } 
    
  }
    
  
  public static class ModelVar{
    public String modelName;
    public List<PropVar> props = Lists.newArrayList();
  }
  
  public static class PropVar{
    public Integer num;
    public String propType;
    public String propName;
    public String comment;
  }
  
  
  public static class ControlVar extends OtherVar {
    public List<ModelMethod> modelMethods = Lists.newArrayList(); 
    public String control;
    public List<String> imports = Lists.newArrayList();
  }
  
  
  public static class ModelMethod extends OtherVar{
    public String control;
    public String lcontrol;
    public String c;
    public String lc;   
    public String m;
    public String lm;
    public boolean intId;
  }
  
  
}
