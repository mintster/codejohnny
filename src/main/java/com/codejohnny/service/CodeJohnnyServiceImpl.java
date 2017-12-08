package com.codejohnny.service;

import com.codejohnny.core.CodeJohnnyConfiguration;
import com.codejohnny.db.cn.CodeJohnnyConnection;
import com.codejohnny.containers.CodeJohnnyUser;
import com.codejohnny.core.CodeJohnnyCache;
import com.codejohnny.db.ICodeJohnnyDb;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class CodeJohnnyServiceImpl implements ICodeJohnnyService {

	private ICodeJohnnyDb codeJohnnyDb;

	private static final Logger logger = LoggerFactory.getLogger(CodeJohnnyServiceImpl.class);

	@Inject
	public CodeJohnnyServiceImpl(ICodeJohnnyDb codeJohnnyDb) {
		this.codeJohnnyDb = codeJohnnyDb;
	}

	// region CodeJohnny User Samples

	@Override
	public List<CodeJohnnyUser> getCodeJohnnyUsers(CodeJohnnyConnection connection) {
		return getCodeJohnnyUsers(false, connection);
	}

	@Override
	public List<CodeJohnnyUser> getCodeJohnnyUsers() {
		return getCodeJohnnyUsers(true, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CodeJohnnyUser> getCodeJohnnyUsers(boolean useCached, CodeJohnnyConnection connection) {

		String key = CodeJohnnyUserListCacheKey();

		List<CodeJohnnyUser> codeJohnnyUsers = (List<CodeJohnnyUser>) CodeJohnnyCache.getInstance().get(key);
		if (codeJohnnyUsers == null || !useCached) {
			try {
				codeJohnnyDb.getCodeJohnnyUsers();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			CodeJohnnyCache.getInstance().put(key, (Serializable) codeJohnnyUsers);
		}

		return codeJohnnyUsers;
	}

	// endregion

	// region caching keys

	private String CodeJohnnyUserListCacheKey() {
		return String.format("CodeJohnnyUserList-%s", CodeJohnnyConfiguration.get().applicationId);
	}

	// endregion

}
