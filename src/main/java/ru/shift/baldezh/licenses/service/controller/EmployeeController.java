package ru.shift.baldezh.licenses.service.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;
import ru.shift.baldezh.licenses.service.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("license/list")
    public List<LicenseEntity> getFilteredLicenseList(@RequestBody String filter) throws JsonProcessingException {
        return employeeService.getLicenseList(filter);
    }

    @GetMapping("license/count")
    public int getFilteredLicenseCount(@RequestBody String filter) throws JsonProcessingException {
        return employeeService.getLicenseCount(filter);
    }

    @GetMapping("user/list")
    public List<UserInfoEntity> getFilteredUserList(@RequestBody String filter) throws JsonProcessingException {
        return employeeService.getUserList(filter);
    }

    @GetMapping("user/count")
    public int getFilteredUserCount(@RequestBody String filter) throws JsonProcessingException {
        return employeeService.getUserCount(filter);
    }
}
