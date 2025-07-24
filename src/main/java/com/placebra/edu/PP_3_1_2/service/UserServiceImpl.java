package com.placebra.edu.PP_3_1_2.service;

import com.placebra.edu.PP_3_1_2.dao.RoleDao;
import com.placebra.edu.PP_3_1_2.dao.UserDao;
import com.placebra.edu.PP_3_1_2.entity.Role;
import com.placebra.edu.PP_3_1_2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;

    private PasswordEncoder passwordEncoder;


    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Transactional
    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    @Transactional
    @Override
    public String saveUser(String name, String username, String password, List<String> roles) {

        if (userDao.findUserByUsername(username) != null) {
            return "Пользователь с таким Username уже создан";
        }

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        List<Role> roleList = roles.stream()
                .map(role -> {
                    if ("USER".equals(role)) {
                        return roleDao.getUserRole();
                    } else if ("ADMIN".equals(role)) {
                        return roleDao.getAdminRole();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        user.setRoles(roleList);
        userDao.saveUser(user);

        return "Пользователь успешно сохранен";
    }

    @Transactional
    @Override
    public String removeUserById(int id) {
        userDao.removeUserById(id);
        return "Пользователь успешно удален";
    }

    @Transactional
    @Override
    public void updateUser(int id, String name, String username, String password, List<String> roles) {

        User user = userDao.findUserById(id);
        user.setName(name);
        user.setUsername(username);

        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
        }

        List<Role> newRoles = roles.stream()
                .map(role -> {
                    if (role.equals("ADMIN")) {
                        return roleDao.getAdminRole();
                    } else if (role.equals("USER")) {
                        return roleDao.getUserRole();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        user.setRoles(newRoles);
    }
}
