package com.justdebugit.codegen.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.justdebugit.codegen.utils.PropertyPlaceholderHelper.PlaceholderResolver;
import com.justdebugit.codegen.variables.MybatisSqlVar.MybatisSqlTagVar;

public class PropertyHelper {
  
  public static String replacePlaceholders(String value, final Properties properties) {
    Validate.notNull(properties, "'properties' must not be null");
    PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper();
    return propertyPlaceholderHelper.replacePlaceholders(value, new PlaceholderResolver() {
      @Override
      public String resolvePlaceholder(String placeholderName) {
        return properties.getProperty(placeholderName);
      }
    });
  }
  
  public static String replacePlaceholders(String value, final Object bean){
    Validate.notNull(bean, "'properties' must not be null");
    PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper();
    return propertyPlaceholderHelper.replacePlaceholders(value, new PlaceholderResolver() {
      @Override
      public String resolvePlaceholder(String placeholderName) {
        return getProperty(bean,placeholderName);
      }
    });
  }
  
  public static String getProperty(Object bean, String name) {
    try {
      return BeanUtils.getProperty(bean, name);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      try {
        Object obj = FieldUtils.readField(bean, name,true);
        return obj != null ? obj.toString() : "";
      } catch (IllegalAccessException e1) {
        return "";
      }
    }
  }
  
  public static void main(String[] args) {
    MybatisSqlTagVar sqlVar = new MybatisSqlTagVar();
    sqlVar.tag = "1";
    System.out.println(replacePlaceholders("asdfasd/${tag}sadf", sqlVar));
  }

}
