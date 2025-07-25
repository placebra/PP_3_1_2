package com.placebra.edu.PP_3_1_2.controller;

import com.placebra.edu.PP_3_1_2.dto.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    @GetMapping("")
    public String login(@AuthenticationPrincipal CustomUserDetails userDetails) {

        //Не пускать авторизованного юзера на логин-форму
        if (userDetails != null) {
            List<String> roles = userDetails.getRoles().stream().map(e -> e.getName()).toList();

            if (roles.contains("ROLE_ADMIN")) {
                return "redirect:/admin";
            } else if (roles.contains("ROLE_USER")) {
                return "redirect:/user";
            }
        }

        return "login";
    }
}
