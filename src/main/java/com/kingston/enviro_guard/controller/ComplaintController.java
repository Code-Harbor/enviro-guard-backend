package com.kingston.enviro_guard.controller;

import com.kingston.enviro_guard.dto.response.ComplaintWithInvestigationResponce;
import com.kingston.enviro_guard.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-18
 * TSTPL
 */

@RestController
@RequestMapping("/complaint")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping("/add-complaint")
    public ResponseEntity addComplaint(@RequestParam("complainerId") Integer complainerId,
                                       @RequestParam("instituteId") Integer instituteId,
                                       @RequestParam("complainTitle") String complainTitle,
                                       @RequestParam("complainDescription") String complainDescription,
                                       @RequestParam("location") String location,
                                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        // check if imageFile is null or empty
        if (imageFile == null || imageFile.isEmpty()) {
            return complaintService.addComplaint(complainerId, instituteId, complainTitle, complainDescription, location, null);
        }
        return complaintService.addComplaint(complainerId, instituteId, complainTitle, complainDescription, location, imageFile);

    }

    @GetMapping("/get-all-complains-by-complainer/{complainerId}")
    public ResponseEntity getAllByComplainer(@PathVariable Integer complainerId) {
        return complaintService.getAllComplaintsByComplainerId(complainerId);
    }

    @GetMapping("/get-all-complains-by-institute/{instituteId}")
    public ResponseEntity getAllByInstitute(@PathVariable Integer instituteId) {
        return complaintService.getAllComplaintsByInstituteId(instituteId);
    }

    @PostMapping("/update-status/{complaintId}")
    public ResponseEntity updateComplaintStatus(@PathVariable Integer complaintId, @RequestParam String newStatus) {
        return complaintService.updateComplaintStatus(complaintId, newStatus);

    }

    @GetMapping("/get-complain-status-count/{instituteId}")
    public ResponseEntity getComplaintStatusCountByInstitute(@PathVariable Integer instituteId) {
        return complaintService.getComplaintStatusCountByInstitute(instituteId);
    }

    @GetMapping("/get-complain-with-investigation/{complainerId}")
    public Optional<ComplaintWithInvestigationResponce> getComplaintWithInvestigation(@PathVariable Integer complainerId) {
        return complaintService.getComplaintWithInvestigation(complainerId);
    }
}
