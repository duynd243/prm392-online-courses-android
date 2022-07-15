package com.mmc.utils;

public class ValidationUtils {
    public static final int MAX_LENGTH_FULL_NAME = 10;
    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidFullName(String fullName) {
        return fullName.length() < MAX_LENGTH_FULL_NAME;
    }

}