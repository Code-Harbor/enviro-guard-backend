package com.kingston.enviro_guard.controller;

import com.kingston.enviro_guard.service.InvestigationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-19
 * TSTPL
 */

@RestController
@RequestMapping("/investigation")
@RequiredArgsConstructor
public class InvestigationController {

    private final InvestigationService investigationService;

    @PostMapping("/add-investigation-report")
    public ResponseEntity addComplaint(@RequestParam("complaintId") Integer complaintId,
                                       @RequestParam("description") String description,
                                       @RequestParam("imageFile") MultipartFile imageFile) {
        return investigationService.addInvestigationReport(complaintId, description, imageFile);
    }
}
