package com.crio.coderHack.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.crio.coderHack.dto.User;
import com.crio.coderHack.exceptions.UserNotFoundException;
import com.crio.coderHack.repositoryServices.IUserRepositoryService;

public class UserServiceTest {

    @Mock
    private IUserRepositoryService userRepositoryService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        // initializing mock before each test case.
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser() {
        String expectedUserName = "user1";
        String expectedUserId = "1";
        User user = new User();
        user.setUserId(expectedUserId);
        user.setUsername(expectedUserName);

        when(userRepositoryService.addUser(anyString())).thenReturn(user);

        // Act begin
        User savedUser = userService.addUser(expectedUserName);
        String actualUsername = savedUser.getUsername();
        String actualUserId = savedUser.getUserId();
        // Act ends

        // Assert begin
        assertEquals(expectedUserId, actualUserId);
        assertEquals(expectedUserName, actualUsername);
        // Assert ends
    }

    @Test
    public void testUpdateScore() throws UserNotFoundException {
        User user = new User();
        user.setUserId("1");
        user.setUsername("user1");
        user.setScore(70);

        int expectedScore = 70;

        when(userRepositoryService.updateScore(anyString(), anyInt())).thenReturn(user);

        // Act begin
        User userWithUpdatedScore = userService.updateScore("1", 70);
        int actualScore = userWithUpdatedScore.getScore();

        // Assert begin
        assertEquals(expectedScore, actualScore);
    }

    @Test
    public void testGetUserById() throws UserNotFoundException {
        User user = new User();
        user.setUserId("1");
        user.setUsername("user1");

        String expectedUserName = "user1";
        String expectedUserId = "1";

        when(userRepositoryService.getUserById(anyString())).thenReturn(user);

        User retrievedUser = userService.getUserById("1");

        // Act begin
        String actualUsername = retrievedUser.getUsername();
        String actualUserId = retrievedUser.getUserId();

        // Assert begin
        assertEquals(expectedUserId, actualUserId);
        assertEquals(expectedUserName, actualUsername);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setUserId("1");
        user1.setUsername("user1");
        user1.setScore(75);

        User user2 = new User();
        user2.setUserId("2");
        user2.setUsername("user2");
        user2.setScore(45);

        int expectedUserCount = 2;
        String expectedUser1Id = "1";
        String expectedUser2Id = "2";

        // when(userRepositoryService.getAllUsers()).thenReturn(Arrays.asList(user1,
        // user2));
        when(userRepositoryService.getAllUsers()).thenReturn(new ArrayList<>(Arrays.asList(user1, user2)));

        List<User> retrievedUsers = userService.getAllUsers();

        System.out.println(retrievedUsers);

        // Act begin
        int actualUserCount = retrievedUsers.size();
        String actualUser1Id = retrievedUsers.get(0).getUserId();
        String actualUser2Id = retrievedUsers.get(1).getUserId();

        // Assert begin
        assertEquals(expectedUserCount, actualUserCount);
        assertEquals(expectedUser1Id, actualUser1Id);
        assertEquals(expectedUser2Id, actualUser2Id);
    }

    @Test
    public void testDeleteUser() throws UserNotFoundException {
        User user = new User();
        user.setUserId("1");
        user.setUsername("user1");

        // Act begin
        userService.deleteUser("1");

        // Assert begin
        verify(userRepositoryService, times(1)).deleteUser("1");
    }
}
