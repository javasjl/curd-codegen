package com.justdebugit.codegen.srctransform;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.common.collect.Ordering;
import com.justdebugit.codegen.utils.ReflectUtils;

@Component
public class TransformDelegate implements SrcTransformer<Object, List<Object>> {
  
  @SuppressWarnings("rawtypes")
  private static final Ordering<SrcTransformer> TRAN_SORTER = Ordering.natural().onResultOf(tran ->{
    Order order = tran.getClass().getAnnotation(Order.class);
    if (order == null) {
      return Integer.MAX_VALUE;
    }else {
      return order.value();
    }
  });
  
  @SuppressWarnings("rawtypes")
  @Autowired
  private List<SrcTransformer> transformers;
  
  public void init(){
    transformers = TRAN_SORTER.sortedCopy(transformers);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object> transform(Object target) {
    return transformers.stream().filter(tran -> {
      Class<?> clazz = ReflectUtils.getFirstParameterizedType(tran.getClass());
      return clazz.equals(target.getClass());
    }).map(tran -> {
      return tran.transform(target);
    }).collect(Collectors.toList());
  }

}
