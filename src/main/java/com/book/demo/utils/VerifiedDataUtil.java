package com.book.demo.utils;

import java.util.regex.Pattern;

public class VerifiedDataUtil {
    public static final String getVerifiedEmail(String email) {
        boolean isMatch = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
            .matcher(email)
            .find();

        if (!isMatch) {
            throw new IllegalArgumentException(String.format("Email is not valid, %s.", email));
        } else {
            return email;
        }
    }

    public static final String getVerifiedPhoneNumber(String phoneNumber) {
        boolean isMatch = Pattern.compile("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$")
            .matcher(phoneNumber)
            .find();

        if (!isMatch) {
            throw new IllegalArgumentException(String.format("Phone number is not valid, %s", phoneNumber));
        } else {
            return phoneNumber;
        }
    }

    public static final String getVerifiedPassword(String password) {
        boolean isMatch = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
            .matcher(password)
            .find();
        
        if (!isMatch) {
            throw new IllegalArgumentException(String.format("Password is not valid, %s", password));
        } else {
            return password;
        }
    }
}
