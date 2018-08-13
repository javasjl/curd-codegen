package com.justdebugit.codegen.outconfig;

import org.springframework.stereotype.Component;

import com.justdebugit.codegen.variables.JsonVar;

@Component
public class JsonOut implements OuterConfig{
  
  @CodeGen("json/json")
  public JsonVar render(JsonVar jsonVar){
    return jsonVar;
  }
  
}
