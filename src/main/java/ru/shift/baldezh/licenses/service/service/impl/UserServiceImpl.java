package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.stereotype.Service;
import ru.shift.baldezh.licenses.service.repository.UserRepository;
import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;
import ru.shift.baldezh.licenses.service.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<UserInfoEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(UserInfoEntity userInfoEntity) {
        userRepository.save(userInfoEntity);
    }

    @Override
    public Optional<UserInfoEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateUser(UserInfoEntity userInfoEntity) {
        userRepository.save(userInfoEntity);
    }
}
