package com.justdebugit.codegen.codec;

import com.justdebugit.codegen.config.CodegenConfig;

public interface Parser<S,T> {
  
  /***
   * 解析源文件到对象
   * @param source 要解析的源文件
   * @param config 所需配置
   * @return
   */
  public T parse(S source,CodegenConfig config);


  /***
   * 解析源文件到对象
   * @param source 要解析的源文件
   * @return
   */
  public T parse(S source);
  
  
  
}
