package com.justdebugit.codegen.variables;

import java.util.List;

import com.google.common.collect.Lists;

public class MybatisMapperVar {
  
  public List<MapperTag> mapperTags = Lists.newArrayList();
  
  public static class MapperTag {
    public String packageName;
    public String rootPack;
    public List<String> imports = Lists.newArrayList();
    public String mapper;
    public List<MapperModel> models = Lists.newArrayList();
  }
  
  public static class MapperModel{
    
    public String clazz;
    public String name;
    public String field;
    public boolean hasIncr;
    public boolean hasDecr;
    
  }
}
