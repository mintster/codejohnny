package com.codejohnny.containers;

public class CodeJohnnyColumn {

	public CodeJohnnyColumn() {
	}

	public int columnPosition;
	public String columnName;
	public String columnCamelCase;
	public String sqlDatatype;
	public String javaDatatype;
	public long varcharLength;
	public long datatypeLength;
	public Boolean isPrimary;
	public Boolean isLastColumn;
	public Boolean isRequired;
	public String columnComment;
	
	public int getColumnPosition() {
		return columnPosition;
	}
	public void setColumnPosition(int columnPosition) {
		this.columnPosition = columnPosition;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnCamelCase() {
		return columnCamelCase;
	}
	public void setColumnCamelCase(String columnCamelCase) {
		this.columnCamelCase = columnCamelCase;
	}
	public String getSqlDatatype() {
		return sqlDatatype;
	}
	public void setSqlDatatype(String sqlDatatype) {
		this.sqlDatatype = sqlDatatype;
	}
	public String getJavaDatatype() {
		return javaDatatype;
	}
	public void setJavaDatatype(String javaDatatype) {
		this.javaDatatype = javaDatatype;
	}
	public long getVarcharLength() {
		return varcharLength;
	}
	public void setVarcharLength(long varcharLength) {
		this.varcharLength = varcharLength;
	}
	public long getDatatypeLength() {
		return datatypeLength;
	}
	public void setDatatypeLength(long datatypeLength) {
		this.datatypeLength = datatypeLength;
	}
	public Boolean getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	public Boolean getIsLastColumn() {
		return isLastColumn;
	}
	public void setIsLastColumn(Boolean isLastColumn) {
		this.isLastColumn = isLastColumn;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public Boolean getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

}
