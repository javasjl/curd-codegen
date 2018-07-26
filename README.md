# curd-codegen
micro-server curd-code-gen

# curd-codegen: codegen for CURD operation project


[![Build Status](https://travis-ci.org/wyzssw/curd-codegen.svg?branch=master)](https://travis-ci.org/wyzssw/curd-codegen)

## 使用


### 启动代码生成器
1. git clone git@github.com:wyzssw/curd-codegen.git
2. mvn clean package
3. java -jar target/curd-codegen.jar
4. 打开浏览器 访问 http://localhost:9798/index

体验地址:http://moviehaha.com:9798/index

### 使用代码生成器
1. 上传mysql sql建表语句,输入bean所在package名
2. 点击上传按钮，解压下载的zip包
3. vim src/main/resources/application.properties修改数据库地址/用户名/密码(`spring.druid开头的配置`)
4. 执行./build.sh 然后 执行./boot.sh start启动进程
4. 打开浏览器 访问 `http://localhost:9234/query/实体名称` (比如表名是t_blog那么实体名称即tBlog)

