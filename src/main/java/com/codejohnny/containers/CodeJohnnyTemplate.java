package com.codejohnny.containers;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "template")
@XmlAccessorType (XmlAccessType.PROPERTY)
public class CodeJohnnyTemplate {

	public CodeJohnnyTemplate() {
	};

	public String name;
	public String description;
	public String dataclass;
	public String datatable;
	public CodeJohnnyMustache codeJohnnyMustache;
	public  List<CodeJohnnyProperty> properties;
	public  List<CodeJohnnyMethod> methods;
	public String content;
	public String connection;

	@XmlTransient
	public  List<CodeJohnnyColumn> columns;

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	@XmlElementWrapper(name = "properties")
    @XmlElement(name = "property", required = true)
	public List<CodeJohnnyProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<CodeJohnnyProperty> properties) {
		this.properties = properties;
	}

	@XmlElementWrapper(name = "methods")
    @XmlElement(name = "method", required = false)
	public List<CodeJohnnyMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<CodeJohnnyMethod> methods) {
		this.methods = methods;
	}

	public String getContent() {
		return content;
	}

	@XmlElement
	public void setContent(String content) {
		this.content = content;
	}

	public CodeJohnnyMustache getCodeJohnnyMustache() {
		return codeJohnnyMustache;
	}

	@XmlElement(name = "mustache", required = true)
	public void setCodeJohnnyMustache(CodeJohnnyMustache codeJohnnyMustache) {
		this.codeJohnnyMustache = codeJohnnyMustache;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public String getDataclass() {
		return dataclass;
	}

	public void setDataclass(String dataclass) {
		this.dataclass = dataclass;
	}

	@XmlElement
	public String getDatatable() {
		return datatable;
	}

	public void setDatatable(String datatable) {
		this.datatable = datatable;
	}

	@XmlElement
	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	@XmlTransient
	public List<CodeJohnnyColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<CodeJohnnyColumn> columns) {
		this.columns = columns;
	}

}
