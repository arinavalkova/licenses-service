package ru.shift.baldezh.licenses.service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.shift.baldezh.licenses.service.repository.LicenseRepository;
import ru.shift.baldezh.licenses.service.repository.UserRepository;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;
import ru.shift.baldezh.licenses.service.repository.specification.LicenseSpecification;
import ru.shift.baldezh.licenses.service.repository.specification.UserSpecification;
import ru.shift.baldezh.licenses.service.service.EmployeeService;

import java.util.List;

@Component
public class EmployeeServiceImpl implements EmployeeService {
    private final LicenseRepository licenseRepository;
    private final UserRepository userRepository;

    public EmployeeServiceImpl(LicenseRepository licenseRepository, UserRepository userRepository) {
        this.licenseRepository = licenseRepository;
        this.userRepository = userRepository;
    }

    private <T>  T getObject(String filter, Class<T> type) throws JsonProcessingException {
        return new ObjectMapper().readValue(filter, type);
    }

    private LicenseEntity getLicense(String filter) throws JsonProcessingException {
        return getObject(filter, LicenseEntity.class);
    }

    private LicenseSpecification getLicenseSpecification(String filter) throws JsonProcessingException {
        return new LicenseSpecification(getLicense(filter));
    }

    private UserInfoEntity getUser(String filter) throws JsonProcessingException {
        return getObject(filter, UserInfoEntity.class);
    }

    private UserSpecification getUserSpecification(String filter) throws JsonProcessingException {
        return new UserSpecification(getUser(filter));
    }

    @Override
    public List<LicenseEntity> getLicenseList(String filter) throws JsonProcessingException {
        return licenseRepository.findAll(getLicenseSpecification(filter));
    }

    @Override
    public int getLicenseCount(String filter) throws JsonProcessingException {
        return (int) licenseRepository.count(getLicenseSpecification(filter));
    }

    @Override
    public List<UserInfoEntity> getUserList(String filter) throws JsonProcessingException {
        return userRepository.findAll(getUserSpecification(filter));
    }

    @Override
    public int getUserCount(String filter) throws JsonProcessingException {
        return (int) userRepository.count(getUserSpecification(filter));
    }
}
