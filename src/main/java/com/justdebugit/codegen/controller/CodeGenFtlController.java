package com.justdebugit.codegen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CodeGenFtlController {
  
  @Autowired
  private CodeGenService codeGenService;
  


  @RequestMapping("json")
  public String getJsonFiles(String json,String jsonClazz,ModelMap modelMap){
    String val =  codeGenService.genJavaFromJson(json, jsonClazz);
    modelMap.put("val", val);
    return "home/javab";
  }
  
  
  
  
  
}
