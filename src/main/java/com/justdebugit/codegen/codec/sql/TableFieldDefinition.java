package com.justdebugit.codegen.codec.sql;

public class TableFieldDefinition {
  
  private String field;
  private String type;
  private String defaultVal;
  private boolean required;
  private String  comment = "";
  private boolean isIndex;
  private boolean isUniq;
  private String  joinTableField;
  
  public String getField() {
    return field;
  }
  public void setField(String field) {
    this.field = field;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getDefaultVal() {
    return defaultVal;
  }
  public void setDefaultVal(String defaultVal) {
    this.defaultVal = defaultVal;
  }
  public boolean isRequired() {
    return required;
  }
  public void setRequired(boolean required) {
    this.required = required;
  }
  public String getComment() {
    return comment;
  }
  public void setComment(String comment) {
    this.comment = comment;
  }
  public boolean isIndex() {
    return isIndex;
  }
  public void setIndex(boolean isIndex) {
    this.isIndex = isIndex;
  }
  public boolean isUniq() {
    return isUniq;
  }
  public void setUniq(boolean isUniq) {
    this.isUniq = isUniq;
  }
  public String getJoinTableField() {
    return joinTableField;
  }
  public void setJoinTableField(String joinTableField) {
    this.joinTableField = joinTableField;
  }
  @Override
  public String toString() {
    return "TableDefinition [field=" + field + ", type=" + type + ", defaultVal=" + defaultVal
        + ", required=" + required + ", comment=" + comment + ", isIndex=" + isIndex + ", isUniq="
        + isUniq + ", joinTableField=" + joinTableField + "]";
  }
  
  

}
