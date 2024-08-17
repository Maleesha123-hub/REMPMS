package com.pdev.rempms.documentservice.util;

import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentFileName {

    public static String getFileName(String documentPath) {
        // Extract the file name with extension from the path
        String fileNameWithExtension = Paths.get(documentPath).getFileName().toString();

        // Regular expression to match trailing numbers
        Pattern pattern = Pattern.compile("^(.*?)(\\d*)$");
        Matcher matcher = pattern.matcher(fileNameWithExtension);

        if (matcher.matches()) {
            // Group 1 is the file name without trailing numbers
            return matcher.group(1);
        }
        throw new IllegalArgumentException("Document path is invalid."); // In case regex doesn't match, return the original file name
    }

}
