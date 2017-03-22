package com.tjsun.dao;

import com.tjsun.model.User;

public interface UserDAO {
    void save(User user);

    void delete();
}
