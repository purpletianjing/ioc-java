package config;

import org.jdom2.Element;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class InvokeMethod {
    public void invokeSetMethod(Element element, PropertyDescriptor[] pd, Object object, Map beans) throws InvocationTargetException, IllegalAccessException {
        for (Element property : element.getChildren("property")) {
            String beanId = property.getAttributeValue("bean");
            Object beanObj = beans.get(beanId);

            for (PropertyDescriptor propertyDescriptor : pd) {
                Method method = propertyDescriptor.getWriteMethod();
                if (method != null) {
                    method.invoke(object, beanObj);
                }
            }

        }
    }
}
