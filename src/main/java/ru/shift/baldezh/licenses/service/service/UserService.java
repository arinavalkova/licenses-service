package ru.shift.baldezh.licenses.service.service;

import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;

import java.util.Optional;

public interface UserService {
    Iterable<UserInfoEntity> getAllUsers();
    void addUser(UserInfoEntity userInfoEntity);
    Optional<UserInfoEntity> getUserById(Long id);
    void updateUser(UserInfoEntity userInfoEntity);
}
