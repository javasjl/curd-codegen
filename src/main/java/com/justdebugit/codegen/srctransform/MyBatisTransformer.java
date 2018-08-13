package com.justdebugit.codegen.srctransform;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.justdebugit.codegen.codec.sql.DatabaseDefinition;
import com.justdebugit.codegen.codec.sql.TableDefinition;
import com.justdebugit.codegen.codec.sql.TableFieldDefinition;
import com.justdebugit.codegen.common.CodeGenContext;
import com.justdebugit.codegen.common.MysqlTypeRepo;
import com.justdebugit.codegen.utils.CaseUtils;
import com.justdebugit.codegen.utils.ConfigParser;
import com.justdebugit.codegen.utils.NullAwareBeanUtils;
import com.justdebugit.codegen.variables.ModelPropVar;
import com.justdebugit.codegen.variables.ModelVar;
import com.justdebugit.codegen.variables.ModelsVar;
import com.justdebugit.codegen.variables.MybatisConfigVar;
import com.justdebugit.codegen.variables.MybatisMapperVar;
import com.justdebugit.codegen.variables.MybatisMapperVar.MapperModel;
import com.justdebugit.codegen.variables.MybatisMapperVar.MapperTag;
import com.justdebugit.codegen.variables.MybatisSqlVar;
import com.justdebugit.codegen.variables.MybatisSqlVar.MybatisSqlTagVar;
import com.justdebugit.codegen.variables.MybatisSqlVar.SqlModel;
import com.justdebugit.codegen.variables.MybatisSqlVar.SqlProp;
import com.justdebugit.codegen.variables.MybatisVar;
import com.justdebugit.codegen.variables.MybatisVar.ControlVar;
import com.justdebugit.codegen.variables.MybatisVar.ModelMethod;
import com.justdebugit.codegen.variables.MybatisVar.OtherVar;

@Component
@Order(10)
public class MyBatisTransformer implements SrcTransformer<DatabaseDefinition,MybatisVar> {
  

  /**
   * <pre>
   * sql parser不能做到解析表注释和建表语句注释
   * 只能通过列注释来提供额外配置,#开始,#结尾
   * 表前置注释配置例子 "-- #name:InfoNews,tag:order,moreprop:bigint-orderId,ctr:audio# --",tag表示表对应的xml文件名
   * 列配置      "xxx#like:1,left-join:table-item_id,inlist:1,incr:1,decr:1#",表示增加like,表关联,in语句等
   * </pre>
   */
  @Override
  public MybatisVar transform(DatabaseDefinition target) {
    Table<String, String, Map<String, String>> columnConfig  = getTableColumnConfig(target);
    Multimap<String, ModelVar> multimap = getCtrMap(target);
    Map<String, String> tableTagConfig = getTableTagMap(target);
    TableConfig tableConfig = new TableConfig(columnConfig, tableTagConfig);
    MybatisConfigVar mybatisConfigVar = extractMbConfig(target, tableConfig);
    MybatisMapperVar mybatisMapperVar = extractMbMapper(target, tableConfig);
    MybatisSqlVar    mybatisSqlVar    = extractMbSql(target, tableConfig);
    MybatisVar mybatisVar = new MybatisVar();
    mybatisVar.mybatisConfigVar = mybatisConfigVar;
    mybatisVar.mybatisMapperVar = mybatisMapperVar;
    mybatisVar.mybatisSqlVar = mybatisSqlVar;
    processOtherVar(mybatisVar);
    processControlVal(mybatisVar,multimap,tableTagConfig);
    return mybatisVar;
  }
  

  private void processControlVal(MybatisVar mybatisVar,Multimap<String, ModelVar> ctrTbMap,Map<String, String> tableTagMap){
    ctrTbMap.asMap().forEach((k,v) ->{
       ControlVar controlVar = new ControlVar();
       NullAwareBeanUtils.copyPropertiesIgnoreNull(controlVar, mybatisVar.otherVar);
       mybatisVar.controlVars.add(controlVar);
       controlVar.control = StringUtils.capitalize(k);
       v.forEach(orgModel ->{
         ModelMethod modelMethod = new ModelMethod();
         NullAwareBeanUtils.copyPropertiesIgnoreNull(modelMethod, mybatisVar.otherVar);
         modelMethod.c  = orgModel.getClazzName();
         modelMethod.lc = orgModel.getLclazz();
         modelMethod.intId = orgModel.isIntId();
         modelMethod.m  =  StringUtils.capitalize(tableTagMap.get(orgModel.getTableName()));
         modelMethod.lm  = StringUtils.uncapitalize(modelMethod.m);
         modelMethod.control = controlVar.control;
         modelMethod.lcontrol = StringUtils.uncapitalize(modelMethod.control);
         controlVar.imports.add(CodeGenContext.getContext().codegenConfig().getPackageName() + "." + modelMethod.c);
         controlVar.imports.add(CodeGenContext.getContext().codegenConfig().getPackageName() + "." + modelMethod.c + "." + modelMethod.c + "Ret");
         controlVar.imports.add(controlVar.servicePack + "." +  modelMethod.lcontrol + "." + modelMethod.c +"Service");
         controlVar.modelMethods.add(modelMethod);
       });
       mybatisVar.serviceVars.addAll(controlVar.modelMethods);
    });
  }
 


