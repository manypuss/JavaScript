package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("/")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(@ModelAttribute("user") User user, Principal principal, Model model) {
        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("activeTable", "usersTable");
        return "admin-page";
    }

    @GetMapping("/admin/profile")
    public String getAdminProfile(@ModelAttribute("user") User user, Principal principal, Model model) {
        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("activeTable", "userProfile");
        return "admin-page";
    }

    @GetMapping("/admin/addNewUser")
    public String createUserModel(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "user-info";
    }

    @PostMapping("/admin/saveNewUser")
    public String saveNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                              @RequestParam("roleIds") Collection<Long> roleIds, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "admin-page";
        }
        userService.saveUserWithRole(user, roleIds);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "user-info";
    }

    @PostMapping("/admin/delete")
    public String delete(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

}
