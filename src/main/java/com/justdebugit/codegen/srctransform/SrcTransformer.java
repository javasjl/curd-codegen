package com.justdebugit.codegen.srctransform;

/**
 * 数据源访问者生成变量
 *
 * @param <T> 数据源
 * @param <V> 变量结果集
 */
public interface SrcTransformer<T,R> {
  
  /**
   * 访问数据源对象进行变量生成
   * @param target
   */
  public R transform(T target);

}
