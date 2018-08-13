package com.justdebugit.codegen.utils;

import java.util.stream.Stream;

public class TypeUtil {
  
  public static boolean isPrimitive(String type){
    if (type.startsWith("java.lang") || Stream.of("Long","Integer","String","Short","Double","Float").filter(item -> type.endsWith(item)).findAny().isPresent()) {
       return true;
    }else {
       return false;
    }
  }

}
