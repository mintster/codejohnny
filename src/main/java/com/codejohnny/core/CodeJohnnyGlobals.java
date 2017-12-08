package com.codejohnny.core;

import java.io.File;

public class CodeJohnnyGlobals implements java.io.Serializable {

    private static final long serialVersionUID = -5262833103399133397L;

    public String connectionsFile;
    public String globalPropertiesFile;
    public String templatesPath;
    public String configPath;

    public static CodeJohnnyGlobals get() {

        CodeJohnnyGlobals globals = (CodeJohnnyGlobals) CodeJohnnyCache.getInstance().get("CodeJohnnyGlobals");
        if (globals == null) {
            globals = new CodeJohnnyGlobals();
            CodeJohnnyCache.getInstance().put("CodeJohnnyGlobals", globals);
        }
        return globals;
    }

    public CodeJohnnyGlobals() {
        String home = CodeJohnnyConfiguration.get().codejohnnyDirectory;

        if (home.equals("classpath:/")) {
            String projectRoot = System.getProperty("user.dir");
            File resourcesDirectory = new File("src/main/resources");
            String resources = resourcesDirectory.getAbsolutePath();

            this.templatesPath = projectRoot + "/templates/";
            this.configPath = resources + "/";
            this.connectionsFile = configPath + "connections.xml";
            this.globalPropertiesFile = configPath + "globalproperties.xml";
        }
        else {
            this.templatesPath = home + "templates/";
            this.configPath = home + "config/";
            this.connectionsFile = configPath + "connections.xml";
            this.globalPropertiesFile = configPath + "globalproperties.xml";
        }
    }

}
