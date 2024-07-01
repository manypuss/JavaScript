package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/api")
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
    public User showUser(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }


    @PostMapping("/admin")
    public User saveNewUser(@RequestBody @Valid User user) {

        userService.saveUser(user);
        return user;
    }

    @PutMapping("/admin")
    public User edit(@RequestBody @Valid User user) {

        userService.saveUser(user);
        return user;
    }

    @DeleteMapping("/admin/user/{id}")
    public String delete(@PathVariable("id") Long id) {

        if (userService.getUserById(id) != null) {
            userService.deleteUserById(id);
        }
        return "User with ID = " + id + " was deleted";
    }

    @GetMapping("/admin/roles")
    public Collection<Role> getAllRoles() {
        return roleService.getAllRoles();
    }


}
