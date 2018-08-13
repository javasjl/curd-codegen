package com.justdebugit.codegen.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.zeroturnaround.zip.ZipUtil;
import org.zeroturnaround.zip.commons.FileUtils;

public class ZipUtils {
	
  private static final String TMP_DIR = System.getProperty("java.io.tmpdir");
  
  public static String zipDir(String dir){
    ZipUtil.pack(new File(dir), new File(dir + ".zip"));
    return dir + ".zip";
  }
  
  public static List<String> unzip(byte[] data){
    File newfile = new File(TMP_DIR + "/unzip/"+System.currentTimeMillis());
    if (!newfile.exists()) {
      newfile.getParentFile().mkdirs();
      newfile.mkdir();
    }
    ZipUtil.unpack(new ByteArrayInputStream(data), newfile, Charset.forName("UTF-8"));
    try {
       return Files.walk(Paths.get(newfile.getAbsolutePath()))
      .filter(Files::isRegularFile)
      .map(Path::toFile)
      .map(file -> {
        try {
          return FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
          throw new IllegalArgumentException(e);
        }
      })
      .collect(Collectors.toList());
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
    
  }
  

}
