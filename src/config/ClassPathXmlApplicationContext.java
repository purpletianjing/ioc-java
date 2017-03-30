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
        List list = getConfigFileElementList();

        for (Object eleChild : list) {
            Element element = (Element) eleChild;

            Object object = buildBeans(element);

            String classPath = element.getAttributeValue("class");
            Class bean = Class.forName(classPath);
            java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(bean);

            java.beans.PropertyDescriptor pd[] = info.getPropertyDescriptors();
            invokeGetMethod(element, object, pd);
        }
    }

    private Object buildBeans(Element element) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String name = element.getAttributeValue("id");
        String classPath = element.getAttributeValue("class");
        Object object = Class.forName(classPath).newInstance();
        beans.put(name, object);
        return object;
    }

    private void invokeGetMethod(Element element, Object object, PropertyDescriptor[] pd) throws IllegalAccessException, InvocationTargetException {
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

    private List getConfigFileElementList() throws JDOMException, IOException {
        String configFilePath = "src/config/beans.xml";
        File file = new File(configFilePath);

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(file);

        Element rootElement = document.getRootElement();
        return (List) rootElement.getChildren();
    }

    @Override
    public Object getBean(String beanName) {
        return beans.get(beanName);
    }
}
