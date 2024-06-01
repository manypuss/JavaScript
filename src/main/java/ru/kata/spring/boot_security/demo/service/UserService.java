package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void saveUserWithRole(User user, Collection<Long> roleIds);

    User getUserById(Long id);

    void deleteUserById(Long id);

    User getUserByUsername(String username);
}
