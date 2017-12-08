package com.codejohnny.templates.java.pojo;

import com.codejohnny.containers.CodeJohnnyColumn;
import com.codejohnny.containers.CodeJohnnyTemplate;
import com.codejohnny.core.CodeJohnnyUtils;
import com.codejohnny.templates.TemplateBase;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("Duplicates")
public class Builder extends TemplateBase {

    private Boolean addId;

    public Builder(CodeJohnnyTemplate codeJohnnyTemplate) {
        this.addId = CodeJohnnyUtils.getBooleanProperty(codeJohnnyTemplate, "addId");
    }

    private String paramPattern(String loopPattern, CodeJohnnyColumn column) {
        return String.format(loopPattern, column.javaDatatype, column.columnCamelCase);
    }

    /*
    pattern="Long userId, String username..."
     */
    public String getBuilderParams(CodeJohnnyTemplate codeJohnnyTemplate) {

        String loopPattern = "%s %s, ";

        for (CodeJohnnyColumn codeJohnnyColumn : codeJohnnyTemplate.getColumns()) {
            if (codeJohnnyColumn.getIsPrimary()) {
                if (addId) {
                    loopPopulatedPattern = paramPattern(loopPattern, codeJohnnyColumn);
                    loopBuilder.append(loopPopulatedPattern);
                }
                continue;
            }
            if (codeJohnnyColumn.getIsRequired()) {
                loopPopulatedPattern = paramPattern(loopPattern, codeJohnnyColumn);
                loopBuilder.append(loopPopulatedPattern);
            }
        }
        return StringUtils.chop(loopBuilder.toString().trim());
    }

    private static String varLoopPattern(String loopPattern, CodeJohnnyColumn column) {
        return String.format(loopPattern, column.columnCamelCase);
    }

    /*
    pattern="userId, username..."
     */
    public String getBuilderVarLoop(CodeJohnnyTemplate codeJohnnyTemplate) {

        String loopPattern = "%s, ";

        for (CodeJohnnyColumn codeJohnnyColumn : codeJohnnyTemplate.getColumns()) {
            if (codeJohnnyColumn.getIsPrimary()) {
                if (addId) {
                    loopPopulatedPattern = varLoopPattern(loopPattern, codeJohnnyColumn);
                    loopBuilder.append(loopPopulatedPattern);
                }
                continue;
            }
            if (codeJohnnyColumn.getIsRequired()) {
                loopPopulatedPattern = varLoopPattern(loopPattern, codeJohnnyColumn);
                loopBuilder.append(loopPopulatedPattern);
            }
        }
        return StringUtils.chop(loopBuilder.toString().trim());
    }

    private String builtParamNamesPattern(String loopPattern, CodeJohnnyColumn column) {
        return String.format(loopPattern, column.columnCamelCase, column.columnCamelCase);
    }

    /*
    pattern="built.userId = userid;"
     */
    public String getBuiltParamNames(CodeJohnnyTemplate codeJohnnyTemplate) {

        String loopPattern = "built.%s = %s;\n";

        for (CodeJohnnyColumn codeJohnnyColumn : codeJohnnyTemplate.getColumns()) {
            if (codeJohnnyColumn.getIsPrimary()) {
                if (addId) {
                    loopPopulatedPattern = builtParamNamesPattern(loopPattern, codeJohnnyColumn);
                    loopBuilder.append(loopPopulatedPattern);
                }
                continue;
            }
            if (codeJohnnyColumn.getIsRequired()) {
                loopPopulatedPattern = builtParamNamesPattern(loopPattern, codeJohnnyColumn);
                loopBuilder.append(loopPopulatedPattern);
            }
        }
        return StringUtils.chop(loopBuilder.toString().trim());
    }

    /*
            public Builder retweetScreenName(String retweetScreenName) {
                built.retweetScreenName = retweetScreenName;
                return this;
            }
     */
    public String getBuiltIndiProperties(CodeJohnnyTemplate codeJohnnyTemplate) {

        String loopPattern = "public Builder [0]([1] [0]) {\n" +
                "built.[0] = [0];\n" +
                "return this;\n" +
                "}\n\n";

        for (CodeJohnnyColumn c : codeJohnnyTemplate.getColumns()) {
            if (!c.getIsRequired()) {
                loopPopulatedPattern = loopPattern.replaceAll("\\[0]", CodeJohnnyUtils.lower(c.columnCamelCase));
                loopPopulatedPattern = loopPopulatedPattern.replaceAll("\\[1]", c.javaDatatype);
                loopBuilder.append(loopPopulatedPattern);
            }
        }
        return StringUtils.chop(loopBuilder.toString().trim());
    }

}
