package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.stereotype.Component;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;
import ru.shift.baldezh.licenses.service.service.EmployeeService;

import java.util.List;

@Component
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public List<LicenseEntity> getLicenseList(String filter) {
        return null;
    }

    @Override
    public int getLicenseCount(String filter) {
        return 0;
    }

    @Override
    public List<UserInfoEntity> getUserList(String filter) {
        return null;
    }

    @Override
    public int getUserCount(String filter) {
        return 0;
    }
}
