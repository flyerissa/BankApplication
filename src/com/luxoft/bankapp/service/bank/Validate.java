package com.luxoft.bankapp.service.bank;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validate {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PHONE_NUMBER_REGEX =
            Pattern.compile("^(\\+38)?(\\(?0\\d{2}\\)?[\\- ]?)?\\d{3}[\\- ]?\\d{2}[\\- ]?\\d{2}$");

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePhone(String phoneString) {
        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(phoneString);
        return matcher.find();
    }

}
