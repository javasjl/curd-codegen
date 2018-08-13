package com.justdebugit.codegen.outconfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CodeGen {
  
  /**
   * mustache 名字
   * @return
   */
  String value() default "";

  /**
   * 输出路径,默认当期目录,只对文件类型起作用
   * @return
   */
  String path() default "";
  
  
  /**
   * 输出类型
   * @return
   */
  OuterType type() default OuterType.JAVA;
  
}
