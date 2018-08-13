package com.justdebugit.codegen.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.justdebugit.codegen.config.CodegenConfig;
import com.justdebugit.codegen.utils.ResourceUtil;
import com.justdebugit.codegen.utils.ZipUtils;

@Service
public class CodeGenService {
   

   @Autowired
   private CodeGenBroker codeGenBroker;

  public String getGenSqlZip(String content, String packName) {
    CodegenConfig codegenConfig = new CodegenConfig();
    String baseDir = getBaseDir(codegenConfig);
    codegenConfig.setBaseDir(baseDir);
    if (StringUtils.isBlank(packName)) {
      packName = codegenConfig.getDefaultPack();
    }
    String beanPack = packName + ".bean";
    codegenConfig.setPackageName(beanPack);
    codeGenBroker.genSql(content, codegenConfig);
    return genFilePath(baseDir);
  }


  private String genFilePath(String baseDir) {
    String dist = ZipUtils.zipDir(baseDir);
    return dist;
  }

  private String getBaseDir(CodegenConfig config) {
    long tm = System.currentTimeMillis();
    String baseDir = config.getTmpDir() + "/down/" + tm;
    return baseDir;
  }

  public String genJavaFromJson(String json, String jsonClazz) {
    CodegenConfig codegenConfig = new CodegenConfig();
    if (Strings.isNullOrEmpty(jsonClazz)|| !jsonClazz.contains(".")) {
      jsonClazz = codegenConfig.getDefaultPack() + ".bean.Root";
    }
    codegenConfig.setJsonClazz(jsonClazz);
    codegenConfig.setBaseDir(getBaseDir(codegenConfig));
    codeGenBroker.genJava(json, codegenConfig);
    List<String> string = ResourceUtil.filesToString(codegenConfig.getBaseDir());
    return Joiner.on("\n\n\n").join(string);
  }
   
   
}
