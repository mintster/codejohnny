package com.codejohnny.business;

import com.codejohnny.db.cn.CodeJohnnyConnection;

import java.util.List;

public interface ICodeJohnnyConnections {
    List<CodeJohnnyConnection> getConnections();

    CodeJohnnyConnection getCurrentConnection(String name);

    void clearCurrentConnectionCache();
}
