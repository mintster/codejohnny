package com.codejohnny.templates.java.util;

import com.codejohnny.containers.CodeJohnnyTemplate;
import com.codejohnny.enums.CjSqlDatatype;
import com.codejohnny.templates.TemplateBase;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public class ColTypeSwitch extends TemplateBase {

    public ColTypeSwitch(CodeJohnnyTemplate codeJohnnyTemplate) {
    }

    public String colTypeLoop(CodeJohnnyTemplate codeJohnnyTemplate) {
        String loopPattern = "case {0}:\n";
        for (CjSqlDatatype dt : CjSqlDatatype.values()) {
            if (dt != CjSqlDatatype.NA) {
                loopPopulatedPattern = MessageFormat.format(loopPattern, dt.name());
                loopBuilder.append(loopPopulatedPattern);
            }
            if (dt == CjSqlDatatype.INT4 || dt == CjSqlDatatype.VARCHAR) {
                loopBuilder.append("\tloopPopulatedPattern = String.format(\"%s...\" +\\n\", columnName);\n\tbreak;\n");
            }
        }
        return StringUtils.chomp(loopBuilder.toString());
    }
}
