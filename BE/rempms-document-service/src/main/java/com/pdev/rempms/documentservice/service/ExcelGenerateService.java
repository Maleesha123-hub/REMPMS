package com.pdev.rempms.documentservice.service;

import com.pdev.rempms.documentservice.dto.excelSheet.CvExcelDTO;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.util.List;

public interface ExcelGenerateService {


    InputStreamResource createCvDetailsExcelSheet(List<CvExcelDTO> cvExcelRequest) throws IOException;
}
