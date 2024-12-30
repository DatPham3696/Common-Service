package com.example.security_demo.application.service.eiDataService;

import com.evo.common.client.storage.StorageClient;
import com.example.security_demo.application.dto.request.userProfile.UserProfileSearchRequest;
import com.example.security_demo.infrastructure.entity.UserActivityLogEntity;
import com.example.security_demo.infrastructure.entity.UserProfile;
import com.example.security_demo.domain.enums.LogInfor;
import com.example.security_demo.infrastructure.persistance.JpaUserProfileRepository;
import com.example.security_demo.infrastructure.repository.custom.UserProfileImpl;
import com.example.security_demo.application.service.CredentialService;

import com.example.security_demo.application.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportDataService {
    private final JpaUserProfileRepository userProfileRepository;
    private final UserProfileImpl userProfile;
    private final CredentialService credentialService;
    private final StorageClient storageServiceClient;
    private final LogService logService;
    private final HttpServletRequest httpRequest;

    public ByteArrayInputStream exportUserData(UserProfileSearchRequest request) throws IOException {
        List<UserProfile> userProfiles = userProfile.searchUser(request);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("User data");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"STT", "Username", "Full Name", "Birth Date", "Street", "District", "Province", "Experience"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
        }

        Font dataFont = workbook.createFont();
        dataFont.setFontName("Arial");
        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setFont(dataFont);

        int rowIndex = 1;
        for (UserProfile user : userProfiles) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(rowIndex);
            row.createCell(1).setCellValue(user.getUsername());
            row.createCell(2).setCellValue(user.getFullName());
            row.createCell(3).setCellValue(user.getBirthDate());
            row.createCell(4).setCellValue(user.getStreet());
            row.createCell(5).setCellValue(user.getDistrict());
            row.createCell(6).setCellValue(user.getProvince());
            row.createCell(7).setCellValue(user.getExperience());

            for (int i = 0; i < headers.length; i++) {
                row.getCell(i).setCellStyle(dataCellStyle);
            }
        }
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        workbook.write(outputStream);
        workbook.close();
        logService.saveLog(UserActivityLogEntity.builder()
                .action(LogInfor.EXPORTDATA.getDescription())
                .browserId(httpRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build());
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    public Object exportDataToStorage(UserProfileSearchRequest request) throws IOException {
        ByteArrayInputStream byteArrayInputStream = exportUserData(request);
        MultipartFile multipartFile = FileUtils.convertToMultipartFile(byteArrayInputStream, "user_data.xlsx");
        String owner = credentialService.getCredentialInfor();
        return storageServiceClient.uploadPrivateFile(multipartFile, false, "1", owner).getBody();
    }
}
