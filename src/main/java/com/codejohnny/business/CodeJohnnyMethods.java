package com.codejohnny.business;

import com.codejohnny.containers.CodeJohnnyMethod;
import com.codejohnny.containers.CodeJohnnyTemplate;
import com.codejohnny.core.CodeJohnnyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CodeJohnnyMethods {

    private static final Logger logger = LoggerFactory.getLogger(CodeJohnnyMethods.class);
    private static final String MUSTACHE_METHOD = "processMustache";

    @SuppressWarnings("ConstantConditions")
	public String populateMethods(String sourceContent, CodeJohnnyTemplate codeJohnnyTemplate) {

		Object result = null;
		Class<?> c = null;
		Class[] consParams = {CodeJohnnyTemplate.class};
		Constructor cons = null;
		Object[] consValues = {codeJohnnyTemplate};

		for (CodeJohnnyMethod codeJohnnyMethod : codeJohnnyTemplate.getMethods()) {
            if (CodeJohnnyUtils.isInTestingMode()) {
                try {
                    c = Class.forName(codeJohnnyMethod.type);
                } catch (ClassNotFoundException e) {
                    printNotFound(codeJohnnyMethod.name);
                }
                try {
                    Object t = null;
                    try {
                        t = c.newInstance();
                    } catch (InstantiationException e) {
                        printNotFound(codeJohnnyMethod.name);
                    }
                    Method method = c.getDeclaredMethod(codeJohnnyMethod.method, CodeJohnnyTemplate.class);
                    result = method.invoke(t, codeJohnnyTemplate);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    printNotFound(codeJohnnyMethod.name);
                }
                sourceContent = sourceContent.replace("{{~" + codeJohnnyMethod.name + "}}", (String) result);
            } else {
                try {
                    c = Class.forName(codeJohnnyMethod.type);
				cons = c .getConstructor(consParams);
                } catch (ClassNotFoundException | NoSuchMethodException e) {
                    logger.error(e.getMessage());
                    printNotFound(codeJohnnyMethod.name);
                }
                try {
                    Object t = null;
                    try {
					t = cons.newInstance(consValues);
                    } catch (InstantiationException e) {
                        printNotFound(codeJohnnyMethod.name);
                    }
                    Method method = c.getDeclaredMethod(codeJohnnyMethod.method, CodeJohnnyTemplate.class);
                    result = method.invoke(t, codeJohnnyTemplate);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    printNotFound(codeJohnnyMethod.name);
                }
                sourceContent = sourceContent.replace("{{~" + codeJohnnyMethod.name + "}}", (String) result);
            }
        }
		return sourceContent;
	}

    @SuppressWarnings("ConstantConditions")
    public String processMustacheTags(String sourceContent, CodeJohnnyTemplate codeJohnnyTemplate) {

        Object result = null;
        Class<?> c = null;
        try {
            c = Class.forName(codeJohnnyTemplate.getCodeJohnnyMustache().getType());
        } catch (ClassNotFoundException e) {
            printNotFound(codeJohnnyTemplate.getCodeJohnnyMustache().getType());
        }
        Class[] consParams = {CodeJohnnyTemplate.class};
        Constructor cons = null;
        Object[] consValues = {codeJohnnyTemplate};

            if (CodeJohnnyUtils.isInTestingMode()) {
                try {
                    c = Class.forName(codeJohnnyTemplate.getCodeJohnnyMustache().getType());
                } catch (ClassNotFoundException e) {
                    printNotFound(MUSTACHE_METHOD);
                }
                try {
                    Object t = null;
                    try {
                        t = c.newInstance();
                    } catch (InstantiationException e) {
                        printNotFound(MUSTACHE_METHOD);
                    }
                    Method method = c.getDeclaredMethod(MUSTACHE_METHOD, String.class, CodeJohnnyTemplate.class);
                    sourceContent = (String) method.invoke(t, sourceContent, codeJohnnyTemplate);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    printNotFound(MUSTACHE_METHOD);
                }
            } else {
                try {
                    c = Class.forName(codeJohnnyTemplate.getCodeJohnnyMustache().getType());
                    cons = c .getConstructor(consParams);
                } catch (ClassNotFoundException | NoSuchMethodException e) {
                    logger.error(e.getMessage());
                    printNotFound(MUSTACHE_METHOD);
                }
                try {
                    Object t = null;
                    try {
                        t = cons.newInstance(consValues);
                    } catch (InstantiationException e) {
                        printNotFound(MUSTACHE_METHOD);
                    }
                    Method method = c.getDeclaredMethod(MUSTACHE_METHOD,  String.class, CodeJohnnyTemplate.class);
                    sourceContent = (String) method.invoke(t, sourceContent, codeJohnnyTemplate);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    printNotFound(MUSTACHE_METHOD);
                }
            }
        return sourceContent;
    }

    private void printNotFound(String method) {
                System.out.println("Method [" + method.toUpperCase() + "] could not be invoked.");
        }

}
