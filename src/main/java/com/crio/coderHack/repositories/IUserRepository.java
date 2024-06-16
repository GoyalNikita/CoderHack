package com.crio.coderHack.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crio.coderHack.models.UserEntity;

public interface IUserRepository extends MongoRepository<UserEntity, String>{
    Optional<UserEntity> findByUserId(String userId);
} 
