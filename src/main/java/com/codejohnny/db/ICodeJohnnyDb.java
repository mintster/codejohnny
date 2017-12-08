package com.codejohnny.db;

import com.codejohnny.containers.CodeJohnnyColumn;
import com.codejohnny.containers.CodeJohnnyUser;

import java.sql.SQLException;
import java.util.List;

public interface ICodeJohnnyDb {

    public List<CodeJohnnyUser> getCodeJohnnyUsers() throws SQLException;
    public List<CodeJohnnyColumn> getCodeJohnnyColumns(String table) throws SQLException;

}
