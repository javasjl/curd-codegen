package com.justdebugit.codegen.srctransform;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.justdebugit.codegen.codec.sql.DatabaseDefinition;
import com.justdebugit.codegen.codec.sql.TableDefinition;
import com.justdebugit.codegen.codec.sql.TableFieldDefinition;
import com.justdebugit.codegen.common.CodeGenContext;
import com.justdebugit.codegen.common.MysqlTypeRepo;
import com.justdebugit.codegen.utils.CaseUtils;
import com.justdebugit.codegen.utils.ConfigParser;
import com.justdebugit.codegen.variables.ModelPropVar;
import com.justdebugit.codegen.variables.ModelVar;
import com.justdebugit.codegen.variables.ModelsVar;

@Component
@Order(1)
public class ModelTransformer implements SrcTransformer<DatabaseDefinition,ModelsVar> {
  
  

  @Override
  public ModelsVar transform(DatabaseDefinition target) {
    List<TableDefinition> tables = target.getTables();
    ModelsVar modelsVar = new ModelsVar();
    CodeGenContext context = CodeGenContext.getContext();
    String packName = context.codegenConfig().getPackageName();
    List<ModelVar>  modelVars  = modelsVar.models();


    Map<String, String> tableClassMap = Maps.newHashMap();
    modelVars.addAll(tables.stream().map(t ->{
        ModelVar modelVar =  toModel(t,packName);
        tableClassMap.put(t.getTableName(), packName + "." + modelVar.clazzSimpleName());
        modelsVar.addTableModel(t.getTableName(),modelVar);
        return modelVar;
    }).collect(Collectors.toList()));
    context.addAttribute("modelsVar", modelsVar);
    
    modelsVar.addAllTableClass(tableClassMap);
    return modelsVar;
  }
  
  private ModelVar toModel(TableDefinition definition,String packName){
    CodeGenContext context = CodeGenContext.getContext();
    ModelVar modelVar = new ModelVar();
    Map<String, String> configs = ConfigParser.parseConfig(definition.getTableConfig());
    String beanName =  configs.get("name");
    String noresult =  configs.get("noresult");
    if (noresult != null) {
       modelVar.setNoresult(true);
    }
    modelVar.setTableName(definition.getTableName());
    modelVar.setComment(definition.getTableComment());
    modelVar.setClazzSimpleName(beanName != null? beanName : CaseUtils.toUpperCamel(definition.getTableName())  + "");
    modelVar.setLclazz(StringUtils.uncapitalize(modelVar.getClazzName()));
    modelVar.setPackageName(packName);
    List<TableFieldDefinition> fields = definition.getFields();
    List<ModelPropVar> props = fields.stream().map(f -> {
         ModelPropVar modelPropVar = new ModelPropVar();
         modelPropVar.setComment(f.getComment());
         modelPropVar.setTypeName(MysqlTypeRepo.getTypeName(f.getType()));
         modelPropVar.setPropName(CaseUtils.toCamel(f.getField()));
         modelPropVar.setGetter("get" + StringUtils.capitalize(modelPropVar.propName()));
         modelPropVar.setSetter("set" + StringUtils.capitalize(modelPropVar.propName()));
         modelPropVar.setClazzName(StringUtils.substringAfterLast(modelPropVar.typeName(), "."));
         modelPropVar.setModelClazzName(modelVar.clazzSimpleName());
         if (modelPropVar.typeName() != null && !modelPropVar.typeName().contains("java.lang")) {
          modelVar.addImport(modelPropVar.typeName());
         }
         if (modelPropVar.propName().equalsIgnoreCase("id") && modelPropVar.clazzName().equalsIgnoreCase("Integer")) {
 			modelVar.setIntId(true);
 		 }
         modelVar.addImports(Lists.newArrayList("java.util.List","java.util.Set","java.util.Map","java.util.HashMap","java.util.HashSet","java.util.ArrayList"));
         return modelPropVar;
    }).collect(Collectors.toList());
    modelVar.addAllProp(props);
    
    String moreprop = configs.getOrDefault("moreprop", "");
    List<String> list = Splitter.on("|").omitEmptyStrings().splitToList(moreprop);
    list.forEach(str ->{
      modelVar.addProp(getMoreProp(str.split("-")[0], str.split("-")[1], modelVar.clazzSimpleName()));
    });
    return modelVar;
  }
  
  
  public ModelPropVar getMoreProp(String type,String name,String modelClazz){
    ModelPropVar modelPropVar = new ModelPropVar();
    modelPropVar.setTypeName(MysqlTypeRepo.getTypeName(type));
    modelPropVar.setPropName(name);
    modelPropVar.setGetter("get" + StringUtils.capitalize(modelPropVar.propName()));
    modelPropVar.setSetter("set" + StringUtils.capitalize(modelPropVar.propName()));
    modelPropVar.setClazzName(StringUtils.substringAfterLast(modelPropVar.typeName(), "."));
    modelPropVar.setModelClazzName(modelClazz);
    return modelPropVar;
  }
  

}
