package com.justdebugit.codegen.codec.sql;

import java.util.List;

import com.google.common.collect.Lists;

public class DatabaseDefinition {
  
  private String dbName;
  
  private List<TableDefinition> tables = Lists.newArrayList();


  public List<TableDefinition> getTables() {
    return tables;
  }

  public void setTables(List<TableDefinition> tables) {
    this.tables = tables;
  }
  
  public void addTable(TableDefinition table) {
    this.tables.add(table);
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  @Override
  public String toString() {
    return "DatabaseDefinition [dbname=" + dbName + ", tables=" + tables + "]";
  }
  
  

}
