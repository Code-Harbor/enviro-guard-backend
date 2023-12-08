package com.kingston.enviro_guard.controller;

import com.kingston.enviro_guard.dto.request.UserLoginRequest;
import com.kingston.enviro_guard.dto.request.UserRegisterRequest;
import com.kingston.enviro_guard.model.UserBean;
import com.kingston.enviro_guard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-05
 * TSTPL
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity registerUser(@RequestBody UserRegisterRequest user) {
        return userService.registerUser(user);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get-user-by-id/{userId}")
    public ResponseEntity getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }
    @PostMapping("/delete-user-by-id/{userId}")
    public ResponseEntity deleteUserById(@PathVariable Integer userId) {
        return userService.deleteUserById(userId);
    }

    @PostMapping("/update-user")
    public ResponseEntity updateUser(@RequestBody UserRegisterRequest user) {
        return userService.updateUser(user);
    }

    @GetMapping("/get-all-users-by-institute/{instituteId}")
    public ResponseEntity getAllUsersByInstitute(@PathVariable Integer instituteId) {
        return userService.getAllUsersByInstitute(instituteId);
    }
    @PostMapping("/user-login")
    public ResponseEntity userLogin(@RequestBody UserLoginRequest user) {
        return userService.userLogin(user);
    }
}
