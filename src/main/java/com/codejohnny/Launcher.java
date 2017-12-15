package com.codejohnny;

import com.codejohnny.business.CodeJohnnyColumns;
import com.codejohnny.business.CodeJohnnyModule;
import com.codejohnny.business.CodeJohnnyTemplates;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Launcher {

    /**
     * TEMPLATE variable: relative to /resources/templates folder.
     * If "test.xml" in /templates/java root, TEMPLATE would be "java/test.xml"
     */
    private static String template = Template.SQL_INSERT_PROC;
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CodeJohnnyModule());
        CodeJohnnyColumns codeJohnnyColumns = injector.getInstance(CodeJohnnyColumns.class);
        CodeJohnnyTemplates codeJohnnyTemplates = injector.getInstance(CodeJohnnyTemplates.class);

        if (args.length > 0) {
            template = args[0];
        }

        System.out.println(codeJohnnyTemplates.generateSourceFromFile(template));
    }

    private static class Template {
        private static final String SQL_INSERT_PROC = "sql/sqlinsertproc.xml";
        private static final String DB_INSERT_PROC = "java/jangles/dbinsertproc.xml";
        private static final String DB_RETRIEVE = "java/jangles/dbretrieve.xml";
        private static final String DB_EXECUTE = "java/jangles/dbexecute.xml";
        private static final String BQ_INSERT = "java/bq/bqinsert.xml";
        private static final String POJO_BUILDER = "java/pojo/builder.xml";
        private static final String POJO  = "java/pojo/pojo.xml";
        private static final String COLTYPE_SWITCH  = "java/util/coltypeswitch.xml";
        private static final String SHOW_REQUIRED  = "java/util/showrequired.xml";
    }
}
