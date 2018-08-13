package com.justdebugit.codegen.utils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ContextClassLoaderLocal;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.converters.ConverterFacade;
import org.apache.commons.beanutils.converters.DateConverter;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * 
 */
public class NullAwareBeanUtils extends BeanUtilsBean {
  
  static{
    
    BeanUtilsBean beanUtilsBean = new BeanUtilsBean(new ExtConvertUtilsBean());
    DateConverter converter = new DateConverter();
    converter.setPattern("yyyy-MM-dd HH:mm:ss");
    beanUtilsBean.getConvertUtils().register(new ConverterFacade(converter), java.util.Date.class);
  }
  
  private static final ContextClassLoaderLocal BEANS_BY_CLASSLOADER =
      new ContextClassLoaderLocal() {
        // Creates the default instance used when the context classloader is unavailable
        protected Object initialValue() {
          return new NullAwareBeanUtils();
        }
      };

  public static BeanUtilsBean getInstance() {
    return (NullAwareBeanUtils) BEANS_BY_CLASSLOADER.get();
  }

  public static void copyPropertiesIgnoreNull(Object dest, Object orig) {
    try {
      NullAwareBeanUtils.getInstance().copyProperties(dest, orig);
    } catch (Exception e) {
      throw new RuntimeException("NullAwareBeanUtils.copyProperties has encounter an problem", e);
    }
  }

  @Override
  public void copyProperty(Object dest, String name, Object value) throws IllegalAccessException,
      InvocationTargetException {
    if (value == null)
      return;
    super.copyProperty(dest, name, value);
  }

  public Object cloneBean(Object bean) throws IllegalAccessException, InstantiationException,
      InvocationTargetException, NoSuchMethodException {

    Object newBean = null;
    if (bean instanceof DynaBean) {
      newBean = ((DynaBean) bean).getDynaClass().newInstance();
    } else {
      newBean = bean.getClass().newInstance();
    }
    copyProperties(newBean, bean);
    return (newBean);

  }

  @SuppressWarnings("unchecked")
  public static <T> List<T> deepCopy(List<T> source) throws IllegalAccessException,
      InstantiationException, InvocationTargetException, NoSuchMethodException {
    Preconditions.checkNotNull(source);
    List<T> destList = Lists.newArrayList();
    for (T item : source) {
      Preconditions.checkNotNull(source);
      destList.add((T) NullAwareBeanUtils.getInstance().cloneBean(item));
    }
    return destList;
  }
  
  
  public static class ExtConvertUtilsBean extends ConvertUtilsBean {


    @Override
    public String convert(Object value) {
      if (value == null) {
        return null;
      } else if (value.getClass().isArray()) {
        if (Array.getLength(value) < 1) {
          return (null);
        }
        value = Array.get(value, 0);
        if (value == null) {
          return null;
        } else {
          Converter converter = lookup(String.class);
          return (converter.convert(String.class, value));
        }
      } else {
        Converter converter = null;
        if (value instanceof java.util.Date) {
          converter = lookup(java.util.Date.class);
        } else {
          converter = lookup(String.class);
        }
        return (converter.convert(String.class, value));
      }
    }

  }
 


}
