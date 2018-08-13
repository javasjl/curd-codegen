package com.justdebugit.codegen.codec.sql;

import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.util.JdbcUtils;
import com.google.common.base.CharMatcher;
import com.justdebugit.codegen.codec.Parser;
import com.justdebugit.codegen.config.CodegenConfig;

public class MysqlDdlSqlParser implements Parser<String,DatabaseDefinition>{
  
  private static final String DB_NAME = "fakeDbName";
  
  private static final CharMatcher CHAR_MATCHER = CharMatcher.anyOf("'`");

  @Override
  public DatabaseDefinition parse(String source) {
    return parse(source,CodegenConfig.defaultConfig());
  }

  @Override
  public DatabaseDefinition parse(String source, CodegenConfig config) {
    List<SQLStatement> list = SQLUtils.parseStatements(source, JdbcUtils.MYSQL);
    DatabaseDefinition databaseDefinition = new DatabaseDefinition();
    databaseDefinition.setDbName(DB_NAME);
    list.stream()
        .filter(MySqlCreateTableStatement.class::isInstance)
        .map(MySqlCreateTableStatement.class::cast)
        .forEach(item -> {
           databaseDefinition.addTable(fromTableElement(item));
        });
    return databaseDefinition;
  }
  
  private static TableDefinition fromTableElement(MySqlCreateTableStatement statement) {
    TableDefinition tableDefinition = new TableDefinition();
    tableDefinition.setTableName(CHAR_MATCHER.removeFrom(statement.getName().getSimpleName()));
    SQLObject sqlObject = statement.getTableOptions().get("COMMENT");
    tableDefinition.setTableComment(sqlObject == null?"":sqlObject.toString());
    List<String> config = statement.getBeforeCommentsDirect();
    if (config != null && !config.isEmpty()) {
      tableDefinition.setTableConfig(config.get(0));
    }
    List<TableFieldDefinition> fieldDefinitions =  statement
       .getTableElementList()
       .stream()
       .filter(b -> b instanceof SQLColumnDefinition)
       .map(SQLColumnDefinition.class::cast)
       .map(item -> {
            TableFieldDefinition fDefinition = new TableFieldDefinition();
            
            String fieldName = CHAR_MATCHER.removeFrom(item.getName().getSimpleName());
            fDefinition.setField(fieldName);
            
            fDefinition.setDefaultVal(item.getDefaultExpr() != null ? CHAR_MATCHER.removeFrom(item.getDefaultExpr().toString()) :"");
            fDefinition.setType(item.getDataType().getName());
            
            String comment =   item.getComment() != null ? CHAR_MATCHER.removeFrom(item.getComment().toString()) :"";
            fDefinition.setComment(comment);
            return fDefinition;
    }).collect(Collectors.toList());
    tableDefinition.addAllField(fieldDefinitions);
    return tableDefinition;
  }

  

  

}
