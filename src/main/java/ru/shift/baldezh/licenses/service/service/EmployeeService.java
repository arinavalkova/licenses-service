package ru.shift.baldezh.licenses.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;

import java.util.List;

public interface EmployeeService {
    List<LicenseEntity> getLicenseList(String filter) throws JsonProcessingException;

    int getLicenseCount(String filter) throws JsonProcessingException;

    List<UserInfoEntity> getUserList(String filter) throws JsonProcessingException;

    int getUserCount(String filter) throws JsonProcessingException;
}
