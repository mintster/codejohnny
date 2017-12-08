package com.codejohnny.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CodeJohnnyConfiguration implements java.io.Serializable {

	// region properties

	private static final Logger logger = LoggerFactory.getLogger(CodeJohnnyConfiguration.class);


	private static final long serialVersionUID = -720348471534321068L;

	public String applicationId;
	public String codejohnnyDirectory;

	// endregion
	
	// region get() 
	
	public static CodeJohnnyConfiguration get() {

		CodeJohnnyConfiguration config = (CodeJohnnyConfiguration) CodeJohnnyCache.getInstance().get("CodeJohnnyConfiguration");
		if (config == null) {
			config = new CodeJohnnyConfiguration();
			CodeJohnnyCache.getInstance().put("CodeJohnnyConfiguration", config);
		}
		return config;
	}

	public CodeJohnnyConfiguration() {

			InputStream propFile = getClass().getResourceAsStream("/codejohnny.properties");
			Properties properties = new Properties(System.getProperties());
			try {
				properties.load(propFile);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}

			this.applicationId = properties.getProperty("application.id");
			this.codejohnnyDirectory = properties.getProperty("codejohnny.directory");

	}
	
	// endregion
	
}
