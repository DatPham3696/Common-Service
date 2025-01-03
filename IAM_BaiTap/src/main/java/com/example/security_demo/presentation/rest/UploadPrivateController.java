package com.example.security_demo.presentation.rest;

import com.evo.common.client.storage.StorageClient;
import com.example.security_demo.application.service.CredentialService;
//import com.example.security_demo.service.storageService.StorageServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@RequestMapping("api/private/file")
@RestController
@RequiredArgsConstructor
public class UploadPrivateController {

  //    private final StorageServiceClient storageServiceClient;
//    private final CredentialService credentialService;
  private final CredentialService credentialService;
  private final StorageClient storageClient;
//    @PostMapping("/paging")
//    @PreAuthorize("hasPermission('FILE','VIEW')")
//    public ResponseEntity<?> getListPage(@ModelAttribute FileRequest fileRequest) {
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(storageServiceClient.getListPage(fileRequest).getBody());
//    }

  //----------------------------------------------------
  @GetMapping("/view-image/{fileId}")
  @PreAuthorize("hasPermission('FILE','VIEW')")
  public ResponseEntity<?> viewImage(@PathVariable("fileId") String fileId,
      @RequestParam("width") Optional<Integer> width,
      @RequestParam("height") Optional<Integer> height,
      @RequestParam("ratio") Optional<Double> ratio) {
    try {
      ResponseEntity<byte[]> response = storageClient.viewImage(fileId, width, height, ratio);
      // Lấy body từ response (byte[])
      byte[] imageBytes = response.getBody();
      InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(imageBytes));
      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.CONTENT_TYPE, "image/png");
      headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileId + ".png");
      return ResponseEntity.ok()
          .headers(headers)
          .contentLength(imageBytes.length)
          .body(resource);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error view file:" + e.getMessage());
    }
  }

  @PostMapping("/upload-file")
  @PreAuthorize("hasPermission('FILE','CREATE')")
  public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file,
      @RequestParam("visibility") boolean visibility,
      @RequestParam("version") String version) {
    try {
      String owner = credentialService.getCredentialInfor();
      return ResponseEntity.ok()
          .body(storageClient.uploadPrivateFile(file, visibility, version, owner).getBody());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error uploading file: " + e.getMessage());
    }
  }
}
