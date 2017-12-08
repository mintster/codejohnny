package com.codejohnny.db.cn;

import com.codejohnny.business.CodeJohnnyConnections;
import com.google.inject.Inject;

/**
 * Created by daveburke on 6/17/17.
 */
public class MySqlConnection implements IConnection {

    @Inject
    CodeJohnnyConnections codeJohnnyConnections;

    @Override
    public CodeJohnnyConnection get() {
        // Connection Name property from connections.xml listed in template
        return codeJohnnyConnections.getCurrentConnection(null);
    }
}
