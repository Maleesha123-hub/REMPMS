package com.pdev.rempms.adminservice.util;

/**
 * @author maleeshasa
 * @Date 16/12/2023
 */
public class CommonValidation {

    public static boolean stringNullValidation(String inputString) {
        return inputString == null || inputString.isEmpty();
    }
}
