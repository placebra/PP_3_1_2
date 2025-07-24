package com.placebra.edu.PP_3_1_2.service;
import com.placebra.edu.PP_3_1_2.entity.User;

import java.util.List;

public interface UserService {

    public User findUserByUsername(String username);
    public List<User> findAllUsers();
    public String saveUser(String name, String username, String password, List<String> roles);
    public String removeUserById(int id);
    public void updateUser(int id, String name, String username, String password, List<String> roles);
}
