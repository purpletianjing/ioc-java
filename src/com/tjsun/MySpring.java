package com.tjsun;

import com.tjsun.util.ClassByPackage;
import config.Autowired;
import config.Resource;
import config.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySpring {
    private final static String packageName = "com.tjsun";

    private static Map<String, Object> beanMap = new HashMap<String, Object>();

    static {
        try {
            packageToScan();
        } catch (InstantiationException error) {
            error.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }

    public static Object getBean(String beanName) {
        return beanMap.get(beanName);
    }

    private static void packageToScan() throws IllegalAccessException, InstantiationException, IntrospectionException {
        ClassByPackage classByPackage = new ClassByPackage();
        List<Class> classList = classByPackage.getClasssFromPackage(packageName);
        for (Class cls : classList) {
            if (cls.isAnnotationPresent(Autowired.class)) {
                java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(cls);
                String className = info.getBeanDescriptor().getName();
                beanMap.put(className, cls.newInstance());
            }
        }

        for (Class cls : classList) {
            if (cls.isAnnotationPresent(Service.class)) {
                String clsName = cls.getName();
                clsName = clsName.substring(clsName.lastIndexOf(".") + 1, clsName.length());
                beanMap.put(clsName.substring(0, 1).toLowerCase(), cls.newInstance());

                Field[] field = cls.getDeclaredFields();
                for (Field f : field) {
                    if (f.isAnnotationPresent(Resource.class)) {
                        f.setAccessible(true);
                        java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(cls);
                        f.set(beanMap.get(info.getBeanDescriptor().getName()), beanMap.get(f.getName()));
                    }
                }
            }
        }
    }
}
