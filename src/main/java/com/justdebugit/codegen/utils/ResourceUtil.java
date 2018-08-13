package com.justdebugit.codegen.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;

public class ResourceUtil {
  
  private static final String FACTORIES_RESOURCE_LOCATION = "META-INF/codegen.factories";
  
  public static void load(Properties properties,String fileClassPath) throws IOException{
    loadFromFilePath(properties, Resources.getResource(fileClassPath));
    
  }
  
  public static void loadFromFilePath(Properties properties,URL url) throws IOException{
    String str = Resources.toString(url, Charset.forName("utf-8"));
    properties.load(new StringReader(str));
  }

  public static String readString(String fileClassPath){
    URL url = Resources.getResource(fileClassPath);
    String text;
    try {
      text = Resources.toString(url, Charsets.UTF_8);
    } catch (IOException e) {
       throw new IllegalArgumentException(e);
    }
    return text;
  }
  
  public static Reader toReader(String fileClassPath) {
    return new StringReader(readString(fileClassPath));
  }
  
  public static File writeToFile(String filename, String contents) {
    File output = new File(filename);
    if (output.getParent() != null && !new File(output.getParent()).exists()) {
      File parent = new File(output.getParent());
      parent.mkdirs();
    }
    Writer out = null;
    try {
      out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"));
      out.write(contents);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }finally {
      try {
        out.close();
      } catch (IOException e) {}
    }
    return output;
  }
  
  
  public static String fileToString(MultipartFile file) {
    String content;
    try {
      content = new String(file.getBytes(), "UTF-8");
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
    return content;
  }
  
  public static List<String> filesToString(String folder) {
    List<String> list = Lists.newArrayList();
    try {
      Files
          .find(Paths.get(folder), Integer.MAX_VALUE, (filePath, fileAttr) -> fileAttr.isRegularFile())
          .forEach(path -> {
            try {
              list.add(new String(Files.readAllBytes(path)));
            } catch (IOException e) {
              throw new IllegalStateException(e);
            }
          });
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    return list;
  }
  
  
  public static List<String> loadFactoryNames(Class<?> factoryClass) {
    String factoryClassName = factoryClass.getName();
    ClassLoader classLoader = MoreObjects.firstNonNull(
        Thread.currentThread().getContextClassLoader(), Resources.class.getClassLoader());
    try {
        Enumeration<URL> urls = (classLoader != null ? classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
                ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
        List<String> result = new ArrayList<String>();
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(url));
            String factoryClassNames = properties.getProperty(factoryClassName);
            result.addAll(Arrays.asList(StringUtils.commaDelimitedListToStringArray(factoryClassNames)));
        }
        return result;
    }
    catch (IOException ex) {
        throw new IllegalArgumentException("Unable to load [" + factoryClass.getName() +
                "] factories from location [" + FACTORIES_RESOURCE_LOCATION + "]", ex);
    }
   }
  
  
  
}
