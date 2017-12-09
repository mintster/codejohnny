package com.codejohnny.templates.java.jangles;

import com.codejohnny.business.CodeJohnnyModule;
import com.codejohnny.containers.CodeJohnnyColumn;
import com.codejohnny.containers.CodeJohnnyTemplate;
import com.codejohnny.core.CodeJohnnyUtils;
import com.codejohnny.db.ICodeJohnnyDb;
import com.codejohnny.templates.TemplateBase;
import com.github.mustachejava.Mustache;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.commons.lang3.StringUtils;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DbRetrieve extends TemplateBase {

    private Boolean doPopulate;

    @Inject
    private ICodeJohnnyDb codeJohnnyDb;

    public DbRetrieve(CodeJohnnyTemplate codeJohnnyTemplate) {
        this.doPopulate = CodeJohnnyUtils.getBooleanProperty(codeJohnnyTemplate, "doPopulate");

        Injector injector = Guice.createInjector(new CodeJohnnyModule());
        this.codeJohnnyDb= injector.getInstance(ICodeJohnnyDb.class);

    }

public String processMustache(String content, CodeJohnnyTemplate codeJohnnyTemplate)
        throws UnsupportedEncodingException, SQLException {
    Mustache m = getMustache(content);
    StringWriter sw = new StringWriter();
    Map<String, Object> model = new HashMap<>();
    model.put("users", codeJohnnyDb.getCodeJohnnyUsers());
    addBooleanProperties(model, codeJohnnyTemplate.getProperties());
    m.execute(sw, model);
    return sw.toString();
}

      public String closeConnections(CodeJohnnyTemplate codeJohnnyTemplate) {

        return "rs.close();\n" +
                "cs.close();\n" +
                "cn.close();";

    }

    /**
     * <P>Generates RecordSet Property Assignment Statements. Typically used in Data Provider base class.</P>
     * <p>
     * <B>Sample Output:</B>
     * <PRE>
     * codeJohnnyColumn.setJavaDatatype(rs.getString("javaDatatype"));
     * codeJohnnyColumn.setDatatypeLength(rs.getInt("datatypeLength"));
     * codeJohnnyColumn.setIsPrimary(rs.getBoolean("isPrimary"));
     * </PRE>
     *
     * @param codeJohnnyTemplate XML file
     * @return String
     */
    public String populateLoop(CodeJohnnyTemplate codeJohnnyTemplate) {

        if (doPopulate) {
            String loopPattern = "{lowerdataclass}.set%s(rs.get%s(\"%s\"));\n";

            for (CodeJohnnyColumn codeJohnnyColumn : codeJohnnyTemplate
                    .getColumns()) {
                loopPopulatedPattern = String.format(loopPattern,
                        StringUtils.capitalize(codeJohnnyColumn.columnCamelCase),
                        StringUtils.capitalize(codeJohnnyColumn.javaDatatype),
                        codeJohnnyColumn.columnName);
                loopBuilder.append(loopPopulatedPattern);
            }

            return loopBuilder.toString().replace("getInteger(","getInt(");
        } else
            return StringUtils.EMPTY;
    }


}
