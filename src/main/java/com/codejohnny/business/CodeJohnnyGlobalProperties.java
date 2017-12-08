package com.codejohnny.business;

import com.codejohnny.containers.CodeJohnnyGlobalProperty;
import com.codejohnny.core.CodeJohnnyGlobals;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "globalproperties")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeJohnnyGlobalProperties {

	public CodeJohnnyGlobalProperties() {
	};

	@XmlElement(name = "globalproperty")
	private List<CodeJohnnyGlobalProperty> globalproperties = null;

	public List<CodeJohnnyGlobalProperty> getGlobalProperties() {
		return globalproperties;
	}

	public void setGlobalProperties(List<CodeJohnnyGlobalProperty> globalproperties) {
		this.globalproperties = globalproperties;
	}

	public List<CodeJohnnyGlobalProperty> retrieveGlobalProperties() {

		CodeJohnnyGlobalProperties codeJohnnyGlobalProperties = null;
		List<CodeJohnnyGlobalProperty> properties = new ArrayList<CodeJohnnyGlobalProperty>();
		
		try {
			JAXBContext jc = JAXBContext.newInstance(CodeJohnnyGlobalProperties.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			File xml = new File(CodeJohnnyGlobals.get().globalPropertiesFile);
			codeJohnnyGlobalProperties = (CodeJohnnyGlobalProperties) unmarshaller.unmarshal(xml);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		for (CodeJohnnyGlobalProperty property : codeJohnnyGlobalProperties.getGlobalProperties())
		{
			properties.add(property);
		}
		return properties;
	}


}
