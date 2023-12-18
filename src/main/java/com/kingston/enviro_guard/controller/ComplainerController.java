package com.kingston.enviro_guard.controller;

import com.kingston.enviro_guard.dto.request.ComplainerLoginRequest;
import com.kingston.enviro_guard.dto.request.ComplainerRegisterRequest;
import com.kingston.enviro_guard.model.ComplainerBean;
import com.kingston.enviro_guard.service.ComplainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/complainer")
@RequiredArgsConstructor
public class ComplainerController {
    private final ComplainerService complainerService;

    @PostMapping("/register-complainer")
    public ResponseEntity registerComplainer(@RequestBody ComplainerRegisterRequest complainer) {
        return complainerService.registerComplainer(complainer);
    }

    @GetMapping("/get-all-complainer")
    public ResponseEntity getAllComplainer() {
        return complainerService.getAllComplainer();
    }

    @GetMapping("/get-complainer-by-id/{complainerId}")
    public ResponseEntity getComplainerById(@PathVariable Integer complainerId) {
        return complainerService.getComplainerById(complainerId);
    }

    @PostMapping("/delete-complainer-by-id/{complainerId}")
    public ResponseEntity deleteComplainerById(@PathVariable Integer complainerId) {
        return complainerService.deleteComplainerById(complainerId);
    }

    @PostMapping("/update-complainer")
    public ResponseEntity updateComplainer(@RequestBody ComplainerBean complainer) {
        return complainerService.updateComplainer(complainer);
    }

    @PostMapping("/complainer-login")
    public ResponseEntity complainerLogin(@RequestBody ComplainerLoginRequest complainer) {
        return complainerService.complainerLogin(complainer);
    }
}
