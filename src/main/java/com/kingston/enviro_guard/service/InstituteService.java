package com.kingston.enviro_guard.service;

import com.kingston.enviro_guard.dto.request.UserRegisterRequest;
import com.kingston.enviro_guard.model.InstituteBean;
import com.kingston.enviro_guard.model.UserBean;
import com.kingston.enviro_guard.repository.InstitureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-15
 * TSTPL
 */

@Component
@RequiredArgsConstructor
public class InstituteService {

    private final InstitureRepository institureRepository;

    public ResponseEntity addInstitute(InstituteBean institute) {
        // check for null institute
        if(institute.getName() ==null || institute.getName().trim().isEmpty()) {
            return new ResponseEntity<>("Institute name cannot be empty", HttpStatus.BAD_REQUEST);
        }

        try {
            InstituteBean savedInstitute = institureRepository.save(institute);
            return new ResponseEntity<>(savedInstitute, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Institute already exists", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getAllInstitute() {
        try {
            List<InstituteBean> instituteList = institureRepository.findAll();
            return new ResponseEntity<>(instituteList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