  private void processOtherVar(MybatisVar mybatisVar) {
    String packName = CodeGenContext.getContext().codegenConfig().getPackageName();
    String appName  = StringUtils.substringAfterLast(StringUtils.substringBeforeLast(packName, "."), ".") ;
    String rootPack = StringUtils.substringBeforeLast(packName, ".");
    String commonPack = rootPack + ".common";
    mybatisVar.otherVar = new OtherVar();
    mybatisVar.otherVar.appName = StringUtils.capitalize(appName);;
    mybatisVar.otherVar.lappName = StringUtils.uncapitalize(appName);
    mybatisVar.otherVar.rootPack = rootPack;
    mybatisVar.otherVar.pack = rootPack;
    mybatisVar.otherVar.commonPack = commonPack;
    
    mybatisVar.otherVar.mainClazz = rootPack + "." + StringUtils.capitalize(appName) + "Application";
    
    mybatisVar.otherVar.configPack = rootPack  + ".config";
    mybatisVar.otherVar.controlPack = rootPack + ".controller";
    mybatisVar.otherVar.servicePack = rootPack + ".service";
    mybatisVar.otherVar.mapperPack = rootPack  + ".mapper";
    
    ModelsVar modelsVar =  (ModelsVar)CodeGenContext.getContext().getAttribute("modelsVar");
    if (!modelsVar.models().isEmpty()) {
        ModelVar modelVar = modelsVar.models().get(0);
        mybatisVar.otherVar.demoType   = modelVar.clazzSimpleName();
        if (!mybatisVar.otherVar.demoType.endsWith("Ety")){
            mybatisVar.otherVar.mapperType = mybatisVar.otherVar.demoType;
        }else {
            mybatisVar.otherVar.mapperType = StringUtils.substringBeforeLast(mybatisVar.otherVar.demoType,"Ety");
        }

        mybatisVar.otherVar.demoClazz = modelVar.packageName() + "." + modelVar.clazzSimpleName();
        mybatisVar.otherVar.demoField = StringUtils.uncapitalize(modelVar.clazzSimpleName());
    }
  }


