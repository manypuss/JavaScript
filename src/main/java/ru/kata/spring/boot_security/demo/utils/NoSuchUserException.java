package ru.kata.spring.boot_security.demo.utils;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String message) {
        super(message);
    }
}
