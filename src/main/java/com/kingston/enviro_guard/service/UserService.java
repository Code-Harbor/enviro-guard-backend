package com.kingston.enviro_guard.service;

import com.kingston.enviro_guard.model.UserBean;
import com.kingston.enviro_guard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-05
 * TSTPL
 */

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserBean addUser(UserBean user) {
        return userRepository.save(user);

    }
}
