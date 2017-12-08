package com.codejohnny;

import com.codejohnny.business.CodeJohnnyConnections;
import com.codejohnny.core.CodeJohnnyConfiguration;
import com.codejohnny.core.CodeJohnnyGlobals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by daveburke on 6/6/17.
 */
@SuppressWarnings("Duplicates")
@RunWith(JUnit4.class)
public class ConfigurationTest {

    @Test
    public void readPropertiesFile() {
        assertNotNull(CodeJohnnyConfiguration.get().applicationId);
    }

    @Test
    public void readGlobals() {
        CodeJohnnyGlobals globals = CodeJohnnyGlobals.get();
        assertThat(globals.templatesPath, endsWith("/templates/"));
    }

    @Test
    public void readConnectionsFromMarshalledXml() {
        CodeJohnnyConnections codeJohnnyConnections = null;

        try {
            JAXBContext jc = JAXBContext.newInstance(CodeJohnnyConnections.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            File xml = new File(this.getClass().getClassLoader().getResource("connections.xml").getFile());
            codeJohnnyConnections = (CodeJohnnyConnections) unmarshaller.unmarshal(xml);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        assertThat(codeJohnnyConnections.getConnections().size(), greaterThan(1));

    }

}
