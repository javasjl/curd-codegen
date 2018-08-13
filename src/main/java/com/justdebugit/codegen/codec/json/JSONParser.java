package com.justdebugit.codegen.codec.json;

import com.alibaba.fastjson.JSON;
import com.justdebugit.codegen.codec.Parser;
import com.justdebugit.codegen.config.CodegenConfig;

public class JSONParser implements Parser<String, JSONobj>{

  @Override
  public JSONobj parse(String source, CodegenConfig config) {
    Object object = JSON.parse(source);
    JSONobj jsoNobj = new JSONobj();
    jsoNobj.set(object);
    return jsoNobj;
  }

  @Override
  public JSONobj parse(String source) {
    return parse(source, CodegenConfig.defaultConfig());
  }

}
