package com.codejohnny.templates;

import com.codejohnny.containers.CodeJohnnyTemplate;
import com.codejohnny.core.CodeJohnnyUtils;
import com.codejohnny.utils.TemplateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class PropertiesTest extends TemplateTestBase {

    @Test
    public void propertyByTypeTest() {

        String template = "test/propertiesIT.xml";
        CodeJohnnyTemplate testTemplate = TemplateUtils.getCodeJohnnyTemplate(template);
        codeJohnnyTemplates.generateSourceFromFile(template);

        // Properties Now Populated
        Boolean addIdTrue = CodeJohnnyUtils.getBooleanProperty(testTemplate, "addIdTrue");
        assertTrue(addIdTrue);

        Boolean addIdFalse = CodeJohnnyUtils.getBooleanProperty(testTemplate, "addIdFalse");
        assertFalse(addIdFalse);

        Integer intValue = CodeJohnnyUtils.getIntegerProperty(testTemplate, "intValue");
        assertEquals(intValue.intValue(), 100);
    }

    public String testLoop(CodeJohnnyTemplate codeJohnnyTemplate) {
        return "got you something";
    }
}
