package com.codejohnny.service;

import com.codejohnny.db.cn.CodeJohnnyConnection;
import com.codejohnny.containers.CodeJohnnyUser;

import java.util.List;

public interface ICodeJohnnyService {

    List<CodeJohnnyUser> getCodeJohnnyUsers(CodeJohnnyConnection connection);
    List<CodeJohnnyUser> getCodeJohnnyUsers();
    List<CodeJohnnyUser> getCodeJohnnyUsers(boolean useCached, CodeJohnnyConnection connection);

}
