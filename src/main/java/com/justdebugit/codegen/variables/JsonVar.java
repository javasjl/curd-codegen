package com.justdebugit.codegen.variables;

import java.util.List;

import com.google.common.collect.Lists;

public class JsonVar {
  
  public String pack;
  public String rootc;
  public List<String> imports = Lists.newArrayList();
  public List<InnerC> clist   = Lists.newArrayList();
  public List<Prop> props = Lists.newArrayList();
  
  public static class InnerC {
    public String innerc;
    public List<Prop> props = Lists.newArrayList();
    @Override
    public String toString() {
      return "InnerC [innerc=" + innerc + ", props=" + props + "]";
    }
  }

  public static class Prop{
    public boolean isList;//是否list
    public int listCnt;
    public String  cname; //类型名称
    public String  pname; //属性名称，首字母大写
    public String  lpname;//属性名称
    public String  jpname;//json 属性名称 lpname|jpname 或者lpname
    @Override
    public String toString() {
      return "Prop [isList=" + isList + ", listCnt=" + listCnt + ", cname=" + cname + ", pname="
          + pname + ", lpname=" + lpname + ", jpname=" + jpname + "]";
    }
  }
  
  @Override
  public String toString() {
    return "JsonVar [pack=" + pack + ", rootc=" + rootc + ", imports=" + imports + ", cList="
        + clist + ", props=" + props + "]";
  }
}
