package com.justdebugit.codegen.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OuterBeanDelegate {
  
  @Autowired
  private OuterBeanScanner outerBeanScanner;
  
  @Autowired
  private MustacheWriter mustacheWriter;
  
  public void apply(Object var){
    List<Object> beans =  outerBeanScanner.getBeans();
    beans.forEach(bean -> mustacheWriter.apply(var, bean));
  }

}
