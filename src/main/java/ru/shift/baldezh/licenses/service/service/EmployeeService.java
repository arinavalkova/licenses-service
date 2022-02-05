package ru.shift.baldezh.licenses.service.service;

import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;

import java.util.List;

public interface EmployeeService {
    List<LicenseEntity> getLicenseList(String filter);

    int getLicenseCount(String filter);

    List<UserInfoEntity> getUserList(String filter);

    int getUserCount(String filter);
}
