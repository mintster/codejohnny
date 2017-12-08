package com.codejohnny.business;

import com.codejohnny.db.CodeJohnnyDbImpl;
import com.codejohnny.db.ICodeJohnnyDb;
import com.codejohnny.db.cn.IConnection;
import com.codejohnny.db.cn.MySqlConnection;
import com.codejohnny.service.CodeJohnnyServiceImpl;
import com.codejohnny.service.ICodeJohnnyService;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Created by daveburke on 6/17/17.
 */
public class CodeJohnnyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(IConnection.class).to(MySqlConnection.class);
        binder.bind(ICodeJohnnyDb.class).to(CodeJohnnyDbImpl.class);
        binder.bind(ICodeJohnnyTemplates.class).to(CodeJohnnyTemplates.class);
        binder.bind(ICodeJohnnyService.class).to(CodeJohnnyServiceImpl.class);
        binder.bind(ICodeJohnnyConnections.class).to(CodeJohnnyConnections.class);
    }
}
