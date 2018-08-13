package com.justdebugit.codegen.common;

import java.util.Map;

import com.google.common.collect.Maps;

public class MysqlTypeRepo {
  
 private static Map<String, String> typeMap = Maps.newHashMap();

 public static final String BOOLEAN_NAME = "boolean";

  public static final String TINYINT_NAME = "tinyint";

  static{
    typeMap.put("bigint", "java.lang.Long");
    typeMap.put("tinyint", "java.lang.Integer");
    typeMap.put("boolean", "java.lang.Boolean");
    typeMap.put("int", "java.lang.Integer");
    typeMap.put("mediumint", "java.lang.Integer");
    typeMap.put("float", "java.lang.Double");
    typeMap.put("double", "java.lang.Double");
    typeMap.put("text", "java.lang.String");
    typeMap.put("tinytext", "java.lang.String");
    typeMap.put("longtext", "java.lang.String");
    typeMap.put("mediumtext", "java.lang.String");
    typeMap.put("varchar", "java.lang.String");
    typeMap.put("char", "java.lang.String");
    typeMap.put("timestamp", "java.sql.TimeStamp");
    typeMap.put("date", "java.util.Date");
    typeMap.put("datetime", "java.util.Date");
    typeMap.put("time", "java.sql.Time");
    typeMap.put("smallint", "java.lang.Integer");
    typeMap.put("decimal","java.math.BigDecimal");
  }

  public static String getTypeName(String mysqlType) {
    String type =  typeMap.get(mysqlType.toLowerCase());
    if (type == null) {
      throw new IllegalArgumentException("Can not find mysqlType mapping " + mysqlType);
    }
    return type;
  }
}
