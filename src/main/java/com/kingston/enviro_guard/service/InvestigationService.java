package com.kingston.enviro_guard.service;

import com.kingston.enviro_guard.model.ComplainerBean;
import com.kingston.enviro_guard.model.ComplaintBean;
import com.kingston.enviro_guard.model.InstituteBean;
import com.kingston.enviro_guard.model.InvestigationBean;
import com.kingston.enviro_guard.repository.ComplaintRepository;
import com.kingston.enviro_guard.repository.InvestigationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-19
 * TSTPL
 */

@Component
@RequiredArgsConstructor
public class InvestigationService {

    public final InvestigationRepository investigationRepository;
    public final ComplaintRepository complaintRepository;

    public ResponseEntity addInvestigationReport(Integer complaintId, String description, MultipartFile imageFile) {
        try {
            // validate parameters
            if (complaintId == null || description == null) {
                return new ResponseEntity<>("Invalid request parameters.", HttpStatus.BAD_REQUEST);
            }

            // check if complaint exists
            ComplaintBean complaint = complaintRepository.findById(complaintId).orElse(null);

            // save the image to a storage location or process it as needed
            String imageUrl = null;
            if (imageFile != null) {
                imageUrl = saveComplaintImage(imageFile);
            }

            InvestigationBean investigation = new InvestigationBean();
            investigation.setDescription(description);
            investigation.setAddedDateTime(new Date());
            investigation.setStatus("Not Approved");
            investigation.setImage(imageUrl);
            investigation.setComplaint(complaint);

            InvestigationBean savedReport = investigationRepository.save(investigation);

            return new ResponseEntity<>(savedReport, HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>("Error processing the image.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String saveComplaintImage(MultipartFile imageFile) throws IOException {
        // define the directory where you want to store the images
        String uploadDirectory = "C:\\xampp\\htdocs\\images\\enviro-guard\\investigation\\";

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
        return "http://localhost/images/enviro-guard/investigation/" + fileName;
    }
}
