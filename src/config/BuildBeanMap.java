package config;

import org.jdom2.Element;

import java.util.Map;

public class BuildBeanMap {
    public Object buildBean(Element element, Map beans) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String name = element.getAttributeValue("id");
        String classPath = element.getAttributeValue("class");
        Object object = Class.forName(classPath).newInstance();
        beans.put(name, object);
        return object;
    }
}
