package com.tjsun.util;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassByPackage {
    public List<Class> getClasssFromPackage(String packageName) {
        List<Class> classList = new ArrayList<>();
        URL url = getClass().getClassLoader().getResource(packageName.replace('.', '/'));
        File dir = new File(url.getFile());
        for (File f : dir.listFiles()) {
            String name = f.getName().replaceAll("\\.class", "");
            String className = packageName + "." + name;

            try {
                Class clazz = Class.forName(className);

                if (Modifier.isAbstract(clazz.getModifiers()) || clazz.isInterface()) {
                    continue;
                }
                classList.add(clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return classList;
    }
}
