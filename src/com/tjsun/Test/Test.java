package com.tjsun.Test;

import com.tjsun.model.User;
import com.tjsun.service.UserService;
import config.BeanFactory;
import config.ClassPathXmlApplicationContext;
import org.jdom2.JDOMException;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, IOException, InstantiationException, JDOMException, NoSuchMethodException, ClassNotFoundException, IntrospectionException {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext();
        UserService userService = (UserService) beanFactory.getBean("userService");
        User user = (User) beanFactory.getBean("user");
        user.setName("tjsun");
        userService.saveUser(user);
    }
}
