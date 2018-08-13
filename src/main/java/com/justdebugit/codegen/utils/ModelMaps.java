package com.justdebugit.codegen.utils;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.google.common.collect.Sets;

/**
 * bean map util 支持thrift 生成 bean与java bean的映射(可以不同类型，只会映射同名属性)，支持内嵌映射
 * 局限性:源bean至少有一个基本类型的属性不为null,否则目标bean将为null，内嵌bean也一样
 * 
 */
public class ModelMaps {


  /** 用于thrift bean 与 java bean 相互转换 */
  private static final ModelMapper TBASE_MAPPER = new ModelMapper();

  private static final ModelMapper DEFAULT_MAPPER = new ModelMapper();

  private static final Converter<Long, Date> long2Date = new AbstractConverter<Long, Date>() {
    protected Date convert(Long source) {
      return new Date(source);
    }
  };

  private static final Converter<Date, Long> date2Long = new AbstractConverter<Date, Long>() {
    protected Long convert(Date source) {
      return source.getTime();
    }
  };


  static {
    TBASE_MAPPER.addConverter(long2Date);
    TBASE_MAPPER.addConverter(date2Long);
    TBASE_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    DEFAULT_MAPPER.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    DEFAULT_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  public static <D> D mapRaw(Object source, Class<D> dType) {
    return DEFAULT_MAPPER.map(source, dType);
  }

  public static <S, D> Set<D> map(Set<S> fromList, Class<D> clazz) {
    Set<D> retList = Sets.newHashSet();
    retList.addAll(
        fromList.stream().map(s -> DEFAULT_MAPPER.map(s, clazz)).collect(Collectors.toList()));
    return retList;
  }

}
