package com.codejohnny.templates;

import com.codejohnny.business.*;
import com.codejohnny.db.ICodeJohnnyDb;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TemplateTestBase {

    protected String loopPopulatedPattern = StringUtils.EMPTY;
    protected String loopContent = StringUtils.EMPTY;
    protected StringBuilder loopBuilder = new StringBuilder();

    @Inject
    ICodeJohnnyTemplates codeJohnnyTemplates;

    @Inject
    private CodeJohnnyColumns codeJohnnyColumns;
    @Inject
    private CodeJohnnyMethods codeJohnnyMethods;
    @Inject
    private ICodeJohnnyConnections codeJohnnyConnections;
    @Inject
    private CodeJohnnyGlobalProperties codeJohnnyGlobalProperties;
    @Inject
    protected ICodeJohnnyDb codeJohnnyDb;

    @Before
    public void setup() {
        Injector injector = Guice.createInjector(new CodeJohnnyModule());
        injector.injectMembers(this);
        this.codeJohnnyColumns = injector.getInstance(CodeJohnnyColumns.class);
        this.codeJohnnyTemplates = new CodeJohnnyTemplates(codeJohnnyGlobalProperties, codeJohnnyColumns, codeJohnnyMethods, codeJohnnyConnections);
        this.codeJohnnyDb = injector.getInstance(ICodeJohnnyDb.class);
    }

    @Test
    public void msgGoAway() {
        Assert.assertEquals(1, 1);
    }
}
