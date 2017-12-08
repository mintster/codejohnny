package com.codejohnny.db;

import com.codejohnny.containers.CodeJohnnyColumn;
import com.codejohnny.containers.CodeJohnnyUser;

import com.codejohnny.db.cn.IConnection;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CodeJohnnyDbImpl extends CodeJohnnySql implements ICodeJohnnyDb {

	private static final Logger logger = LoggerFactory.getLogger(CodeJohnnyDbImpl.class);

	// region Properties

	@Inject
	public CodeJohnnyDbImpl(IConnection iConnection) {
		super(iConnection);
	}

	// endregion

	// region CodeJohnnyServiceImpl
	@Override
	public List<CodeJohnnyUser> getCodeJohnnyUsers() throws SQLException {
		{

			List<CodeJohnnyUser> CodeJohnnyUserList = new ArrayList<CodeJohnnyUser>();

			ResultSet rs = mySqlQuery("SELECT * FROM codejohnny_users ORDER BY user_id");

			CodeJohnnyUser codeJohnnyUser = null;
			while (rs.next()) {
				codeJohnnyUser = new CodeJohnnyUser();
				populateCodeJohnnyUserList(rs, codeJohnnyUser);
				CodeJohnnyUserList.add(codeJohnnyUser);
			}
			mySqlClose();

			return CodeJohnnyUserList;
		}
	}
	// endregion

	// region CodeJohnnyColumns

	@Override
	public List<CodeJohnnyColumn> getCodeJohnnyColumns(String table) throws SQLException {
		{

			List<CodeJohnnyColumn> CodeJohnnyColumnList = new ArrayList<CodeJohnnyColumn>();
			CallableStatement cs = mySqlCall("{call p_codejohnny_columns_get(?, ?)}");

			ResultSet rs = null;
			try {
				cs.setString(1, iConnection.get().database);
				cs.setString(2, table);
				rs = cs.executeQuery();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

			CodeJohnnyColumn codeJohnnyColumn = null;
			while (rs.next()) {
				codeJohnnyColumn = new CodeJohnnyColumn();
				populateCodeJohnnyColumnList(rs, codeJohnnyColumn);
				CodeJohnnyColumnList.add(codeJohnnyColumn);
			}
			mySqlCallClose();

			return CodeJohnnyColumnList;
		}
	}
	// endregion

}
