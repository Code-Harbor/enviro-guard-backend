package com.kingston.enviro_guard.service;

import com.kingston.enviro_guard.dto.request.UserLoginRequest;
import com.kingston.enviro_guard.dto.request.UserRegisterRequest;
import com.kingston.enviro_guard.model.InstituteBean;
import com.kingston.enviro_guard.model.UserBean;
import com.kingston.enviro_guard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-05
 * TSTPL
 */

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity registerUser(UserRegisterRequest user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return new ResponseEntity<>("Name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return new ResponseEntity<>("Email cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return new ResponseEntity<>("Password cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (user.getType() == null || user.getType().trim().isEmpty()) {
            return new ResponseEntity<>("User type cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            return new ResponseEntity<>("User role cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (user.getInstituteId() == null) {
            return new ResponseEntity<>("Institute cannot be empty", HttpStatus.BAD_REQUEST);
        }

        try {
            UserBean userBean = new UserBean();
            userBean.setName(user.getName());
            userBean.setEmail(user.getEmail());
            userBean.setPassword(user.getPassword());
            userBean.setType(user.getType());
            userBean.setRole(user.getRole());
            userBean.setInstitute(new InstituteBean(user.getInstituteId()));

            UserBean savedUser = userRepository.save(userBean);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getAllUsers() {
        try {
            List<UserBean> userList = userRepository.findAll();
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getUserById(Integer userId) {
        try {

            UserBean userById = userRepository.findById(userId).orElse(null);

            if (userById == null) {
                return new ResponseEntity<>("User not found with the id", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(userById, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity deleteUserById(Integer userId) {
        try {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found with the id: " + userId, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity updateUser(UserRegisterRequest user) {
        if (user.getId() == null) {
            return new ResponseEntity<>("User ID cannot be null", HttpStatus.BAD_REQUEST);
        }

        // Check if the user with the given ID exists
        UserBean existingUser = userRepository.findById(user.getId()).orElse(null);

        if (existingUser == null) {
            return new ResponseEntity<>("User not found with the id: " + user.getId(), HttpStatus.NOT_FOUND);
        }


        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return new ResponseEntity<>("Name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return new ResponseEntity<>("Email cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return new ResponseEntity<>("Password cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (user.getType() == null || user.getType().trim().isEmpty()) {
            return new ResponseEntity<>("User type cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            return new ResponseEntity<>("User role cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (user.getInstituteId() == null) {
            return new ResponseEntity<>("Institute cannot be empty", HttpStatus.BAD_REQUEST);
        }

        try {
            // Update the user fields with the new values
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setType(user.getType());
            existingUser.setRole(user.getRole());
            existingUser.setInstitute(new InstituteBean(user.getInstituteId()));


            // Save the updated user
            UserBean updatedUser = userRepository.save(existingUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getAllUsersByInstitute(Integer instituteId) {
        try {
            List<UserBean> userList = userRepository.findByInstituteId(instituteId);
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity userLogin(UserLoginRequest user) {
        if (user.getEmail() == null) {
            return new ResponseEntity<>("User email cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (user.getPassword() == null) {
            return new ResponseEntity<>("User password cannot be null", HttpStatus.BAD_REQUEST);
        }

        try {

            // Find the user by email
            UserBean existingUser = userRepository.findByEmail(user.getEmail());

            if (existingUser == null) {
                return new ResponseEntity<>("User not found with the provided email", HttpStatus.NOT_FOUND);
            }

            // Check if the provided password matches the user's password
            if (!user.getPassword().equals(existingUser.getPassword())) {
                return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
            }

            // Return success response
            return new ResponseEntity<>(existingUser,  HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
