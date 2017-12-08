package com.codejohnny.containers;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
public class CodeJohnnyProperty {

	@XmlAttribute
	public String name;
	
	@XmlAttribute
	public String value;
	
	@XmlAttribute
	public Class<?> type;
	
	@XmlAttribute
	public String category;
	
	@XmlAttribute
	public String description;
	
	@XmlAttribute
	public String defaultvalue;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	public Class<?> getType() {
		return type;
	}
	
	public void setType(Class<?> type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	 
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	 
	public String getDefaultvalue() {
		return defaultvalue;
	}
	
	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	@Override
	public String toString() {
		return "CodeJohnnyProperty{" +
				"name='" + name + '\'' +
				", value='" + value + '\'' +
				", type=" + type +
				", category='" + category + '\'' +
				", description='" + description + '\'' +
				", defaultvalue='" + defaultvalue + '\'' +
				'}';
	}
}
