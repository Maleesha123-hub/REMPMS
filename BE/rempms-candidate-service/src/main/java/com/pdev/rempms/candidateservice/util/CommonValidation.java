package com.pdev.rempms.candidateservice.util;

public class CommonValidation {

    public static boolean stringNullValidation(String inputString) {
        return inputString == null || inputString.isEmpty();
    }

    public static boolean integerNullValidation(Integer inputInteger) {
        return inputInteger == null;
    }

}
