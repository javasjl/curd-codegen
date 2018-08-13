package com.justdebugit.codegen.variables;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;


public class MybatisConfigVar {
  
  public List<Map<String, String>> aliases = Lists.newArrayList();  //{typeFullName,typeName}
  
  public List<Map<String, String>> mappers = Lists.newArrayList(); //{mapperXmlPath}

  public void addAlias(String typeFullName,String typeName){
    aliases.add(ImmutableMap.of("typeFullName", typeFullName,"typeName", typeName));
  }
  
  public void addMapper(String mapperXmlPath){
    mappers.add(ImmutableMap.of("mapperXmlPath", mapperXmlPath));
  }

  @Override
  public String toString() {
    return "MybatisConfigVar [aliases=" + aliases + ", mappers=" + mappers + "]";
  }
  
  

}
