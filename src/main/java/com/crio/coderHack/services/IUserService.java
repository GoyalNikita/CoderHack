package com.crio.coderHack.services;

import java.util.List;

import com.crio.coderHack.dto.User;
import com.crio.coderHack.exceptions.UserNotFoundException;

public interface IUserService {
    User addUser(String username);

    User updateScore(String userId, int score) throws UserNotFoundException;

    User getUserById(String userId) throws UserNotFoundException;

    List<User> getAllUsers();

    void deleteUser(String userId) throws UserNotFoundException;
}