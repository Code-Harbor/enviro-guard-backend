package com.kingston.enviro_guard.controller;

import com.kingston.enviro_guard.model.UserBean;
import com.kingston.enviro_guard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/add-user")
    public UserBean addUser(@RequestBody UserBean user) {
        return userService.addUser(user);
    }

}
