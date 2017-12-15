package com.codejohnny.templates.sql;

import com.codejohnny.containers.CodeJohnnyColumn;
import com.codejohnny.containers.CodeJohnnyTemplate;
import com.codejohnny.core.CodeJohnnyUtils;
import com.codejohnny.enums.CjSqlDatatype;
import com.codejohnny.templates.TemplateBase;
import org.apache.commons.lang3.StringUtils;

public class SqlInsertProc extends TemplateBase {

    private Boolean addId;
    private Boolean outId;
    private String outIdName;

    public SqlInsertProc(CodeJohnnyTemplate codeJohnnyTemplate) {
        this.addId = CodeJohnnyUtils.getBooleanProperty(codeJohnnyTemplate, "addId");
        this.outId = CodeJohnnyUtils.getBooleanProperty(codeJohnnyTemplate, "outId");
        this.outIdName = CodeJohnnyUtils.getStringProperty(codeJohnnyTemplate, "outIdName");
    }

    /*
     CREATE PROCEDURE p_tweets_insert(
         IN p_id BIGINT(20),
         IN p_tweet_text VARCHAR(1000),
         IN p_favorite_count INT...
     */
    public String paramLoop(CodeJohnnyTemplate codeJohnnyTemplate) {

        String loopPattern = "IN p_%s %s,\n";
        String loopPatternType = "IN p_%s %s(%s),\n";

        for (CodeJohnnyColumn c : codeJohnnyTemplate.getColumns()) {
            if (c.getIsPrimary()) {
                if (addId) {
                    loopPopulatedPattern = loopPopulatedPattern = String.format(loopPatternType,
                            c.getColumnName(),
                            c.getSqlDatatype().toUpperCase(),
                            c.getDatatypeLength());
                    loopBuilder.append(loopPopulatedPattern);

                }
                continue;
            }
            if (c.getSqlDatatype().toUpperCase().equals(CjSqlDatatype.VARCHAR.name())) {
                loopPopulatedPattern = String.format(loopPatternType,
                        c.getColumnName(),
                        c.getSqlDatatype().toUpperCase(),
                        c.getDatatypeLength());
            } else {
                loopPopulatedPattern = String.format(loopPattern,
                        c.getColumnName(),
                        c.getSqlDatatype().toUpperCase());
            }
            if (c.getIsLastColumn() && !outId) {
                loopPopulatedPattern = StringUtils.chop(loopPopulatedPattern.trim());
            }
            if (c.getIsLastColumn() && outId) {
                loopPopulatedPattern += "OUT out_" + outIdName + " BIGINT";
            }
            loopBuilder.append(loopPopulatedPattern);
        }

        return loopBuilder.toString();

    }

    /*
    INSERT INTO tweets (
         id,
        tweet_text,
        user_screen_name...
     */
    public String insertLoop(CodeJohnnyTemplate codeJohnnyTemplate) {
        String loopPattern = "%s,\n";

        for (CodeJohnnyColumn c : codeJohnnyTemplate.getColumns()) {
            if (c.getIsPrimary()) {
                if (addId) {
                    loopPopulatedPattern = String.format(loopPattern,
                            c.getColumnName());
                    loopBuilder.append(loopPopulatedPattern);
                }
            } else {
                loopPopulatedPattern = String.format(loopPattern,
                        c.getColumnName());
                if (c.getIsLastColumn()) {
                    loopPopulatedPattern = StringUtils.chop(loopPopulatedPattern.trim());
                }
                loopBuilder.append(loopPopulatedPattern);
            }
        }

        return loopBuilder.toString();

    }

    /*
    VALUES (
    p_id,
    p_tweet_text,
    p_favorite_count...
     */
    public String valueLoop(CodeJohnnyTemplate codeJohnnyTemplate) {

        String loopPattern = "p_%s,\n";

        for (CodeJohnnyColumn c : codeJohnnyTemplate.getColumns()) {
            if (c.getIsPrimary()) {
                if (addId) {
                    loopPopulatedPattern = String.format(loopPattern,
                            c.getColumnName());
                    loopBuilder.append(loopPopulatedPattern);
                }
                continue;
            } else {
                loopPopulatedPattern = String.format(loopPattern,
                        c.getColumnName());
            }
            if (c.getIsLastColumn()) {
                loopPopulatedPattern = StringUtils.chop(loopPopulatedPattern.trim());
            }
            loopBuilder.append(loopPopulatedPattern);
        }

        return loopBuilder.toString();

    }

}
