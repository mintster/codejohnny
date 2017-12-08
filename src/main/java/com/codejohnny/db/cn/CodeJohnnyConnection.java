package com.codejohnny.db.cn;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "connection")
@XmlAccessorType (XmlAccessType.FIELD)
public class CodeJohnnyConnection implements java.io.Serializable {

	private static final long serialVersionUID = 1268527199886230105L;

	public CodeJohnnyConnection() {
	};

	public String name;
	public String environment;
	public String username;
	public String password;
	public String urlbase;
	public String database;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrlbase() {
		return urlbase;
	}

	public void setUrlbase(String urlbase) {
		this.urlbase = urlbase;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUrl() {
		return this.urlbase + this.database;
	}


}
