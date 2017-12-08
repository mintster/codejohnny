package com.codejohnny.templates;

import com.codejohnny.containers.CodeJohnnyTemplate;
import com.codejohnny.enums.CjSqlDatatype;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.MessageFormat;

import static org.junit.Assert.assertTrue;

@SuppressWarnings("Duplicates")
@RunWith(JUnit4.class)
public class DataTypeLoopTest extends TemplateTestBase {

    @Test
    public void propertyByTypeTest() {
        String template = "test/datatypeloopIT.xml";
        String output = codeJohnnyTemplates.generateSourceFromFile(template);
        assertTrue(output.contains("case SMALLINT:"));
    }

    @Test
    public void iterateOverEnumTest() {
        Boolean containsSMALLINT =false;
        for (CjSqlDatatype dt : CjSqlDatatype.values()) {
            if (dt.name().equals("SMALLINT")) {
                containsSMALLINT = true;
            }
        }
        assertTrue(containsSMALLINT);
    }

    /*
        case DATETIME:
        case TIMESTAMP:
        case VARCHAR:
     */
    public String testLoop(CodeJohnnyTemplate codeJohnnyTemplate) {
        String loopPattern = "case {0}:\n";
        String breakPattern = "break;\n" +
                "default:\n" +
                "    break;" +
                " }\n" +
                "}\n" +
                "if (c.getIsLastColumn()) {\n" +
                "    loopPopulatedPattern = loopPopulatedPattern.replace(\",\\\" +\", \"\\\" +\");\n" +
                "}\n" +
                "loopBuilder.append(loopPopulatedPattern);\n" +
                "}\n\n}";
        for (CjSqlDatatype dt : CjSqlDatatype.values()) {
            if (dt != CjSqlDatatype.NA) {
                loopPopulatedPattern = MessageFormat.format(loopPattern, dt.name());
                loopBuilder.append(loopPopulatedPattern);
            }
        }
        return StringUtils.chomp(loopBuilder.toString());
    }

}
