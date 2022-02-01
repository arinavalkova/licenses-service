package ru.shift.baldezh.licenses.service.service;

import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;

public interface UserService {
    Iterable<UserInfoEntity> getAllUsers();
    void addUser(UserInfoEntity userInfoEntity);
    void getUserById(Long id);
    void updateUser(UserInfoEntity userInfoEntity);
}
