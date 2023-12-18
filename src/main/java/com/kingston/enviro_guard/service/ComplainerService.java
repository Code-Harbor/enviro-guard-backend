package com.kingston.enviro_guard.service;

import com.kingston.enviro_guard.dto.request.ComplainerLoginRequest;
import com.kingston.enviro_guard.dto.request.ComplainerRegisterRequest;
import com.kingston.enviro_guard.model.ComplainerBean;
import com.kingston.enviro_guard.repository.ComplainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ComplainerService {
    private final ComplainerRepository complainerRepository;

    public ResponseEntity registerComplainer(ComplainerRegisterRequest complainer) {
        if (complainer.getName() == null || complainer.getName().trim().isEmpty()) {
            return new ResponseEntity<>("Complainer name cannot be empty.", HttpStatus.BAD_REQUEST);
        }
        if (complainer.getEmail() == null || complainer.getEmail().trim().isEmpty()) {
            return new ResponseEntity<>("Complainer E-mail cannot be empty.", HttpStatus.BAD_REQUEST);
        }
        if (complainer.getPassword() == null || complainer.getPassword().trim().isEmpty()) {
            return new ResponseEntity<>("Complainer password cannot be empty.", HttpStatus.BAD_REQUEST);
        }

        try {
            ComplainerBean complainerBean = new ComplainerBean();
            complainerBean.setName(complainer.getName());
            complainerBean.setEmail(complainer.getEmail());
            complainerBean.setPassword(complainer.getPassword());
            complainerBean.setPhoneNumber(null);
            complainerBean.setAddress(null);

            ComplainerBean saveComplainer = complainerRepository.save(complainerBean);
            return new ResponseEntity<>(saveComplainer, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Email already exists.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getAllComplainer() {
        try {
            List<ComplainerBean> complainerBeans = complainerRepository.findAll();
            return new ResponseEntity<>(complainerBeans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getComplainerById(Integer complainerId) {
        try {
            ComplainerBean complainerById = complainerRepository.findById(complainerId).orElse(null);

            if (complainerById == null) {
                return new ResponseEntity<>("Complainer not found with the id.", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(complainerById, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity deleteComplainerById(Integer complainerId) {
        try {
            if (complainerRepository.existsById(complainerId)) {
                complainerRepository.deleteById(complainerId);
                return new ResponseEntity<>("Complainer deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Complainer not found with the id: " + complainerId, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity updateComplainer(ComplainerBean complainer) {
        if (complainer.getId() == null) {
            return new ResponseEntity<>("Complainer id cannot be null.", HttpStatus.BAD_REQUEST);
        }

        // check if the complainer with the given ID exists.
        ComplainerBean existingComplainer = complainerRepository.findById(complainer.getId()).orElse(null);

        if (existingComplainer == null) {
            return new ResponseEntity<>("Complainer not found", HttpStatus.NOT_FOUND);
        }

        if (complainer.getName() == null || complainer.getName().trim().isEmpty()) {
            return new ResponseEntity<>("Complainer name cannot be empty.", HttpStatus.BAD_REQUEST);
        }

        if (complainer.getEmail() == null || complainer.getEmail().trim().isEmpty()) {
            return new ResponseEntity<>("Complainer email cannot be empty.", HttpStatus.BAD_REQUEST);
        }


        try {

            existingComplainer.setName(complainer.getName());
            existingComplainer.setEmail(complainer.getEmail());
            existingComplainer.setPhoneNumber(complainer.getPhoneNumber());
            existingComplainer.setAddress(complainer.getAddress());

            // update password if provided
            if (complainer.getPassword() != null) {
                existingComplainer.setPassword(complainer.getPassword());
            }

            ComplainerBean updatedComplainer = complainerRepository.save(existingComplainer);
            return new ResponseEntity<>(updatedComplainer, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Complainer email already exists.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity complainerLogin(ComplainerLoginRequest complainer) {
        if (complainer.getEmail() == null) {
            return new ResponseEntity<>("Complainer email cannot be null.", HttpStatus.BAD_REQUEST);
        }

        if (complainer.getPassword() == null) {
            return new ResponseEntity<>("Complainer password cannot be null.", HttpStatus.BAD_REQUEST);
        }

        try {
            ComplainerBean existingComplainer = complainerRepository.findByEmail(complainer.getEmail());
            if (existingComplainer == null) {
                return new ResponseEntity<>("Complainer not fount with the provide email.", HttpStatus.NOT_FOUND);
            }

            // Check if the provided password matches the complainer's password
            if (!complainer.getPassword().equals(existingComplainer.getPassword())) {
                return new ResponseEntity<>("Incorrect password.", HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(existingComplainer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
