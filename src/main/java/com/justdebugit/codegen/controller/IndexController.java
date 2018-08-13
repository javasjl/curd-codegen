package com.justdebugit.codegen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
  
  @RequestMapping("index")
  public String index(){
    return "home/index";
  }
  
  @RequestMapping("")
  public String index(ModelMap modelMap){
    return "redirect:/index";
  }

}
