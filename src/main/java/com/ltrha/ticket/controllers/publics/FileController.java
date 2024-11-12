package com.ltrha.ticket.controllers.publics;

import com.ltrha.ticket.exception.FlightTicketRuntimeException;
import com.ltrha.ticket.modules.minio.MinioRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);
    private final MinioRepo minioRepo;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam(name = "file") MultipartFile file) {
        // upload file to minio
        try {

            minioRepo.putObject("trye-bucket", file, file.getOriginalFilename(), file.getContentType());
        } catch (Exception e) {
            logger.warn("Failed to upload file", e);
            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR,"Failed to upload file");
        }
        return ResponseEntity.ok().build();

    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam(name = "file") String file, HttpServletResponse response) {
        // get file from minio
        try {
            var object = minioRepo.getObject("trye-bucket", file);
            response.getOutputStream().write(object.readAllBytes());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.warn("Failed to get file", e);
            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR,"Failed to get file");
        }
    }
}
