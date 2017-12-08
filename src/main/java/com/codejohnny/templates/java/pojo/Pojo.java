package com.codejohnny.templates.java.pojo;

import com.codejohnny.containers.CodeJohnnyColumn;
import com.codejohnny.containers.CodeJohnnyTemplate;
import org.apache.commons.lang3.StringUtils;

public class Pojo {


	public Pojo(CodeJohnnyTemplate codeJohnnyTemplate) {
	}

	/**
	 * <P>Generates Variable Declarations. Typically used with a POJO.</P>
	 * 
	 * <B>Sample Output:</B>
	 * <PRE>
	 *	public int columnPosition;
	 *	public String columnName;
	 *	public long datatypeLength;
	 *	public Boolean isPrimary;
	 *</PRE>
	 *
	 *@param codeJohnnyTemplate XML file
	 *@return String
	 *
	 */
	public String propertyLoop(CodeJohnnyTemplate codeJohnnyTemplate) {

		String loopContent = StringUtils.EMPTY;
		String loopPattern = "public %s %s;\n";
		String loopPopulatedPattern = StringUtils.EMPTY;
		
		for (CodeJohnnyColumn codeJohnnyColumn : codeJohnnyTemplate.getColumns()) {
			loopPopulatedPattern = String.format(loopPattern, codeJohnnyColumn.javaDatatype,
					codeJohnnyColumn.columnCamelCase);
			loopContent += loopPopulatedPattern;
		}

		return loopContent;
		

	}

	/**
	 * <P>Generates Getter and Setters for all fields. Typically used with a POJO.</P>
	 * 
	 * <B>Sample Output:</B>
	 * <PRE>
 	 * public int getColumnPosition() {
 	 *     return columnPosition;
	 *  }
 	 * public void setColumnPosition(int columnPosition) {
 	 *     this.columnPosition = columnPosition;
	 *  }
	 *</PRE>
	 *
	 *@param codeJohnnyTemplate XML file
	 *@return String
	 *
	 */
	public String gettersetterLoop(CodeJohnnyTemplate codeJohnnyTemplate) {

		String loopContent = StringUtils.EMPTY;
		StringBuilder s = new StringBuilder();
		s.append("public %s get%s() {\n");
		s.append("return %s;\n");
		s.append("}\n\n");
		s.append("public void set%s(%s %s) {\n");
		s.append("this.%s = %s;\n");
		s.append("}\n\n");

		String loopPattern = s.toString();
		String loopPopulatedPattern = StringUtils.EMPTY;

		String lowerColumnName = StringUtils.EMPTY;
		String columnName = StringUtils.EMPTY;
		String javaType = StringUtils.EMPTY;

		for (CodeJohnnyColumn codeJohnnyColumn : codeJohnnyTemplate.getColumns()) {
			lowerColumnName = StringUtils.uncapitalize(codeJohnnyColumn.columnCamelCase);
			columnName = StringUtils.capitalize(codeJohnnyColumn.columnCamelCase);
			javaType = codeJohnnyColumn.javaDatatype;

			loopPopulatedPattern = String.format(loopPattern, javaType, columnName, lowerColumnName, columnName,
					javaType, lowerColumnName, lowerColumnName, lowerColumnName);
			loopContent += loopPopulatedPattern;
		}

		return loopContent;
	}


	// endregion
	
	
}
