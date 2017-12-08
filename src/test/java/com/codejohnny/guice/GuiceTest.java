package com.codejohnny.guice;

import com.codejohnny.business.CodeJohnnyModule;
import com.codejohnny.db.CodeJohnnyDbImpl;
import com.codejohnny.db.ICodeJohnnyDb;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by daveburke on 6/19/17.
 */
@RunWith(JUnit4.class)
public class GuiceTest {

    @Inject
    private ICodeJohnnyDb iCodeJohnnyDb;

    @Before
    public void setup() {
        Injector injector = Guice.createInjector(new CodeJohnnyModule());
        injector.injectMembers(this);
    }

    @Test
    public void apiInjectionTest() {
        assertThat(iCodeJohnnyDb, is(instanceOf(CodeJohnnyDbImpl.class)));
    }

}
