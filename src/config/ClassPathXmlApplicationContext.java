package config;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory {
    Map beanMap = new HashMap<String, Object>();

    public ClassPathXmlApplicationContext() throws JDOMException, IOException, ClassNotFoundException,
        IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IntrospectionException {
        ReadXmlBean readXmlBean = new ReadXmlBean();
        List list = readXmlBean.getXmlNodes();

        for (Object eleChild : list) {
            Element element = (Element) eleChild;
            BuildBeans buildBeans = new BuildBeans();
            Object object = buildBeans.buildBean(element, beanMap);

            String classPath = element.getAttributeValue("class");

            Class bean = Class.forName(classPath);
            java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(bean);
            java.beans.PropertyDescriptor pd[] = info.getPropertyDescriptors();
            InvokeMethod invokeMethod = new InvokeMethod();
            invokeMethod.invokeSetMethod(element, pd, object, beanMap);
        }
    }

    @Override
    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }
}
