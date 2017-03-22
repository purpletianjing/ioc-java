package com.tjsun.model;

public class User {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(name);
        sb.append(password);
        return sb.toString();
//        return "User{" +
//            "name='" + name + '\'' +
//            ", password='" + password + '\'' +
//            '}';
    }
}
