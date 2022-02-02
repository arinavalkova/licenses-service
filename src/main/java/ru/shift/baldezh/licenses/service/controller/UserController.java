package ru.shift.baldezh.licenses.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;
import ru.shift.baldezh.licenses.service.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "")
    public ResponseEntity<Void> addNewUser(@RequestBody UserInfoEntity userInfoEntity) {
        userService.addUser(userInfoEntity);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "")
    public ResponseEntity<List<UserInfoEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserInfoEntity>> getUserById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("")
    public ResponseEntity<Void> updateUser(@RequestBody UserInfoEntity userInfoEntity) {
        userService.updateUser(userInfoEntity);
        return ResponseEntity.ok().build();
    }
}
