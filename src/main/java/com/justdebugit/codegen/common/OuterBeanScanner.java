package com.justdebugit.codegen.common;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.justdebugit.codegen.outconfig.OuterConfig;
import com.justdebugit.codegen.utils.ResourceUtil;


@Component
public class OuterBeanScanner implements ApplicationContextAware,InitializingBean{
  
  private ApplicationContext context;
  
  private List<Object> beans = Lists.newArrayList();

  @Override
  public void afterPropertiesSet() throws Exception {
    List<Object> beanList = Lists.newArrayList();
    Map<String, OuterConfig> map =
        BeanFactoryUtils.beansOfTypeIncludingAncestors(context, OuterConfig.class);
    List<String> names = ResourceUtil.loadFactoryNames(OuterConfig.class);
    beanList.addAll(names.stream().map(name -> {
      Class<?> newClazz;
      Object target = null;
      try {
        newClazz = Class.forName(name);
        target = newClazz.newInstance();
      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
      return target;
    }).collect(Collectors.toList()));
    beanList.addAll(Lists.newArrayList(map.values()));
    beans = beanList;
  }
  
  public List<Object> getBeans() {
    return beans;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }

}
