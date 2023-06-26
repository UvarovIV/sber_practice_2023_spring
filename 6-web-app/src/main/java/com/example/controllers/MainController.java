package com.example.controllers;

import com.example.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

  private static boolean isAuthorized = false;
  private User user;

  public static boolean isAuthorized() {
    return isAuthorized;
  }

  @GetMapping("/home")
  public String viewProducts(Model model) {
    model.addAttribute("isAuthorized", isAuthorized());
    return "home.html";
  }

  @PostMapping("/home_authorized")
  public String signIn(@RequestParam String login, @RequestParam String password, Model model) {

    if (!login.isBlank() && !password.isBlank()) {
        isAuthorized = true;
        user = new User(login, password);
        model.addAttribute("login", user.login());
        model.addAttribute("password", user.password());
        model.addAttribute("isAuthorized", isAuthorized);
    } else {
        model.addAttribute("isAuthorized", isAuthorized);
    }

    return "home.html";
  }

    @PostMapping("/home")
    public String signOut(Model model) {
        isAuthorized = false;
        model.addAttribute("isAuthorized", isAuthorized);
        return "home.html";
    }
}
