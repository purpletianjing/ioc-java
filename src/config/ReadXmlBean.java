package config;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReadXmlBean {
    public List getXmlNodes() throws JDOMException, IOException {
        String configFilePath = "src/config/beans.xml";
        File file = new File(configFilePath);

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(file);

        Element rootElement = document.getRootElement();
        return (List) rootElement.getChildren();
    }
}
