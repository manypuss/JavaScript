package ru.kata.spring.boot_security.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserGlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<UserIncorrectData> handleException(
            NoSuchUserException exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
}
