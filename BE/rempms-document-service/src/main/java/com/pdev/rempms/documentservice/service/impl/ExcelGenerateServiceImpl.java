package com.pdev.rempms.documentservice.service.impl;

import com.pdev.rempms.documentservice.dto.excelSheet.CvExcelDTO;
import com.pdev.rempms.documentservice.service.ExcelGenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ExcelGenerateServiceImpl implements ExcelGenerateService {

    @Override
    public InputStreamResource createCvDetailsExcelSheet(List<CvExcelDTO> cvExcelRequest) throws IOException {

        // Columns
        String[] columns = {"Count", "Name", "Email", "Description", "Cv url"};

        // Create a ne work book object
        Workbook workbook = new XSSFWorkbook();

        // Create a sheet
        Sheet sheet = workbook.createSheet("Cvs Details");

        // Format header values
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLUE.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create headers
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create rows with cv data
        int rowNum = 1;
        for (CvExcelDTO cvExcelDTO : cvExcelRequest) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 1);
            row.createCell(1).setCellValue(cvExcelDTO.getName());
            row.createCell(2).setCellValue(cvExcelDTO.getEmail());
            row.createCell(3).setCellValue(cvExcelDTO.getDescription());
            row.createCell(4).setCellValue(cvExcelDTO.getCvUrl());
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        byteArrayOutputStream.close();
        InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);
        byteArrayInputStream.close();

        return inputStreamResource;
    }

}
