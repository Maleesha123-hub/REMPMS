package com.pdev.rempms.documentservice.controller;

import com.pdev.rempms.documentservice.dto.excelSheet.CvExcelDTO;
import com.pdev.rempms.documentservice.service.ExcelGenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/document/v1/excel-generate")
public class ExcelGenerateController {

    private final ExcelGenerateService excelGenerateService;

    @GetMapping(value = "/cv-sheet")
    public ResponseEntity<InputStreamResource> createCvDetailsExcelSheet(@RequestPart(value = "cvExcelRequest") List<CvExcelDTO> cvExcelRequest) throws IOException {

        try {
            InputStreamResource inputStreamResource = excelGenerateService.createCvDetailsExcelSheet(cvExcelRequest);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Disposition", "attachment; filename=");
            httpHeaders.setContentType(new MediaType("application",
                    "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

            return ResponseEntity.ok().headers(httpHeaders)
                    .body(inputStreamResource);

        } catch (IOException ex) {
            throw new IOException("ExcelGenerateController -> createCvDetailsExcelSheet() => error {} " + ex.getMessage());
        }
    }

}
