package com.justdebugit.codegen.variables;

import java.util.List;

import com.google.common.collect.Lists;

public class MybatisSqlVar {
  
  public List<MybatisSqlTagVar> sqlTagVars = Lists.newArrayList();
  
  public static class MybatisSqlTagVar {
    public List<SqlModel> models = Lists.newArrayList();
    public String mapperClazz ;
    public String tag;
  }
  
  public static class SqlModel{
    public String clazz;
    public String table;
    public boolean isFirst;
    
    public List<SqlProp> props = Lists.newArrayList();
    
    public boolean hasJoin;
    public String joinMethod;
    public String btable;
    public String ajoinField;
    public String bjoinField;
    public String incrTfield;
    public String decrTfield;
  }
  
  public static class SqlProp{
    public boolean last;
    public boolean isId;
    public boolean isString;
    public boolean inList;
    public boolean like;
    public String tfield;
    public String jfield;
    public String name;
    
  }

  

}
