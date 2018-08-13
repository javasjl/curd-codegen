package com.justdebugit.codegen.utils;

import com.google.common.base.CaseFormat;

public class CaseUtils {
  
  public static String toCamel(String input){
    return  CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, input);
  }
  
  public static String toUpperCamel(String input){
    return  CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, input);
  }
  
  public static String toUnderScore(String input) {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input);
  }

  public static void main(String[] args) {
    System.out.println(toCamel("id"));
  }
}
