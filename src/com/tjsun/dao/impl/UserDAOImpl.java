package com.tjsun.dao.impl;

import com.tjsun.dao.UserDAO;
import com.tjsun.model.User;

public class UserDAOImpl implements UserDAO {
    @Override
    public void save(User user) {
        System.out.println(user.toString());
    }

}
