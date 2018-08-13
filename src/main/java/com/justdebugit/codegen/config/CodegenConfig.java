package com.justdebugit.codegen.config;

public class CodegenConfig {
  
  public static CodegenConfig defaultConfig() {
    return new CodegenConfig();
  }
  
  private String packageName;
  
  
  private String  jsonClazz;
  
  private String  tmpDir  = System.getProperty("java.io.tmpdir");
  
  private String  baseDir = System.getProperty("user.dir");
  
  private String  defaultPack = "com.justdebugit.codegen";
  
  public String packageName() {
    return packageName;
  }

  public void packageName(String packageName) {
    this.packageName = packageName;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }


  public String getBaseDir() {
    return baseDir;
  }


  public String getJsonClazz() {
    return jsonClazz;
  }

  public void setJsonClazz(String jsonClazz) {
    this.jsonClazz = jsonClazz;
  }


  public String getTmpDir() {
	return tmpDir;
}

public String getDefaultPack() {
	return defaultPack;
}

public void setDefaultPack(String defaultPack) {
	this.defaultPack = defaultPack;
}

public void setTmpDir(String tmpDir) {
	this.tmpDir = tmpDir;
}

public void setBaseDir(String baseDir) {
    this.baseDir = baseDir;
  }
  
  

}
