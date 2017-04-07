package config;

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
        InvokeField invokeField = new InvokeField();
        invokeField.invokeField(list, beanMap);
    }

    @Override
    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }
}
