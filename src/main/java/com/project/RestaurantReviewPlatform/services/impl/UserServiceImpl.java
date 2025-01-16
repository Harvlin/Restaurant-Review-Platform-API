package com.project.RestaurantReviewPlatform.services.impl;

import com.project.RestaurantReviewPlatform.domain.entity.UserEntity;
import com.project.RestaurantReviewPlatform.repositories.UserRepository;
import com.project.RestaurantReviewPlatform.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.findByName(userEntity.getName())
                .orElseGet(() -> userRepository.save(userEntity));
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<UserEntity> findOne(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserEntity> findByName(String name) {
        return userRepository.findAllByName(name);
    }

    @Override
    public UserEntity partialUpdate(UUID id, UserEntity userEntity) {
        userEntity.setId(id);

        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(userEntity.getName()).ifPresent(existingUser::setName);
            Optional.ofNullable(userEntity.getDescription()).ifPresent(existingUser::setDescription);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User doesn't exist"));
    }

    @Override
    public void deleteUser(UUID id) {
        if (!isExist(id)) {
            throw new RuntimeException("User not found");
        } else {
            userRepository.deleteById(id);
        }
    }

    @Override
    public UserEntity getUserById(UUID uuid) {
        return userRepository.getUserById(uuid);
    }

    @Override
    public boolean isExist(UUID id) {
        return userRepository.existsById(id);
    }
}
