package com.kingston.enviro_guard.controller;

import com.kingston.enviro_guard.dto.request.UserRegisterRequest;
import com.kingston.enviro_guard.model.InstituteBean;
import com.kingston.enviro_guard.service.InstituteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-15
 * TSTPL
 */

@RestController
@RequestMapping("/institute")
@RequiredArgsConstructor
public class InstituteController {

    private final InstituteService instituteService;

    @PostMapping("/add-institute")
    public ResponseEntity addInstitute(@RequestBody InstituteBean institute) {
        return instituteService.addInstitute(institute);
    }

    @GetMapping("/get-all-institute")
    public  ResponseEntity getAllInstitute() {
        return instituteService.getAllInstitute();
    }
}
