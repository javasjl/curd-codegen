package com.justdebugit.codegen.codec.sql;

import java.util.List;

import com.google.common.collect.Lists;

public class TableDefinition {
  private String tableName;
  
  private String tableComment;
  
  private String tableConfig;
  
  private List<TableFieldDefinition> fields = Lists.newArrayList();

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public List<TableFieldDefinition> getFields() {
    return fields;
  }

  public String getTableConfig() {
    return tableConfig;
  }

  public void setTableConfig(String tableConfig) {
    this.tableConfig = tableConfig;
  }

  public void setFields(List<TableFieldDefinition> fields) {
    this.fields = fields;
  }
  
  public void addField(TableFieldDefinition field) {
    this.fields.add(field);
  }
  
  public void addAllField(List<TableFieldDefinition> fields) {
    this.fields.addAll(fields);
  }

  public String getTableComment() {
    return tableComment;
  }

  public void setTableComment(String tableComment) {
    this.tableComment = tableComment;
  }

  @Override
  public String toString() {
    return "TableDefinition [tableName=" + tableName + ", tableComment=" + tableComment
        + ", fields=" + fields + "]";
  }

  
  

}
