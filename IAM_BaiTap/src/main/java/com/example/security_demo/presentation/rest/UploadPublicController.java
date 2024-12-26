package com.example.security_demo.presentation.rest;

import com.evo.common.client.storage.StorageClient;
import com.example.security_demo.application.service.CredentialService;
//import com.example.security_demo.service.storageService.StorageServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("api/public/file")
@RestController
@RequiredArgsConstructor
public class UploadPublicController {
    //    private final StorageServiceClient storageServiceClient;
    private final CredentialService credentialService;
    private final StorageClient storageClient;


    //    @GetMapping("/test")
//    public ResponseEntity<?> test() {
//        return ResponseEntity.ok().body(storageServiceClient.test().getBody());
//    }
    @GetMapping("/get-content/{fileId}")
    public ResponseEntity<Resource> getContent(@PathVariable("fileId") String fileId) {
        ResponseEntity<Resource> response = storageClient.getContent(fileId);
        return ResponseEntity.status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody());
    }

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file,
                                        @RequestParam("visibility") boolean visibility,
                                        @RequestParam("version") String version) {
        try {
            String owner = credentialService.getCredentialInfor();
            return ResponseEntity.ok()
                    .body(storageClient.uploadPublicFile(file, visibility, version, owner).getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> download(@PathVariable("fileId") String fileId) {
        ResponseEntity<Resource> response = storageClient.downloadFile(fileId);
        return ResponseEntity.ok()
                .headers(response.getHeaders())
                .body(response.getBody());
    }
}