  private MybatisSqlVar extractMbSql(DatabaseDefinition target,TableConfig tableConfig) {
    MybatisSqlVar mybatisSqlVar = new MybatisSqlVar();
    Multimap<String, String> multimap = tableConfig.getTagTableConfig();
    Table<String, String, Map<String, String>> columnConfig = tableConfig.getColumnConfig();
    String packName = CodeGenContext.getContext().codegenConfig().getPackageName();
    CodeGenContext context = CodeGenContext.getContext();
    ModelsVar modelsVar =  (ModelsVar)context.getAttribute("modelsVar");
    
    List<TableDefinition> tables = target.getTables();
    Map<String, List<TableFieldDefinition>> map = tables.stream().collect(Collectors.toMap(TableDefinition::getTableName, TableDefinition::getFields));
    multimap.asMap().forEach((tag,tbs) ->{
      MybatisSqlTagVar sqlTagVar = new MybatisSqlTagVar();
      sqlTagVar.tag = tag;
      mybatisSqlVar.sqlTagVars.add(sqlTagVar);
      sqlTagVar.mapperClazz = StringUtils.substringBeforeLast(packName, ".") + "." + "mapper."
          + StringUtils.capitalize(tag) + "Mapper";
      tbs.forEach(tb -> {
        SqlModel sqlModel = new SqlModel();
        sqlTagVar.models.add(sqlModel);
        if (sqlTagVar.models.size() == 1) {
           sqlModel.isFirst = true;
        }
        
        sqlModel.table = tb;
        sqlModel.clazz = StringUtils.substringAfterLast(modelsVar.getTableClassMap().get(tb), ".");
      
        List<TableFieldDefinition> list = map.get(tb);
        list.forEach(column ->{
           Map<String, String> config = columnConfig.get(tb, column.getField());
           SqlProp prop = new SqlProp();
           sqlModel.props.add(prop);
           prop.tfield = column.getField();
           if (prop.tfield.equalsIgnoreCase("id")) {
               prop.isId = true;
           }
           prop.jfield = CaseUtils.toCamel(column.getField());
           prop.name = StringUtils.uncapitalize(sqlModel.clazz);
          
           prop.isString = MysqlTypeRepo.getTypeName(column.getType()).equals("java.lang.String");
           prop.like = config.get("like") == null? false : true;
           if (config.get("incr") != null) {
              sqlModel.incrTfield = column.getField();
           }
           if (config.get("decr") != null) {
              sqlModel.decrTfield = column.getField();
           }
           Map<String, String> joinmap = Maps.newHashMap();
           joinmap.put("inner-join", config.get("inner-join"));
           joinmap.put("right-join", config.get("right-join"));
           joinmap.put("left-join", config.get("left-join"));
           Optional<Map.Entry<String, String>> entry = joinmap.entrySet().stream().filter(e -> e.getValue()!=null).findFirst();
           if (entry.isPresent()) {
              sqlModel.hasJoin = true;
              sqlModel.ajoinField = column.getField();
              sqlModel.joinMethod = StringUtils.replace(entry.get().getKey(), "-", " ");
              String val = entry.get().getValue();
              List<String> tableField =  Splitter.on("-").splitToList(val);
              sqlModel.bjoinField = tableField.get(1);
              sqlModel.btable = tableField.get(0);
           }
           prop.inList = config.get("inList") == null? false : true;
           if (prop.inList) {
              ModelVar modelVar = modelsVar.getTableModelMap().get(tb);
              String jfield = prop.jfield;
              ModelPropVar propVar = modelVar.props().stream().filter(p -> p.propName().equals(jfield)).findFirst().get();
              ModelPropVar listProp = new ModelPropVar();
              modelVar.addProp(listProp);
              listProp.setClazzName("List<" + propVar.clazzName() + ">");
              listProp.setGetter(propVar.getter() + "s");
              listProp.setSetter(propVar.setter() + "s");
              listProp.setModelClazzName(propVar.modelClazzName());
              listProp.setPropName(propVar.propName()+"s");
              listProp.setTypeName(propVar.typeName());
              listProp.setList(true);
              modelVar.addImport("java.util.List");
              modelVar.addImport("java.util.ArrayList");
          }
        });
        sqlModel.props.get(sqlModel.props.size()-1).last = true;
      });
    });
    return mybatisSqlVar;
  }
  

  private MybatisMapperVar extractMbMapper(DatabaseDefinition target,TableConfig tableConfig) {
    String packName = CodeGenContext.getContext().codegenConfig().getPackageName();
    MybatisMapperVar mapperVar = new MybatisMapperVar();
    Multimap<String, String> multimap = tableConfig.getTagTableConfig();
    Table<String, String, Map<String, String>> columnConfig = tableConfig.getColumnConfig();
    CodeGenContext context = CodeGenContext.getContext();
    ModelsVar modelsVar =  (ModelsVar)context.getAttribute("modelsVar");
    
    multimap.asMap().forEach((tag,tbs) ->{
      MapperTag mapperTag = new MapperTag();
      mapperTag.rootPack = StringUtils.substringBeforeLast(packName, ".");
      mapperTag.packageName = mapperTag.rootPack + "." + "mapper";
      mapperTag.mapper = StringUtils.capitalize(tag);
      tbs.forEach(tb ->{
        mapperTag.imports.add(modelsVar.getTableClassMap().get(tb));
        
        MapperModel mapperModel = new MapperModel();
        mapperModel.clazz = StringUtils.substringAfterLast(modelsVar.getTableClassMap().get(tb), ".");
        mapperModel.field = StringUtils.uncapitalize(mapperModel.clazz);
        mapperModel.name  = mapperModel.field;
        
        if (columnConfig.row(tb).values().stream().anyMatch(m -> m.containsKey("incr"))) {
          mapperModel.hasIncr = true;
        }
        if (columnConfig.row(tb).values().stream().anyMatch(m -> m.containsKey("decr"))) {
          mapperModel.hasDecr = true;
        }
       
        mapperTag.models.add(mapperModel);
      });
      mapperVar.mapperTags.add(mapperTag);
    });
    return mapperVar;
  }

