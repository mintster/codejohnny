package com.codejohnny.business;

import com.codejohnny.containers.CodeJohnnyColumn;
import com.codejohnny.db.cn.CodeJohnnyConnection;
import com.codejohnny.db.ICodeJohnnyDb;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.List;

public class CodeJohnnyColumns {

	private ICodeJohnnyDb codeJohnnyDb;

	@Inject
	public CodeJohnnyColumns(ICodeJohnnyDb codeJohnnyDb) {
		this.codeJohnnyDb = codeJohnnyDb;
	}

	// region Get Columns

	public List<CodeJohnnyColumn> getCodeJohnnyColumns(String table, CodeJohnnyConnection currentConnection) {
		List<CodeJohnnyColumn> codeJohnnyColumns = null;
		try {
			codeJohnnyColumns = codeJohnnyDb.getCodeJohnnyColumns(table);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return codeJohnnyColumns;
	}

	// endregion

}
