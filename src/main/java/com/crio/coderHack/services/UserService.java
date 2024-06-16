package com.crio.coderHack.services;

import java.util.List;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crio.coderHack.dto.User;
import com.crio.coderHack.exceptions.UserNotFoundException;
import com.crio.coderHack.repositoryServices.IUserRepositoryService;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepositoryService userRepositoryService;

    @Override
    public User addUser(String username) {
        User user = userRepositoryService.addUser(username);
        return user;
    }

    @Override
    public User updateScore(String userId, int score) throws UserNotFoundException {
        User user = userRepositoryService.updateScore(userId, score);
        return user;
    }

    @Override
    public User getUserById(String userId) throws UserNotFoundException {
        User user = userRepositoryService.getUserById(userId);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepositoryService.getAllUsers();
        sortUsersBasedOnScore(users);
        return users;
    }

    @Override
    public void deleteUser(String userId) throws UserNotFoundException {
        userRepositoryService.deleteUser(userId);
    }

    // sorting users with tc O(nlogn)
    private static void sortUsersBasedOnScore(List<User> users) {
        PriorityQueue<User> maxHeap = new PriorityQueue<>((u1, u2) -> u2.getScore() - u1.getScore());
        maxHeap.addAll(users);
        users.clear();
        while (!maxHeap.isEmpty()) {
            users.add(maxHeap.poll());
        }
    }

}
