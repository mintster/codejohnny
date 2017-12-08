package com.codejohnny.guice;

import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.internal.ProviderMethodsModule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Created by daveburke on 6/19/17.
 */
public class GuiceJUnit4Runner extends BlockJUnit4ClassRunner {

    public GuiceJUnit4Runner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public Object createTest() throws Exception {
        Object object = super.createTest();
        Module module = ProviderMethodsModule.forObject(object);
        Guice.createInjector(module).injectMembers(object);
        return object;
    }

}