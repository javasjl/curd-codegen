package com.justdebugit.codegen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.justdebugit.codegen.codec.json.JSONParser;
import com.justdebugit.codegen.codec.json.JSONobj;
import com.justdebugit.codegen.codec.sql.DatabaseDefinition;
import com.justdebugit.codegen.codec.sql.MysqlDdlSqlParser;
import com.justdebugit.codegen.common.CodeGenContext;
import com.justdebugit.codegen.common.OuterBeanDelegate;
import com.justdebugit.codegen.config.CodegenConfig;
import com.justdebugit.codegen.srctransform.TransformDelegate;


@Component
public class CodeGenBroker {
  
  
  @Autowired
  private TransformDelegate transformDelegate;
  
  @Autowired
  private OuterBeanDelegate outerBeanDelegate;
  
  public void genSql(String sql,CodegenConfig config) {
    setUpContext(config);
    MysqlDdlSqlParser mysqlDdlSqlParser = new MysqlDdlSqlParser();
    DatabaseDefinition databaseDefinition = mysqlDdlSqlParser.parse(sql, CodeGenContext.getContext().codegenConfig());
    generate(databaseDefinition);
  }

  
  public void genJava(String json,CodegenConfig config) {
    setUpContext(config);
    JSONParser jsonParser = new JSONParser();
    JSONobj  jsonobj = jsonParser.parse(json, config);
    generate(jsonobj);
  }
  

  private void setUpContext(CodegenConfig config) {
    CodeGenContext context = new CodeGenContext();
    context.setCodegenConfig(config);
    CodeGenContext.setContext(context);
  }
  
  public  void generate(Object source){
    List<Object> vars =  transformDelegate.transform(source);
    vars.forEach(var -> outerBeanDelegate.apply(var));
  }


}
