package com.codejohnny.templates;

import com.codejohnny.containers.CodeJohnnyProperty;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public abstract class TemplateBase {

    protected String loopPopulatedPattern = StringUtils.EMPTY;
    protected String loopContent = StringUtils.EMPTY;
    protected StringBuilder loopBuilder = new StringBuilder();

    protected Mustache getMustache(String content) throws UnsupportedEncodingException {
        InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8.name()));
        InputStreamReader reader = new InputStreamReader(stream);
        MustacheFactory c = new DefaultMustacheFactory();
        return c.compile(reader, content);
    }

    protected void addBooleanProperties(Map<String, Object> model, List<CodeJohnnyProperty> properties) {
        for (CodeJohnnyProperty property : properties) {
            if (property.getType() != null) {
                if (property.getType().equals(Boolean.class)) {
                    model.put(property.getName(), Boolean.valueOf(property.getValue()));
                }
            }
        }
    }
}
