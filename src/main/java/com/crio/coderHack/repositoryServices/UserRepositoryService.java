package com.crio.coderHack.repositoryServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crio.coderHack.dto.Badge;
import com.crio.coderHack.dto.User;
import com.crio.coderHack.exceptions.UserNotFoundException;
import com.crio.coderHack.models.UserEntity;
import com.crio.coderHack.repositories.IUserRepository;

import jakarta.inject.Provider;

@Service
public class UserRepositoryService implements IUserRepositoryService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private Provider<ModelMapper> modelMapperProvider;

    @Override
    public User addUser(String username) {
        ModelMapper modelMapper = modelMapperProvider.get();
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        User user = modelMapper.map(userRepository.save(userEntity), User.class);
        return user;
    }

    @Override
    public User updateScore(String userId, int score) throws UserNotFoundException {
        ModelMapper modelMapper = modelMapperProvider.get();
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(userId);

        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            userEntity.setScore(score);
            generateBadge(userEntity, score);
            userRepository.save(userEntity);
            return modelMapper.map(userEntity, User.class);
        } else
            throw new UserNotFoundException();
    }

    @Override
    public User getUserById(String userId) throws UserNotFoundException {
        ModelMapper modelMapper = modelMapperProvider.get();
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);

        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            return modelMapper.map(userEntity, User.class);
        } else
            throw new UserNotFoundException();
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<User> users = convertToUserList(userEntities);
        return users;
    }

    @Override
    public void deleteUser(String userId) throws UserNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);

        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            userRepository.delete(userEntity);
        } else
            throw new UserNotFoundException();
    }

    // Badges added based oon User's score

    private void generateBadge(UserEntity userEntity, int score) {
        List<Badge> badges = userEntity.getBadges();
        badges.clear();

        if (score >= 60 && score <= 100) {
            badges.add(Badge.CodeNinja);
            badges.add(Badge.CodeChamp);
            badges.add(Badge.CodeMaster);
        } else if (score >= 30 && score < 60) {
            badges.add(Badge.CodeNinja);
            badges.add(Badge.CodeChamp);
        } else if (score >= 1 && score < 30)
            badges.add(Badge.CodeNinja);

    }

    private List<User> convertToUserList(List<UserEntity> userEntities) {
        ModelMapper modelMapper = modelMapperProvider.get();
        List<User> users = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            User user = modelMapper.map(userEntity, User.class);
            users.add(user);
        }
        return users;
    }

}
