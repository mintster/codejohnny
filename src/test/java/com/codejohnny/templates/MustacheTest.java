package com.codejohnny.templates;

import com.codejohnny.business.CodeJohnnyModule;
import com.codejohnny.containers.CodeJohnnyProperty;
import com.codejohnny.containers.CodeJohnnyTemplate;
import com.codejohnny.db.ICodeJohnnyDb;
import com.codejohnny.utils.TemplateUtils;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class MustacheTest extends TemplateTestBase {

    private CodeJohnnyTemplate testTemplate;
    private String template = "test/mustacheIT.xml";

    @Test
    public void loadTemplateTest() {
        // processMustache() runs
        Assert.assertTrue(templateContents().contains("MUSTACHE TRUE IS PRESENT!"));
        Assert.assertTrue(templateContents().contains("Ted Jenkins"));
    }

    public String processMustache(String content, CodeJohnnyTemplate codeJohnnyTemplate) throws UnsupportedEncodingException, SQLException {

        Injector injector = Guice.createInjector(new CodeJohnnyModule());
        this.codeJohnnyDb= injector.getInstance(ICodeJohnnyDb.class);

        Mustache m = getMustache(content);
        StringWriter sw = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("users", codeJohnnyDb.getCodeJohnnyUsers());
        addBooleanProperties(model, codeJohnnyTemplate.getProperties());
        m.execute(sw, model);
        return sw.toString();
    }

    /**
     * Testing both iterations and properties
     */
    @Test
    public void mustacheMultiTest() throws SQLException, UnsupportedEncodingException {
        Mustache m = getMustache();
        StringWriter sw = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("users", codeJohnnyDb.getCodeJohnnyUsers());
        addBooleanProperties(model);
        m.execute(sw, model);
        String result = sw.toString();
        Assert.assertTrue(result.contains("Ted Jenkins"));
    }

    @Test
    public void mustacheIterationTest() throws SQLException, UnsupportedEncodingException {
        Mustache m = getMustache();
        StringWriter sw = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("users", codeJohnnyDb.getCodeJohnnyUsers());
        m.execute(sw, model);
        String result = sw.toString();
        Assert.assertTrue(result.contains("Ted Jenkins"));
    }

    @Test
    public void mustacheBooleanTest() throws UnsupportedEncodingException {
        Mustache m = getMustache();
        StringWriter sw = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        addBooleanProperties(model);
        m.execute(sw, model);
        String result = sw.toString();
        Assert.assertTrue(result.contains("MUSTACHE TRUE IS PRESENT!"));
    }

    private Mustache getMustache() throws UnsupportedEncodingException {
        return getMustache(templateContents());
    }

    private Mustache getMustache(String content) throws UnsupportedEncodingException {
        String toparse = content;
        InputStream stream = new ByteArrayInputStream(toparse.getBytes(StandardCharsets.UTF_8.name()));
        InputStreamReader reader = new InputStreamReader(stream);
        MustacheFactory c = new DefaultMustacheFactory();
        return c.compile(reader, toparse);
    }

    private void addBooleanProperties(Map<String, Object> model) {
        List<CodeJohnnyProperty> properties = testTemplate.getProperties();
        for (CodeJohnnyProperty property : properties) {
            if (property.getType() != null) {
                if (property.getType().equals(Boolean.class)) {
                    model.put(property.getName(), Boolean.valueOf(property.getValue()));
                }
            }
        }
    }

    private void addBooleanProperties(Map<String, Object> model, List<CodeJohnnyProperty> properties) {
        for (CodeJohnnyProperty property : properties) {
            if (property.getType() != null) {
                if (property.getType().equals(Boolean.class)) {
                    model.put(property.getName(), Boolean.valueOf(property.getValue()));
                }
            }
        }
    }

    private String templateContents() {
        testTemplate = TemplateUtils.getCodeJohnnyTemplate(template);
        return codeJohnnyTemplates.generateSourceFromFile(template);
    }

    public String mustacheVar(CodeJohnnyTemplate codeJohnnyTemplate) {
        return "FOUND!";
    }

}
