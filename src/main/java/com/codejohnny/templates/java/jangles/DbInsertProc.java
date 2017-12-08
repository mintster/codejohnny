package com.codejohnny.templates.java.jangles;

import com.codejohnny.containers.CodeJohnnyColumn;
import com.codejohnny.containers.CodeJohnnyTemplate;
import com.codejohnny.core.CodeJohnnyUtils;
import com.codejohnny.templates.TemplateBase;
import org.apache.commons.lang3.StringUtils;

public class DbInsertProc extends TemplateBase {

    private Boolean addId;

    public DbInsertProc(CodeJohnnyTemplate codeJohnnyTemplate) {
        this.addId = CodeJohnnyUtils.getBooleanProperty(codeJohnnyTemplate, "addId");
    }

    /*
        cn.prepareCall("{call p_tweets_insert(?,?,?,?,?,?...)}");
     */
    public String commaLoop(CodeJohnnyTemplate codeJohnnyTemplate) {
        int columns = codeJohnnyTemplate.getColumns().size();
        for (int i = 0; i < columns; i++) {
            if (i == 0) {
                if (!addId)
                    continue;
            }
            loopBuilder.append("?,");
        }
        return StringUtils.chop(loopBuilder.toString());
    }

    /*
        cs.setLong(1, tweet.getId());
        cs.setString(2, tweet.getTweetText());
        cs.setInt(3, tweet.getFavoriteCount());
        ...
     */
    public String procParamLoop(CodeJohnnyTemplate codeJohnnyTemplate) {
        String loopPattern = "cs.set%s(%s, %s.get%s());\n";
        Boolean addId = CodeJohnnyUtils.getBooleanProperty(codeJohnnyTemplate, "addId");
        int i = 1;

        for (CodeJohnnyColumn c : codeJohnnyTemplate.getColumns()) {
            if (c.getIsPrimary()) {
                if (addId) {
                    loopPopulatedPattern = String.format(loopPattern, c.getJavaDatatype(), i, CodeJohnnyUtils.lower(codeJohnnyTemplate.dataclass),
                            StringUtils.capitalize(c.getColumnCamelCase()));
                    loopBuilder.append(loopPopulatedPattern);
                    i++;
                }
            } else {
                loopPopulatedPattern = String.format(loopPattern, c.getJavaDatatype(), i, CodeJohnnyUtils.lower(codeJohnnyTemplate.dataclass),
                        StringUtils.capitalize(c.getColumnCamelCase()));
                loopBuilder.append(loopPopulatedPattern);
                i++;
            }
        }
        loopContent = loopBuilder.toString().replace("Integer(", "Int(");
        return loopContent;
    }
}
