package com.justdebugit.codegen.srctransform;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.justdebugit.codegen.codec.json.JSONobj;
import com.justdebugit.codegen.common.CodeGenContext;
import com.justdebugit.codegen.utils.JsonVisitor;
import com.justdebugit.codegen.variables.JsonVar;

@Component
public class JsonTransformer implements SrcTransformer<JSONobj, JsonVar> {

  @Override
  public JsonVar transform(JSONobj json) {
    CodeGenContext context = CodeGenContext.getContext();
    String jsonClazz = context.codegenConfig().getJsonClazz();
    JsonVar jsonVar = new JsonVar();
    jsonVar.pack = StringUtils.substringBeforeLast(jsonClazz, ".");
    jsonVar.rootc = StringUtils.substringAfterLast(jsonClazz, ".");
    JsonVisitor jsonVisitor = new JsonVisitor(jsonVar);
    JsonVar ret =  jsonVisitor.visit(json.get());
    System.out.println(ret);
    return ret;
  }

}
