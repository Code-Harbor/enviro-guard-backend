package com.kingston.enviro_guard.service;

import com.kingston.enviro_guard.dto.response.ComplaintWithInvestigationResponce;
import com.kingston.enviro_guard.model.ComplaintBean;
import com.kingston.enviro_guard.model.InstituteBean;
import com.kingston.enviro_guard.model.InvestigationBean;
import com.kingston.enviro_guard.repository.ComplaintRepository;
import com.kingston.enviro_guard.repository.ComplainerRepository;
import com.kingston.enviro_guard.repository.InvestigationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.kingston.enviro_guard.model.ComplainerBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-18
 * TSTPL
 */

@Component
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ComplainerRepository complainerRepository;
    private final InvestigationRepository investigationRepository;

    public ResponseEntity addComplaint(Integer complainerId, Integer instituteId, String complainTitle,
                                       String complainDescription, String location, MultipartFile imageFile) {
        try {
            // validate parameters
            if (instituteId == null || complainTitle == null || complainDescription == null) {
                return new ResponseEntity<>("Invalid request parameters.", HttpStatus.BAD_REQUEST);
            }

            // check if complainer exists
            ComplainerBean complainer = complainerRepository.findById(complainerId).orElse(null);

            // save the image to a storage location or process it as needed
            String imageUrl = null;
            if (imageFile != null) {
                imageUrl = saveComplaintImage(imageFile);
            }

            // create and save the complaint
            ComplaintBean complaint = new ComplaintBean();

            complaint.setComplaintTitle(complainTitle);
            complaint.setComplaintDescription(complainDescription);
            complaint.setLocation(location);
            complaint.setAddedDateTime(new Date());
            complaint.setStatus("Pending");
            complaint.setImage(imageUrl);
            complaint.setInstitute(new InstituteBean(instituteId));
            complaint.setComplainer(complainer);

            ComplaintBean savedComplain = complaintRepository.save(complaint);

            return new ResponseEntity<>(savedComplain, HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>("Error processing the image.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String saveComplaintImage(MultipartFile imageFile) throws IOException {
        // define the directory where you want to store the images
        String uploadDirectory = "D:\\Sanjaya Personal\\Kingston Uni\\Software Development Practices\\Course Works\\Project\\Backend\\images\\complaints\\";

        // ensure the directory exists, create it if necessary
        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // generate a unique file name for the uploaded image
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

        // define the path where the image will be stored
        Path filePath = Path.of(uploadDirectory + fileName);

        // copy the image to the specified path
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // return the URL or path of the saved image
        return "D:\\Sanjaya Personal\\Kingston Uni\\Software Development Practices\\Course Works\\Project\\Backend\\images\\complaints\\" + fileName;
    }

    public ResponseEntity getAllComplaintsByComplainerId(Integer complainerId) {
        try {

            List<ComplaintBean> complaints = complaintRepository.findAllByComplainerId(complainerId);
            return new ResponseEntity<>(complaints, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getAllComplaintsByInstituteId(Integer instituteId) {
        try {

            List<ComplaintBean> complaints = complaintRepository.findAllByInstituteId(instituteId);
            return new ResponseEntity<>(complaints, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity updateComplaintStatus(Integer complaintId, String newStatus) {
        try {

            complaintRepository.updateStatus(complaintId, newStatus);
            return new ResponseEntity<>("Status updated successfully.", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity getComplaintStatusCountByInstitute(Integer instituteId) {
        try {
            List<Object[]> statusCounts = complaintRepository.getStatusCountByInstitute(instituteId);
            Long totalCount = complaintRepository.getCountByInstitute(instituteId);

            Map<String, Long> statusCountMap = new HashMap<>();

            // Initialize counts for all statuses to 0
            for (String status : getAllStatuses()) {
                statusCountMap.put(status, 0L);
            }

            // Update counts based on the query result
            for (Object[] row : statusCounts) {
                String status = (String) row[0];
                Long count = (Long) row[1];
                statusCountMap.put(status, count);
            }

            // Add total count to the response
            statusCountMap.put("Total", totalCount);

            return new ResponseEntity<>(statusCountMap, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<String> getAllStatuses() {
        return List.of("Pending", "Ongoing", "Completed");
    }


    public Optional<ComplaintWithInvestigationResponce> getComplaintWithInvestigation(Integer complaintId) {
        Optional<ComplaintBean> optionalComplaint = complaintRepository.findById(complaintId);

        if (optionalComplaint.isPresent()) {
            ComplaintBean complaint = optionalComplaint.get();

            // Access the associated InvestigationBean using the naming convention
            Optional<InvestigationBean> optionalInvestigation = investigationRepository.findByComplaintId(complaint.getId());

            if (optionalInvestigation.isPresent()) {
                InvestigationBean investigation = optionalInvestigation.get();

                // Create a DTO to hold both ComplaintBean and InvestigationBean
                ComplaintWithInvestigationResponce resultDTO = new ComplaintWithInvestigationResponce(complaint, investigation);

                return Optional.of(resultDTO);
            } else {
                // If no investigation is found, create a DTO with only the ComplaintBean
                ComplaintWithInvestigationResponce resultDTO = new ComplaintWithInvestigationResponce(complaint, null);

                return Optional.of(resultDTO);
            }
        } else {
            // Complaint with the given ID not found
            return Optional.empty();
        }
    }
}
