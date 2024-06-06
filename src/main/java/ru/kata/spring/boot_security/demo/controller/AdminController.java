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
    public String showAllUsers(@ModelAttribute("newUser") User user, Principal principal, Model model) {
        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("activeTable", "usersTable");
        return "admin-page";
    }

   /* @GetMapping("/admin/profile")
    public String getAdminProfile(@ModelAttribute("newUser") User user, Principal principal, Model model) {
        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("activeTable", "userProfile");
        return "admin-page";
    }*/

   /* @GetMapping("/admin/addNewUser")
    public String createUserModel(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("activeTable", "addUser");
        return "admin-page";
    }*/

    @PostMapping("/admin")
    public String saveNewUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult,
                              @RequestParam("roleIds") Collection<Long> roleIds,Principal principal, Model model ) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
            model.addAttribute("allRoles", roleService.getAllRoles());
            model.addAttribute("allUsers", userService.getAllUsers());
            model.addAttribute("activeTable", "addUser");
            return "admin-page";
        }
        userService.saveUserWithRole(user, roleIds);
        return "redirect:/admin";
    }

    @PatchMapping("/admin")
    public String edit(@ModelAttribute("editUser") @Valid User user, BindingResult bindingResult,
                       Model model, Principal principal) {

        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        if (bindingResult.hasErrors()) {
            return "admin-page";
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/admin")
    public String delete(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

}
