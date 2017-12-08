package com.codejohnny.db;

import com.codejohnny.containers.CodeJohnnyColumn;
import com.codejohnny.containers.CodeJohnnyUser;
import com.codejohnny.db.cn.CodeJohnnyConnection;
import com.codejohnny.db.cn.IConnection;

import java.sql.*;

public abstract class CodeJohnnySql {

	protected final IConnection iConnection;

	private Connection connection;
	private Statement statement;
	private CallableStatement callablestatement;

	protected CodeJohnnySql(IConnection iConnection) {
		this.iConnection = iConnection;
	}


	// region MySQL Connection and Query Processes
	protected Connection mySqlConnection() throws ClassNotFoundException, SQLException {
		CodeJohnnyConnection cn = iConnection.get();
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(cn.getUrl(),
				cn.username, cn.password);
	}

	protected ResultSet mySqlQuery(String query) {
		ResultSet rs = null;
		try {

			this.connection = mySqlConnection();
			this.statement = this.connection.createStatement();
			rs = this.statement.executeQuery(query);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}


	protected CallableStatement mySqlCall(String statement) {
		try {

			this.connection = mySqlConnection();
			this.callablestatement = this.connection.prepareCall(statement);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return callablestatement;
	}

	protected void mySqlClose() throws SQLException {
		if (!this.connection.isClosed())
			this.connection.close();
		if (!this.statement.isClosed())
			this.statement.close();
	}

	protected void mySqlCallClose() throws SQLException {
		if (!this.connection.isClosed())
			this.connection.close();
		if (!this.callablestatement.isClosed())
			this.callablestatement.close();
	}

	// endregion

	// region Populate List Objects from ResultSets

	public void populateCodeJohnnyUserList(ResultSet rs, CodeJohnnyUser codeJohnnyUser) throws SQLException {
		codeJohnnyUser.setUserId(rs.getInt("user_id"));
		codeJohnnyUser.setFirstName(rs.getString("first_name"));
		codeJohnnyUser.setLastName(rs.getString("last_name"));
	}

	public void populateCodeJohnnyColumnList(ResultSet rs, CodeJohnnyColumn codeJohnnyColumn)
			throws SQLException {

		codeJohnnyColumn.setColumnPosition(rs.getInt("columnPosition"));
		codeJohnnyColumn.setColumnName(rs.getString("columnName"));
		codeJohnnyColumn.setColumnCamelCase(rs.getString("columnCamelCase"));
		codeJohnnyColumn.setSqlDatatype(rs.getString("sqlDatatype"));
		codeJohnnyColumn.setJavaDatatype(rs.getString("javaDatatype"));
		codeJohnnyColumn.setVarcharLength(rs.getLong("varcharLength"));
		codeJohnnyColumn.setDatatypeLength(rs.getInt("datatypeLength"));
		codeJohnnyColumn.setIsPrimary(rs.getBoolean("isPrimary"));
		codeJohnnyColumn.setIsLastColumn(rs.getBoolean("isLastColumn"));
		codeJohnnyColumn.setIsRequired(rs.getBoolean("isRequired"));
		codeJohnnyColumn.setColumnComment(rs.getString("columnComment"));

	}

	// endregion

}
