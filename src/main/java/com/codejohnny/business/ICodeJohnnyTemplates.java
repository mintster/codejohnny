package com.codejohnny.business;

import com.codejohnny.containers.CodeJohnnyProperty;
import com.github.mustachejava.Mustache;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface ICodeJohnnyTemplates {
    String generateSourceFromStream(String stream);
    String generateSourceFromFile(String filename);

    void addBooleanProperties(Map<String, Object> model, List<CodeJohnnyProperty> codeJohnnyProperties);

    Mustache getMustache(String content) throws UnsupportedEncodingException;
}
