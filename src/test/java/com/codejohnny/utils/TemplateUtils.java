package com.codejohnny.utils;

import com.codejohnny.containers.CodeJohnnyTemplate;
import com.codejohnny.core.CodeJohnnyGlobals;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class TemplateUtils {

    public static CodeJohnnyTemplate getCodeJohnnyTemplate(String filename) {
        CodeJohnnyTemplate codeJohnnyTemplate = null;

        try {
            JAXBContext jc = JAXBContext.newInstance(CodeJohnnyTemplate.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            File xml = new File(CodeJohnnyGlobals.get().templatesPath + filename);
            codeJohnnyTemplate = (CodeJohnnyTemplate) unmarshaller.unmarshal(xml);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return codeJohnnyTemplate;
    }

}