  private MybatisConfigVar extractMbConfig(DatabaseDefinition target,TableConfig tableConfig) {
    MybatisConfigVar configVar = new MybatisConfigVar();
    CodeGenContext context = CodeGenContext.getContext();
    ModelsVar modelsVar =  (ModelsVar)context.getAttribute("modelsVar");
    
    List<ModelVar> modelVars =  modelsVar.getModels();
    modelVars.forEach(model -> configVar.addAlias(model.getPackName() + "." + model.getClazzName(),
        model.getClazzName()));
    
    Multimap<String, String> multimap = tableConfig.getTagTableConfig();
    multimap.keys().forEach(tag -> configVar.addMapper("mapper/"+tag +".xml"));
    return configVar;
  }
  
  
  /**
   * 表名-tag名
   * @param target
   * @return
   */
  private Map<String, String> getTableTagMap(DatabaseDefinition target){
     List<TableDefinition> tables = target.getTables();
     Map<String, String> tableTagMap = tables.stream().map(table ->ImmutablePair.of(table.getTableName(), table.getTableConfig()))
     .map(pair ->ImmutablePair.of(pair.getKey().toString(),ConfigParser.parseConfig(pair.getRight()).getOrDefault("tag",ConfigParser.parseConfig(pair.getRight()).getOrDefault("name", ""))))
     .collect(Collectors.toMap(ImmutablePair::getKey, ImmutablePair::getValue));
     tableTagMap =  tableTagMap.entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e-> e.getValue() == "" ? CaseUtils.toCamel(e.getKey()) : StringUtils.uncapitalize(e.getValue())));
     return tableTagMap;
  }
  
  /**
   * ctr名-model 映射
   * @param definition
   * @return
   */
  private Multimap<String, ModelVar> getCtrMap(DatabaseDefinition definition){
    Multimap<String, ModelVar> multimap = ArrayListMultimap.create();
    Map<String, String> map = Maps.newHashMap();
    definition.getTables().forEach(table ->{
      String ctr = ConfigParser.parseConfig(table.getTableConfig()).getOrDefault("ctr","");
      map.put(table.getTableName(),Strings.isNullOrEmpty(ctr)? "" : ctr);
    });
    ModelsVar modelsVar =  (ModelsVar)CodeGenContext.getContext().getAttribute("modelsVar");
    map.forEach((k,v) ->{
      List<ModelVar> models = modelsVar.models();
      ModelVar model =  models.stream().filter(m -> m.getTableName().equals(k)).findFirst().get();
      multimap.put(Strings.isNullOrEmpty(v) ? model.getClazzName() : v, model);
    });
    return multimap;
  }
  
  
  /**
   * 表名-列名-配置
   * @param target
   * @return
   */
  private Table<String, String, Map<String, String>>  getTableColumnConfig(DatabaseDefinition target){
    Table<String, String, Map<String, String>> config = HashBasedTable.create();
    List<TableDefinition> tables = target.getTables();
    Map<String, List<TableFieldDefinition>> result = tables.stream().collect(Collectors.toMap(TableDefinition::getTableName, TableDefinition::getFields));
    result.forEach((k,v) -> {
      v.forEach(field -> {
          config.put(k, field.getField(), ConfigParser.parseConfig(field.getComment()));
      });
    });
    return config;
  }
  
  
  public static class TableConfig{
    
    private Table<String, String, Map<String, String>> columnConfig;
    private Map<String, String> tableTagConfig;
    private Multimap<String, String> tagTableConfig;
    
    public TableConfig(){}
    
    public TableConfig(Table<String, String, Map<String, String>> columnConfig,Map<String, String> tableTagConfig){
      this.columnConfig = columnConfig;
      this.tableTagConfig = tableTagConfig;
      Multimap<String, String> multimap = ArrayListMultimap.create();
      tableTagConfig.forEach((k,v) -> {
        multimap.put(v, k);
      });
      tagTableConfig = multimap;
    }
    
    public Table<String, String, Map<String, String>> getColumnConfig() {
      return columnConfig;
    }
    public void setColumnConfig(Table<String, String, Map<String, String>> columnConfig) {
      this.columnConfig = columnConfig;
    }

    public Map<String, String> getTableTagConfig() {
      return tableTagConfig;
    }

    public void setTableTagConfig(Map<String, String> tableTagConfig) {
      this.tableTagConfig = tableTagConfig;
    }

    public Multimap<String, String> getTagTableConfig() {
      return tagTableConfig;
    }

    public void setTagTableConfig(Multimap<String, String> tagTableConfig) {
      this.tagTableConfig = tagTableConfig;
    }
  }
  
  
  

}
