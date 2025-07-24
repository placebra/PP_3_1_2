package com.placebra.edu.PP_3_1_2.dao;

import com.placebra.edu.PP_3_1_2.entity.User;

import java.util.List;

public interface UserDao {


    public User findUserByUsername(String username);
    public List<User> findAllUsers();
    public void saveUser(User user);
    public void removeUserById(int id);
    public User findUserById(int id);
}
