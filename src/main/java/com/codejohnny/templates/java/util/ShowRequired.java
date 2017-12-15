package com.codejohnny.templates.java.util;

import com.codejohnny.containers.CodeJohnnyColumn;
import com.codejohnny.containers.CodeJohnnyTemplate;
import com.codejohnny.templates.TemplateBase;

import java.text.MessageFormat;

public class ShowRequired extends TemplateBase {

    public ShowRequired(CodeJohnnyTemplate codeJohnnyTemplate) {
    }

    public String showIsRequiredAndComments(CodeJohnnyTemplate codeJohnnyTemplate) {
        String loopPattern = "Field: {0} Required: {1} Field Comments: {2};\n";

        for (CodeJohnnyColumn codeJohnnyColumn : codeJohnnyTemplate.getColumns()) {
            loopPopulatedPattern = MessageFormat.format(loopPattern,
                    codeJohnnyColumn.columnName,
                    codeJohnnyColumn.isRequired,
                    codeJohnnyColumn.columnComment);
            loopBuilder.append(loopPopulatedPattern);
        }

        return loopBuilder.toString();

    }


}
