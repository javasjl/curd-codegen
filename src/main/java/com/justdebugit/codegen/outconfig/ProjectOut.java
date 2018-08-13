package com.justdebugit.codegen.outconfig;

import java.util.List;

import org.springframework.stereotype.Component;

import com.justdebugit.codegen.variables.MybatisConfigVar;
import com.justdebugit.codegen.variables.MybatisMapperVar.MapperTag;
import com.justdebugit.codegen.variables.MybatisSqlVar.MybatisSqlTagVar;
import com.justdebugit.codegen.variables.MybatisVar;
import com.justdebugit.codegen.variables.MybatisVar.ControlVar;
import com.justdebugit.codegen.variables.MybatisVar.ModelMethod;
import com.justdebugit.codegen.variables.MybatisVar.OtherVar;

@Component
public class ProjectOut implements OuterConfig{
  
  @CodeGen(value= "mybatis/mybatis-config",path="mybatis/mybatis-config.xml",type=OuterType.RESOURCE)
  public MybatisConfigVar render(MybatisVar mybatisVar){
    return mybatisVar.mybatisConfigVar;
  }
  
  @CodeGen(value="mybatis/mybatis-mapper")
  public List<MapperTag> renderMapper(MybatisVar mybatisVar){
    return mybatisVar.mybatisMapperVar.mapperTags;
  }
  
  @CodeGen(value="mybatis/mybatis-sql",path="mapper/${tag}.xml",type=OuterType.RESOURCE)
  public List<MybatisSqlTagVar> renderSql(MybatisVar mybatisVar){
    return mybatisVar.mybatisSqlVar.sqlTagVars;
  }

  
  @CodeGen(value="core/application",path="application.properties",type=OuterType.RESOURCE)
  public OtherVar application(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="core/logback",path="logback.xml",type=OuterType.RESOURCE)
  public OtherVar logback(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="core/AppApplication",type=OuterType.JAVA)
  public OtherVar mainClazz(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="core/AppApplicationTests",type=OuterType.TEST)
  public OtherVar mainClazzTest(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="core/build",path="build.sh",type=OuterType.FILE)
  public OtherVar build(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="core/boot",path="boot.sh",type=OuterType.FILE)
  public OtherVar control(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="core/pom",path="pom.xml",type=OuterType.FILE)
  public OtherVar pom(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  
  @CodeGen(value="core/CodeMsg",type=OuterType.JAVA)
  public OtherVar codeMsg(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="core/CommonConfig",type=OuterType.JAVA)
  public OtherVar commonConfig(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="core/XController",type=OuterType.JAVA)
  public List<ControlVar> XController(MybatisVar mybatisVar){
    return mybatisVar.controlVars;
  }
  
  @CodeGen(value="core/XService",type=OuterType.JAVA)
  public List<ModelMethod> XService(MybatisVar mybatisVar){
    return mybatisVar.serviceVars;
  }
  
  
  @CodeGen(value="core/XControllerTest",type=OuterType.TEST)
  public List<ControlVar> XControllerTest(MybatisVar mybatisVar){
    return mybatisVar.controlVars;
  }
  
  @CodeGen(value="core/IllegalParamsException",type=OuterType.JAVA)
  public OtherVar IllegalParamsException(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="core/Exception2CodeMap",type=OuterType.JAVA)
  public OtherVar exception2CodeMap(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="core/WithTypeException",type=OuterType.JAVA)
  public OtherVar withTypeException(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="core/GlobalExceptionHandler",type=OuterType.JAVA)
  public OtherVar globalExceptionHandler(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  
  @CodeGen(value="core/DependencyException",type=OuterType.JAVA)
  public OtherVar dependencyException(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="core/RestResponse",type=OuterType.JAVA)
  public OtherVar restResponse(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="core/RestConfigs",type=OuterType.JAVA)
  public OtherVar restConfigs(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }

  @CodeGen(value="autoconf/HttpClientProperties",type=OuterType.JAVA)
  public OtherVar httpClientProperties(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }

  @CodeGen(value="autoconf/RestAutoConfiguration",type=OuterType.JAVA)
  public OtherVar restAutoConfiguration(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="autoconf/spring-factories",path="META-INF/spring.factories",type=OuterType.RESOURCE)
  public OtherVar springfactories(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }

  @CodeGen(value="log/LogEncoder",type=OuterType.JAVA)
  public OtherVar logEncoder(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="log/MapBasicMarker",type=OuterType.JAVA)
  public OtherVar mapBasicMarker(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="log/MapMarker",type=OuterType.JAVA)
  public OtherVar mapMarker(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="log/MsgConverter",type=OuterType.JAVA)
  public OtherVar msgConverter(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="logbook/BodyReplacedHttpResponse",type=OuterType.JAVA)
  public OtherVar bodyReplacedHttpResponse(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="logbook/CompleteCurlHttpLogFormatter",type=OuterType.JAVA)
  public OtherVar completeCurlHttpLogFormatter(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="logbook/CompleteHttpLogFormatter",type=OuterType.JAVA)
  public OtherVar completeHttpLogFormatter(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="logbook/CutOffBodyResponseFilter",type=OuterType.JAVA)
  public OtherVar cutOffBodyResponseFilter(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="logbook/FormBodyConstructs",type=OuterType.JAVA)
  public OtherVar formBodyConstructs(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="logbook/SimpleLogbookAutoConfiguration",type=OuterType.JAVA)
  public OtherVar simpleLogbookAutoConfiguration(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="logbook/SimpleLogbookProperties",type=OuterType.JAVA)
  public OtherVar simpleLogbookProperties(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="logbook/WebAppTraceFilter",type=OuterType.JAVA)
  public OtherVar webAppTraceFilter(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  
  @CodeGen(value="utils/BeanHelper",type=OuterType.JAVA)
  public OtherVar beanHelper(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  
  @CodeGen(value="utils/MultiValueMaps",type=OuterType.JAVA)
  public OtherVar multiValueMaps(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  

  @CodeGen(value="utils/Query",type=OuterType.JAVA)
  public OtherVar query(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="utils/RestConfig",type=OuterType.JAVA)
  public OtherVar restConfig(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="utils/Rests",type=OuterType.JAVA)
  public OtherVar rests(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="utils/RestService",type=OuterType.JAVA)
  public OtherVar restService(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  
  @CodeGen(value="utils/Retrys",type=OuterType.JAVA)
  public OtherVar retrys(MybatisVar mybatisVar){
    return mybatisVar.otherVar;
  }
  
  @CodeGen(value="utils/Traces",type=OuterType.JAVA)
  public OtherVar traces(MybatisVar mybatisVar){
	  return mybatisVar.otherVar;
  }
  
  
}
