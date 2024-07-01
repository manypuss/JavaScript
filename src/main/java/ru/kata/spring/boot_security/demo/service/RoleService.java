package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Collection;

public interface RoleService {
    Collection<Role> getAllRoles();

    Role getByName(String name);

}
