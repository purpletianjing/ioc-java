package config;

import org.jdom2.Element;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class InvokeField {
    public void invokeField(List list, Map beanMap) throws IllegalAccessException, InstantiationException, ClassNotFoundException, IntrospectionException, InvocationTargetException {
        for (Object eleChild : list) {
            Element element = (Element) eleChild;

            BuildBeanMap buildBeanMap = new BuildBeanMap();
            Object object = buildBeanMap.buildBean(element, beanMap);

            String classPath = element.getAttributeValue("class");

            Class bean = Class.forName(classPath);
            java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(bean);
            java.beans.PropertyDescriptor pd[] = info.getPropertyDescriptors();
            InvokeMethod invokeMethod = new InvokeMethod();
            invokeMethod.invokeSetMethod(element, pd, object, beanMap);
        }
    }
}
