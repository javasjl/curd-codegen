package com.justdebugit.codegen.utils;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import com.google.common.base.Preconditions;

public class ReflectUtils {
  
  public static Class<?> rawTypeFor(Type type) {
    if (type instanceof Class<?>) {
      return (Class<?>) type;
    } else if (type instanceof ParameterizedType) {
      return (Class<?>) ((ParameterizedType) type).getRawType();
    } else if (type instanceof GenericArrayType) {
      Type componentType = ((GenericArrayType) type).getGenericComponentType();
      return Array.newInstance(rawTypeFor(componentType), 0).getClass();
    } else if (type instanceof TypeVariable) {
      return rawTypeFor(((TypeVariable<?>) type).getBounds()[0]);
    } else if (type instanceof WildcardType) {
      return rawTypeFor(((WildcardType) type).getUpperBounds()[0]);
    } else {
      String className = type == null ? "null" : type.getClass().getName();
      throw new IllegalArgumentException("Could not determine raw type for " + className);
    }
  }
  
  public static Class<?> getFirstParameterizedType(Class<?> clazz) {
    Type superclass = clazz.getGenericInterfaces()[0];
    Preconditions.checkArgument(superclass instanceof ParameterizedType, "%s is not parameterized", superclass);
    Type    type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
    return rawTypeFor(type);
  }
  

}
