package com.codejohnny.business;

import com.codejohnny.core.CodeJohnnyConfiguration;
import com.codejohnny.core.CodeJohnnyGlobals;
import com.codejohnny.core.CodeJohnnyCache;
import com.codejohnny.db.cn.CodeJohnnyConnection;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;

@SuppressWarnings("Duplicates")
@XmlRootElement(name = "connections")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeJohnnyConnections implements ICodeJohnnyConnections {

	private static final Logger logger = LoggerFactory.getLogger(CodeJohnnyConnections.class);

	@Inject
	public CodeJohnnyConnections(){}

	@XmlElement(name = "connection")
	private List<CodeJohnnyConnection> connections = null;

	@Override
	public List<CodeJohnnyConnection> getConnections() {
		return connections;
	}

	public void setConnections(List<CodeJohnnyConnection> connections) {
		this.connections = connections;
	}

	@Override
	public CodeJohnnyConnection getCurrentConnection(String name) {

		CodeJohnnyConnections codeJohnnyConnections = null;
		CodeJohnnyConnection currentConnection = null;

		String cacheKey = CurrentConnectionCacheKey();
		currentConnection = (CodeJohnnyConnection) CodeJohnnyCache.getInstance().get(cacheKey);
		if (currentConnection == null) {
			try {
				JAXBContext jc = JAXBContext.newInstance(CodeJohnnyConnections.class);
				Unmarshaller unmarshaller = jc.createUnmarshaller();
				File xml = new File(CodeJohnnyGlobals.get().connectionsFile);
				codeJohnnyConnections = (CodeJohnnyConnections) unmarshaller.unmarshal(xml);
			} catch (JAXBException e) {
				e.printStackTrace();
			}

			currentConnection = codeJohnnyConnections.getConnections().stream()
					.filter(s -> s.name.equalsIgnoreCase(name)).findFirst().get();

			CodeJohnnyCache.getInstance().put(cacheKey, currentConnection);
		}
		return currentConnection;
	}

	private String CurrentConnectionCacheKey() {
		return String.format("CodeJohnnyCurrentConnection-%s", CodeJohnnyConfiguration.get().applicationId);
	}

	@Override
	public void clearCurrentConnectionCache() {
		try {
			CodeJohnnyCache.getInstance().remove(CurrentConnectionCacheKey());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
