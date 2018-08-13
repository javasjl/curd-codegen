package com.justdebugit.codegen.common;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.justdebugit.codegen.outconfig.CodeGen;
import com.justdebugit.codegen.outconfig.OuterType;
import com.justdebugit.codegen.utils.PropertyHelper;
import com.justdebugit.codegen.utils.ResourceUtil;
import com.samskivert.mustache.Mustache.Compiler;
import com.samskivert.mustache.Template;


@Component
public class MustacheWriter {
  
  private static final Logger logger = LoggerFactory.getLogger(MustacheWriter.class);
  
  private static final  Pattern PACK_REGEX  = Pattern.compile("^package " + "(.*)" + ";");
  private static final  Pattern CLAZZ_REGEX = Pattern.compile("\n(.*)(class|interface|enum)\\s+" + "(\\w+)" + "(\\s+|\\{|\\<)");
  
  @Autowired
  private Compiler compiler;
  
  public <T> void apply(T var, Object bean) {
    List<Method> methods = MethodUtils.getMethodsListWithAnnotation(bean.getClass(), CodeGen.class);
    
    methods = methods.stream().filter(m -> { 
         List<Class<?>> classes = Arrays.asList(m.getParameterTypes());
         return classes.contains(var.getClass());
    }).collect(Collectors.toList());
    
    methods.forEach(method -> {
      CodeGen codeGen = method.getAnnotation(CodeGen.class);
      String path = StringUtils.isBlank(codeGen.path()) ? method.getName() + Instant.now().getEpochSecond() : codeGen.path();
      String template = "mustache/" + codeGen.value() + ".mustache";
      Object targetVar =  getVar(var, bean, method);
      write(path, targetVar, template,codeGen.type());
    });
  }

  
  @SuppressWarnings({"rawtypes", "unchecked"})
  private  void write(String path,Object targetVar,String template,OuterType type) {
    if (List.class.isAssignableFrom(targetVar.getClass())) {
      List<Object> varList =  (List)targetVar;
       varList.forEach(var ->{
         Template tmp = compiler.compile(ResourceUtil.readString(template));
         String out =  tmp.execute(var);
         String filePath = getFilePath(path, var,type,out);
         ResourceUtil.writeToFile(filePath, out);
         logger.info("Write to file {} success",filePath);
       });
    }else {
      Template tmp = compiler.compile(ResourceUtil.readString(template));
      String out = tmp.execute(targetVar);
      String filePath = getFilePath(path, targetVar,type,out);
      File outFile = ResourceUtil.writeToFile(filePath, out);
      if (filePath.endsWith(".sh")) {
         outFile.setExecutable(true);
      }
      logger.info("Write to file {} success",filePath);
    }
  }


  private String getFilePath(String path, Object var,OuterType type,String out) {
    String filePath;
    boolean isJava = false;
    if (out.startsWith("package ")) {
       isJava = true;
    }
    if (isJava) {
       Pair<String, String> packClazz =  readPackAndClazz(out);
       filePath = filePathForJava(packClazz.getKey(), packClazz.getValue(),type);
     }else {
        String pathVar = PropertyHelper.replacePlaceholders(path, var);
        if (type != OuterType.FILE) {
          pathVar = "/src/main/resources/" + pathVar;
        }else {
          pathVar = "/" + pathVar;
        }
        filePath = CodeGenContext.getContext().codegenConfig().getBaseDir() + pathVar;
     }
    return filePath;
  }
  
  private Pair<String, String> readPackAndClazz(String out) {
    Matcher pm = PACK_REGEX.matcher(out);
    Matcher cm = CLAZZ_REGEX.matcher(out);
    String packName = "";
    String className= "";
    if (pm.find()) {
       packName =  pm.group(1);
    }
    if (cm.find()) {
       className = cm.group(3);
       className = className.trim();
       className = className.replaceAll("<.*>", "");
    }
    return ImmutablePair.of(packName, className);
  }


  public  String filePathForJava(String packName,String clazzName,OuterType type) {
    CodeGenContext context = CodeGenContext.getContext();
    String baseDir = context.codegenConfig().getBaseDir();
    if (type == OuterType.TEST) {
      baseDir = baseDir + "/src/test/java/" ;
    }else {
      baseDir = baseDir + "/src/main/java/" ;
    }
    return baseDir  + StringUtils.replace(packName, ".", "/") + "/" + clazzName +".java";
  }
  
  
  private <T> Object getVar(T var, Object bean, Method method) {
    Object targetVar;
    try {
        targetVar = method.invoke(bean, var);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    return targetVar ;
  }
  
}
