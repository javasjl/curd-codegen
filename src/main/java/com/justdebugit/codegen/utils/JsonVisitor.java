package com.justdebugit.codegen.utils;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.justdebugit.codegen.variables.JsonVar;
import com.justdebugit.codegen.variables.JsonVar.InnerC;
import com.justdebugit.codegen.variables.JsonVar.Prop;

public class JsonVisitor {
  private JsonVar jsonVar;
  
  public JsonVisitor(JsonVar jsonVar){
    this.jsonVar = jsonVar;
  }
  
  
  public JsonVar visit(Object object){
   Type type = inspect(object);
   if (type == Type.JSONArray) {
     JSONArray array = (JSONArray)(object);
     if (!array.isEmpty()) {
       visit(array.get(0));
     }
   }else if (type == Type.JSONObject) {
     visitJObj((JSONObject)object);
   }else {
     throw new IllegalArgumentException("非法输入");
   }
   return jsonVar;
  }
  
  
  public void visitJObj(JSONObject jsonObject){
    jsonObject.forEach((k,v) ->{
      Prop prop = new Prop();
      jsonVar.props.add(prop);
      setPname(prop, k);
      visit(prop,v);
    });
  }
  
  public  InnerC addInnerC(String innerC) {
    InnerC inner = new InnerC();
    inner.innerc = innerC;
    jsonVar.clist.add(inner);
    return inner;
  }
  
  public void visit(Prop prop,Object obj){
    Type type = inspect(obj);
    if (type == Type.JSONArray) {
      JSONArray array = (JSONArray)obj;
      if (!array.isEmpty()) {
        prop.isList = true;
        prop.listCnt++;
        visit(prop,array.get(0));
      }
    }else if (type == Type.JSONObject) {
      InnerC innerC = addInnerC(prop.pname);
      JSONObject jobj = (JSONObject)obj;
      prop.cname = prop.isList ? getListCName(prop.listCnt, innerC.innerc)  : innerC.innerc;
      jobj.forEach((k,v) ->{
        Prop iProp = new Prop();
        innerC.props.add(iProp);
        setPname(iProp, k);
        visit(iProp, v);
      });
    }else {
      String cname = type.name();
      prop.cname = prop.isList ? getListCName(prop.listCnt, cname)  : cname;
    }
  }


  private void setPname(Prop prop, String fieldName) {
    prop.lpname = fieldName;
    if (fieldName.contains("|")) {
      prop.lpname = StringUtils.substringBeforeLast(fieldName, "|");
      prop.jpname = StringUtils.substringAfterLast(fieldName, "|");
    }
    prop.pname  = StringUtils.capitalize(prop.lpname);
  }

  
  public static enum Type{
    JSONObject,JSONArray,String,Double,Float,Long,Integer,Boolean,Unkown;
  }
  
  public static String getListCName(Integer count,String cname){
    String ret = "%s";
    for (int i = 0; i < count; i++) {
      ret = "List<" + ret + ">";
    }
    return String.format(ret, cname);
  }
  
  
  public static Type inspect(Object object){
    if (object instanceof JSONObject) {
      return Type.JSONObject;
    }else if (object instanceof JSONArray) {
      return Type.JSONArray;
    }else if (object instanceof String) {
      return Type.String;
    }else if (object instanceof Double) {
      return Type.Double;
    }else if (object instanceof Float) {
      return Type.Float;
    }else if (object instanceof Integer) {
      return Type.Integer;
    }else if (object instanceof Long) {
      return Type.Long;
    }else if (object instanceof Boolean) {
      return Type.Boolean;
    }else if (object instanceof BigDecimal) {
      return Type.Double;
    }
    return Type.Unkown;
  }
  
  
}
