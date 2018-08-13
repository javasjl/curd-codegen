package com.justdebugit.codegen.common;

import java.util.Map;

import com.google.common.collect.Maps;
import com.justdebugit.codegen.config.CodegenConfig;

public class CodeGenContext {
  
  private static final ThreadLocal<CodeGenContext> CONTEXT_HOLDER = new ThreadLocal<>();
  
  public static CodeGenContext getContext() {
    return CONTEXT_HOLDER.get();
  }
  
  public static void setContext(CodeGenContext context) {
    CONTEXT_HOLDER.set(context);
  }
  
  private CodegenConfig codegenConfig = CodegenConfig.defaultConfig();
  
  private Map<String, Object> attributes = Maps.newHashMap();

  public Map<String, Object> getAttributes() {
    return attributes;
  }
  
  public Object getAttribute(String key) {
    return attributes.get(key);
  }
  

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }
  
  public void addAttribute(String key,Object val){
    this.attributes.put(key, val);
  }

  public CodegenConfig codegenConfig() {
    return codegenConfig;
  }

  public void setCodegenConfig(CodegenConfig codegenConfig) {
    this.codegenConfig = codegenConfig;
  }
  

}
