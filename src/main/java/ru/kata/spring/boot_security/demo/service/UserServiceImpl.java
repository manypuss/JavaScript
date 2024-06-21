package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import ru.kata.spring.boot_security.demo.utils.NoSuchUserException;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
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
        if (usersRepository.findById(id).isPresent()){
            usersRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

}
