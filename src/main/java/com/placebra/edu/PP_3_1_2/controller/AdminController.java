package com.placebra.edu.PP_3_1_2.controller;

import com.placebra.edu.PP_3_1_2.entity.Role;
import com.placebra.edu.PP_3_1_2.entity.User;
import com.placebra.edu.PP_3_1_2.service.RoleService;
import com.placebra.edu.PP_3_1_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String homeAdminPage(Model model) {
        model.addAttribute("allUsers", userService.findAllUsers());
        return "admin_page";
    }

    @PostMapping("/save")
    public String addNewUser(@RequestParam String name,
                             @RequestParam String username,
                             @RequestParam(required = false) String password,
                             @RequestParam List<String> roles,
                             RedirectAttributes redirectAttributes) {


        String message = userService.saveUser(name, username, password, roles);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin";
    }

    @PostMapping("/remove")
    public String removeUser(@RequestParam int id, RedirectAttributes redirectAttributes) {

        String message = userService.removeUserById(id);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam int id,
                           @RequestParam String name,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam List<String> roles) {

        userService.updateUser(id, name, username, password, roles);

        return "redirect:/admin";
    }
}
