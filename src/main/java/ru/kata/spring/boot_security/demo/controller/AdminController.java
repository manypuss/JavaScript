package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.utils.NoSuchUserException;
import ru.kata.spring.boot_security.demo.utils.UserIncorrectData;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("api/")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public List<User> showAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/admin/user/{id}")
    public User showAllUsers(@PathVariable ("id") Long id) {
        return userService.getUserById(id);
    }



    @PostMapping("/admin")
    public String saveNewUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult
            ,Principal principal, Model model ) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
            model.addAttribute("allRoles", roleService.getAllRoles());
            model.addAttribute("allUsers", userService.getAllUsers());
            model.addAttribute("activeTable", "addUser");
            return "admin-page";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/admin")
    public String edit(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                              Model model, Principal principal) {

        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        if (bindingResult.hasErrors()) {
            return "admin-page";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin")
    public String delete(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @ExceptionHandler
    private ResponseEntity<UserIncorrectData> handleException(
            NoSuchUserException exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
}
