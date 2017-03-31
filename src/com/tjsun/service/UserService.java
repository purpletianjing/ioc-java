package com.tjsun.service;

import com.tjsun.dao.UserDAO;
import com.tjsun.model.User;
import config.Autowired;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class UserService {

    @Autowired
    private UserDAO userDAO;

    public UserService() throws IllegalAccessException, InvocationTargetException, IOException, InstantiationException, JDOMException, NoSuchMethodException, ClassNotFoundException {
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void saveUser(User user) {
        userDAO.save(user);
    }
}
