package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import ru.kata.spring.boot_security.demo.exceptions.NoSuchUserException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    @Transactional
    public void createUser(User user) {
        user.setRoles(user.getRoles().stream()
                .map(role -> roleService.getByName(role.getName()))
                .collect(Collectors.toSet()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User user) {
        User updateUser = usersRepository.findById(id).orElseThrow(() -> new NoSuchUserException(
                "There is no employee with ID = '" + id + "' in Database"
        ));
        updateUser.setUsername(user.getUsername());
        updateUser.setUsersurname(user.getUsersurname());
        updateUser.setDepartment(user.getDepartment());
        updateUser.setSalary(user.getSalary());
        if (!user.getPassword().equals(updateUser.getPassword())) {
            updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        updateUser.setRoles(user.getRoles().stream()
                .map(role -> roleService.getByName(role.getName()))
                .collect(Collectors.toSet()));

        usersRepository.save(updateUser);
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new NoSuchUserException(
                "There is no employee with ID = '" + id + "' in Database"
        ));
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        if (usersRepository.findById(id).isPresent()) {
            usersRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}
