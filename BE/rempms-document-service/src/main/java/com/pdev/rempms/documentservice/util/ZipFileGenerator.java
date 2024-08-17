package com.pdev.rempms.documentservice.util;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class ZipFileGenerator {
    public void addFileToZip(ZipOutputStream zipOutputStream, String entryName, byte[] content) throws IOException {
        ZipEntry zipEntry = new ZipEntry(entryName);
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(content);
        zipOutputStream.closeEntry();
    }

    /**
     * Create Zip Generator
     *
     * @param path       - required for file location
     * @param outputPath - required for zip save location
     * @return success or failed response from zip file generator
     */
    public ByteArrayOutputStream fileToZip(String path, String outputPath) {
        log.info("ZipFileGenerator -> filesToZip() => started");

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

            ZipEntry zipEntry = new ZipEntry(path);
            zipOutputStream.putNextEntry(zipEntry);

            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fileInputStream.read(bytes)) >= 0) {
                zipOutputStream.write(bytes, 0, length);
            }

            zipOutputStream.closeEntry();
            fileInputStream.close();
            zipOutputStream.close();
            log.info("ZipFileGenerator -> filesToZip() => ended");
            return byteArrayOutputStream;

        } catch (IOException e) {

            log.info("ZipFileGenerator -> filesToZip() => failed");
            throw new IllegalArgumentException("Unable to compress zipOutputStream   ", e);

        }

    }

    /**
     * Zip compressing  For All Pdf Documents
     *
     * @param bytes           - required for zip create
     * @param zipOutputStream required for iterating zip create process
     * @return ZipOutputStream will be returned
     */
    public ZipOutputStream pdfToZip(byte[] bytes, ZipOutputStream zipOutputStream, String pdfName) {
        log.info("ZipFileGenerator -> pdfToZip() =>  started");
        try {
            zipOutputStream.putNextEntry(new ZipEntry(pdfName + ".pdf"));
            zipOutputStream.write(bytes);
            zipOutputStream.closeEntry();
            log.info("ZipFileGenerator -> pdfToZip() =>  process successes");
            return zipOutputStream;
        } catch (IOException ex) {
            log.info("ZipFileGenerator -> pdfToZip() =>  failed");
            throw new IllegalArgumentException("Unable to compress zipOutputStream   ", ex);

        }

    }

    public ZipOutputStream pdf(String filePath, ZipOutputStream zipOutputStream, String pdfName) {
        log.info("ZipFileGenerator -> pdfToZip() =>  started");
        try {
            byte[] pdfBytes = Files.readAllBytes(Paths.get(filePath));

            zipOutputStream.putNextEntry(new ZipEntry(pdfName + ".pdf"));
            zipOutputStream.write(pdfBytes);
            zipOutputStream.closeEntry();


            log.info("ZipFileGenerator -> pdfToZip() =>  process successes");
            return zipOutputStream;
        } catch (IOException ex) {
            log.info("ZipFileGenerator -> pdfToZip() =>  failed");
            throw new IllegalArgumentException("Unable to compress zipOutputStream   ", ex);

        }

    }
}
