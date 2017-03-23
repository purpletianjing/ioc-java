package config;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory {
    Map beans = new HashMap<String, Object>();

    public ClassPathXmlApplicationContext() throws JDOMException, IOException, ClassNotFoundException,
        IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IntrospectionException {
        File file = new File("src/config/beans.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(file);
        Element rootElement = document.getRootElement();


        List list = rootElement.getChildren();

        for (Object eleChild : list) {
            Element element = (Element) eleChild;
            String name = element.getAttributeValue("id");
            String classPath = element.getAttributeValue("class");
            Object object = Class.forName(classPath).newInstance();

            Class bean = Class.forName(classPath);
            java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(bean);
            java.beans.PropertyDescriptor pd[] = info.getPropertyDescriptors();


            beans.put(name, object);

            for (Element property : element.getChildren("property")) {
                String beanId = property.getAttributeValue("bean");
                Object beanObj = getBean(beanId);

                for (PropertyDescriptor propertyDescriptor : pd) {
                    Method method = propertyDescriptor.getWriteMethod();
                    if (method != null) {
                        method.invoke(object, beanObj);
                    }
                }

            }
        }
    }

    @Override
    public Object getBean(String beanName) {
        return beans.get(beanName);
    }
}
