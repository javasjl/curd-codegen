package com.justdebugit.codegen.common;

import java.io.Reader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.justdebugit.codegen.utils.ResourceUtil;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Mustache.Compiler;

@Configuration
public class BeanConfig {
  
  @Bean
  public Compiler compiler(){
    Mustache.Compiler compiler = Mustache.compiler()
      .withLoader(new Mustache.TemplateLoader() {
          @Override
          public Reader getTemplate(String name) {
              return ResourceUtil.toReader("mustache/" + name + ".mustache");
          }
      })
      .defaultValue("");
    return compiler;
  }
  
  
  

}
