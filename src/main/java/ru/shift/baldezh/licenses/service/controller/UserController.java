package ru.shift.baldezh.licenses.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;
import ru.shift.baldezh.licenses.service.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfoEntity userInfoEntity) {
        userService.addUser(userInfoEntity);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "")
    public ResponseEntity<List<UserInfoEntity>> getAllUsers() {
        List<UserInfoEntity> users = StreamSupport.stream(userService.getAllUsers().spliterator(), false).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("")
    public ResponseEntity<Optional<UserInfoEntity>> getUserById(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("")
    public ResponseEntity<?> updateUser(@RequestBody UserInfoEntity userInfoEntity) {
        userService.updateUser(userInfoEntity);
        return ResponseEntity.ok().build();
    }
}
