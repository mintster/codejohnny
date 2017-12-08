package com.codejohnny.business;

import com.codejohnny.containers.CodeJohnnyColumn;
import com.codejohnny.containers.CodeJohnnyGlobalProperty;
import com.codejohnny.containers.CodeJohnnyProperty;
import com.codejohnny.containers.CodeJohnnyTemplate;
import com.codejohnny.core.CodeJohnnyGlobals;
import com.codejohnny.core.CodeJohnnyUtils;
import com.codejohnny.db.cn.CodeJohnnyConnection;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeJohnnyTemplates implements ICodeJohnnyTemplates {

    private CodeJohnnyGlobalProperties codeJohnnyGlobalProperties;
    private CodeJohnnyColumns codeJohnnyColumns;
    private CodeJohnnyMethods codeJohnnyMethods;
    private ICodeJohnnyConnections codeJohnnyConnections;

    @Inject
    public CodeJohnnyTemplates(CodeJohnnyGlobalProperties codeJohnnyGlobalProperties, CodeJohnnyColumns codeJohnnyColumns, CodeJohnnyMethods codeJohnnyMethods, ICodeJohnnyConnections codeJohnnyConnections) {
        this.codeJohnnyGlobalProperties = codeJohnnyGlobalProperties;
        this.codeJohnnyColumns = codeJohnnyColumns;
        this.codeJohnnyMethods = codeJohnnyMethods;
        this.codeJohnnyConnections = codeJohnnyConnections;
    }

    // region Unmarshall XML call generate source code methods

    @Override
    public String generateSourceFromStream(String stream) {
        InputStream is = new ByteArrayInputStream(stream.getBytes());
        CodeJohnnyTemplate codeJohnnyTemplate = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(CodeJohnnyTemplate.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            codeJohnnyTemplate = (CodeJohnnyTemplate) unmarshaller.unmarshal(is);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return generateSource(codeJohnnyTemplate);
    }


    @Override
    public String generateSourceFromFile(String filename) {
        CodeJohnnyTemplate codeJohnnyTemplate = null;

        try {
            JAXBContext jc = JAXBContext.newInstance(CodeJohnnyTemplate.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            File xml = new File(CodeJohnnyGlobals.get().templatesPath + filename);
            codeJohnnyTemplate = (CodeJohnnyTemplate) unmarshaller.unmarshal(xml);
        } catch (JAXBException e) {
            System.out.println("Template at location [" + filename.toUpperCase() + "] not found. Check template path and filename.");
            System.exit(0);
        }

        return generateSource(codeJohnnyTemplate);
    }

    // endregion

    // region Generate Source Code from Parsed Template Content

    private String generateSource(CodeJohnnyTemplate codeJohnnyTemplate) {

        // get xml template

        // CodeJohnnyTemplate codeJohnnyTemplate = getXmlTemplate(filename);

        // set template data connection
        String s = codeJohnnyTemplate.datatable;

        codeJohnnyConnections.clearCurrentConnectionCache();
        CodeJohnnyConnection codeJohnnyConnection = codeJohnnyConnections.getCurrentConnection(codeJohnnyTemplate.connection);

        // populate source table columns

        codeJohnnyTemplate.setColumns(codeJohnnyColumns.getCodeJohnnyColumns(codeJohnnyTemplate.datatable, codeJohnnyConnection));

        // obtain original template source to pass among template populating processes

        String sourceContent = codeJohnnyTemplate.getContent();

        // 1 -- process method tags in form of {{~ method1 }} {{~ method2 }}

        if (codeJohnnyTemplate.getMethods().size() > 0) {
            sourceContent = codeJohnnyMethods.populateMethods(sourceContent, codeJohnnyTemplate);
        }

        // 2 -- process CodeJohnny base properties - {baseclass} {dataclass} {lowerpluraldataclass} etc

        sourceContent = populateBaseProperties(sourceContent, codeJohnnyTemplate);

        // 3 -- process template properties from [template].xml file in form of {var1} {var2} etc

        if (codeJohnnyTemplate.getProperties().size() > 0) {
            sourceContent = populateCustomProperties(sourceContent, codeJohnnyTemplate.getProperties());

        }

        // 4 -- process global properties from /config/globalproperties.xml in template source {global1} {global2} etc

        if (codeJohnnyGlobalProperties.retrieveGlobalProperties().size() > 0) {
            sourceContent = populateGlobalProperties(sourceContent, codeJohnnyGlobalProperties.retrieveGlobalProperties());
        }

        // 5 -- process any Mustache regions {{# listname }}{{/ listname }} using Template Class processMustacheTags() method
        if (codeJohnnyTemplate.codeJohnnyMustache != null) {
            sourceContent = codeJohnnyMethods.processMustacheTags(sourceContent,codeJohnnyTemplate);
        }

        codeJohnnyTemplate.setContent(sourceContent);
        return codeJohnnyTemplate.getContent();
    }

    // endregion

    // region Get Template

    private CodeJohnnyTemplate getXmlTemplateFromInputStream(String stream) {
        InputStream is = new ByteArrayInputStream(stream.getBytes());

        CodeJohnnyTemplate codeJohnnyTemplate = null;

        try {
            JAXBContext jc = JAXBContext.newInstance(CodeJohnnyTemplate.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            codeJohnnyTemplate = (CodeJohnnyTemplate) unmarshaller.unmarshal(is);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return codeJohnnyTemplate;

    }

    private CodeJohnnyTemplate getXmlTemplate(String filename) {
        CodeJohnnyTemplate codeJohnnyTemplate = null;

        try {
            JAXBContext jc = JAXBContext.newInstance(CodeJohnnyTemplate.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            File xml = new File(filename);
            codeJohnnyTemplate = (CodeJohnnyTemplate) unmarshaller.unmarshal(xml);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return codeJohnnyTemplate;
    }

    // endregion

    // region Populate Templates

    public String populateBaseProperties(String content, CodeJohnnyTemplate codeJohnnyTemplate) {

        Map<String, String> baseProperties = new HashMap<String, String>();

        baseProperties.put("{dataclass}", codeJohnnyTemplate.dataclass);
        baseProperties.put("{lowerdataclass}", StringUtils.uncapitalize(codeJohnnyTemplate.dataclass));
        baseProperties.put("{pluraldataclass}", CodeJohnnyUtils.pluralize(codeJohnnyTemplate.dataclass));
        baseProperties.put("{lowerpluraldataclass}", CodeJohnnyUtils.lowerPluralize(codeJohnnyTemplate.dataclass));
        baseProperties.put("{datatable}", codeJohnnyTemplate.datatable);
        baseProperties.put("{lowerprimarykeycamelcase}", getPrimaryKey(codeJohnnyTemplate.columns, true));
        baseProperties.put("{primarykey}", getPrimaryKey(codeJohnnyTemplate.columns, false));

        for (Map.Entry<String, String> param : baseProperties.entrySet()) {
            content = content.replace(param.getKey(), param.getValue());
        }
        return content;
    }

    public String getPrimaryKey(List<CodeJohnnyColumn> codeJohnnyColumns, boolean getCamelCase) {

        String primaryCamelCase = StringUtils.EMPTY;
        String primaryKey = StringUtils.EMPTY;

        for (CodeJohnnyColumn codeJohnnyColumn : codeJohnnyColumns) {
            if (codeJohnnyColumn.isPrimary) {
                primaryCamelCase = codeJohnnyColumn.columnCamelCase;
                primaryKey = codeJohnnyColumn.columnName;
            }
        }

        String key = getCamelCase ? primaryCamelCase : primaryKey;
        return key;
    }

    public String populateCustomProperties(String content, List<CodeJohnnyProperty> codeJohnnyProperties) {

        for (CodeJohnnyProperty codeJohnnyProperty : codeJohnnyProperties) {
            if (codeJohnnyProperty.type == null) {
                content = content.replace("{" + codeJohnnyProperty.name + "}", codeJohnnyProperty.value);
            }
        }
        return content;
    }

    public String populateGlobalProperties(String content, List<CodeJohnnyGlobalProperty> codeJohnnyGlobalProperties) {

        for (CodeJohnnyGlobalProperty codeJohnnyGlobalProperty : codeJohnnyGlobalProperties) {
            if (codeJohnnyGlobalProperty.type == null) {
                content = content.replace("{" + codeJohnnyGlobalProperty.name + "}", codeJohnnyGlobalProperty.value);
            }
        }
        return content;
    }

    @Override
    public void addBooleanProperties(Map<String, Object> model, List<CodeJohnnyProperty> codeJohnnyProperties) {
        for (CodeJohnnyProperty property : codeJohnnyProperties) {
            if (property.getType() != null) {
                if (property.getType().equals(Boolean.class)) {
                    model.put(property.getName(), Boolean.valueOf(property.getValue()));
                }
            }
        }
    }
    // endregion

    // region Mustache processing

    @Override
    public Mustache getMustache(String content) throws UnsupportedEncodingException{
        Mustache mustache = null;
        try {
            InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8.name()));
            InputStreamReader reader = new InputStreamReader(stream);
            MustacheFactory c = new DefaultMustacheFactory();
            mustache = c.compile(reader, content);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return mustache;
    }

    // endregion

}
