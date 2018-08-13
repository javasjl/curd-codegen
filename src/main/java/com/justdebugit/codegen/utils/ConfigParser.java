package com.justdebugit.codegen.utils;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

public class ConfigParser {
  
  public static Map<String, String> parseConfig(String comment) {
    String config = StringUtils.substringBetween(comment, "#");
    if (config == null) {
      return Maps.newHashMap();
    }
    Map<String, String> map = Splitter.on(",").omitEmptyStrings().withKeyValueSeparator(":").split(config);
    Map<String, String> caseInsensMap =  new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
    caseInsensMap.putAll(map);
    return caseInsensMap;
  }

}
