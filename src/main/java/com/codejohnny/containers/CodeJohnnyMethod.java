package com.codejohnny.containers;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
public class CodeJohnnyMethod {

	@XmlAttribute
	public String name;
	
	@XmlAttribute
	public String method;
	
	@XmlAttribute
	public String type;
	
	@XmlAttribute
	public String pattern;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getPattern() {
		return pattern;
	}
	
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}


	 
	
}
