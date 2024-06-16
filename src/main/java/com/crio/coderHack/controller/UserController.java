package com.crio.coderHack.controller;

import java.util.List;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crio.coderHack.dto.User;
import com.crio.coderHack.exceptions.UserNotFoundException;
import com.crio.coderHack.exchanges.RegisterUserRequest;
import com.crio.coderHack.services.IUserService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Validated
@RestController
public class UserController {
    public static final String USER_API_ENDPOINT = "/users";

    @Autowired
    private IUserService userService;

    // Handles GET requests to retrieve all users.
    @GetMapping(USER_API_ENDPOINT)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    // Handles GET requests to retrieve a specific user by their userID.
    @GetMapping(USER_API_ENDPOINT + "/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user;

        try {
            user = userService.getUserById(userId);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);
    }

    // Handles POST requests to register a new user.
    @PostMapping(USER_API_ENDPOINT)
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        String username = registerUserRequest.getUsername();

        User user = userService.addUser(username);

        return ResponseEntity.ok().body(user);
    }

    // Handles PUT requests to update score of a specific user by their userID.
    @PutMapping(USER_API_ENDPOINT + "/{userId}")
    public ResponseEntity<User> updateScore(@PathVariable String userId,
            @Range(min = 0, max = 100) @RequestParam int score) {
        User user;

        try {
            user = userService.updateScore(userId, score);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);
    }

    // Handles DELETE requests to deregister a specific user by their userId.
    @DeleteMapping(USER_API_ENDPOINT + "/{userId}")
    public ResponseEntity<Void> deregisterUser(@PathVariable String userId) {

        try {
            userService.deleteUser(userId);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    // Handles ConstraintViolationException thrown during validation.
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body("Bad Request");
    }

}
