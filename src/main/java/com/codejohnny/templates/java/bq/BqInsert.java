package com.codejohnny.templates.java.bq;

import com.codejohnny.containers.CodeJohnnyColumn;
import com.codejohnny.containers.CodeJohnnyTemplate;
import com.codejohnny.enums.CjSqlDatatype;
import com.codejohnny.templates.TemplateBase;
import org.apache.commons.lang3.StringUtils;

public class BqInsert extends TemplateBase {

    public BqInsert(CodeJohnnyTemplate codeJohnnyTemplate) {
    }

    /*
        "(" + user.getUsername() + "', " +
        "'" + user.getEmail() + "', " +
     */
    public String dtoLoop(CodeJohnnyTemplate codeJohnnyTemplate) {

        String dto = StringUtils.uncapitalize(codeJohnnyTemplate.dataclass);
        String quotelead = "\"'\"";
        for (CodeJohnnyColumn c : codeJohnnyTemplate.getColumns()) {
            if (!c.isPrimary) {
                String columnName = StringUtils.capitalize(c.columnCamelCase);
                switch (CjSqlDatatype.toSqlDataType(c.sqlDatatype.toUpperCase())) {
                    case DATETIME:
                    case TIMESTAMP:
                    case VARCHAR:
                    case CHAR:
                    case LONGTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                        loopPopulatedPattern = String.format("%s + %s.get%s() + \"',\" +\n", quotelead, dto, columnName);
                        break;
                    case BIGINT:
                    case SMALLINT:
                    case INT:
                    case INT4:
                    case FLOAT:
                    case FLOAT4:
                    case FLOAT8:
                    case DOUBLE:
                    case BIT:
                    case TINYINT:
                    case BOOL:
                        loopPopulatedPattern = String.format("%s.get%s() + \",\" +\n", dto, columnName);
    break;
default:
    break;
    }
}
if (c.getIsLastColumn()) {
    loopPopulatedPattern = loopPopulatedPattern.replace(",\" +", "\" +");
}
loopBuilder.append(loopPopulatedPattern);
}
        return StringUtils.chomp(loopBuilder.toString());
    }

    /*
              // username, first_name, last_name...
     */
    public String fieldLoop(CodeJohnnyTemplate codeJohnnyTemplate) {

        for (CodeJohnnyColumn c : codeJohnnyTemplate.getColumns()) {
            if (!c.isPrimary) {
                loopPopulatedPattern = c.columnName + ", ";
                loopBuilder.append(loopPopulatedPattern);
            }
        }
        return StringUtils.chop(loopBuilder.toString().trim());

    }
}
