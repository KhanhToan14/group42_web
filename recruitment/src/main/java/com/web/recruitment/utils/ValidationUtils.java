package com.web.recruitment.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Slf4j
public class ValidationUtils {
    private ValidationUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean validateRegex(String content, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(content)
                .matches();
    }

    public static boolean validateEmail(String emailAddress) {
        return validateRegex(emailAddress, Constants.EMAIL_REGEX);
    }

    public static boolean validatePassword(String password) {
        return validateRegex(password, Constants.PASSWORD_REGEX);
    }

    public static String validateVietnamesePhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim();
        if (validateRegex(phoneNumber, Constants.PHONE_NUMBER_10_REGEX) || validateRegex(phoneNumber, Constants.PHONE_NUMBER_11_REGEX)) {
            return phoneNumber;
        }

        if (validateRegex(phoneNumber, Constants.PHONE_NUMBER_84_REGEX)) {
            return phoneNumber.replace("+84", "0");
        }
        return null;
    }

    public static boolean validateBase64(String base64String) {
        var decoder = Base64.getDecoder();
        try {
            decoder.decode(base64String);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
    public static boolean validateDateForm(String date) {
        String[] parts = date.split("-");

        if (parts.length != 3) return false;

        else {
            if (!isNumeric(parts[0]) || !isNumeric(parts[1]) || !isNumeric(parts[2])) return false;
            else if (parts[0].length() > 5 || parts[1].length() > 2 || parts[2].length() > 2) return false;
            else {
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                ValueRange yearRange = ValueRange.of(1000, 9999);
                ValueRange monthRange = ValueRange.of(1, 12);
                ValueRange dayRange;
                if (!yearRange.isValidIntValue(year) || !monthRange.isValidIntValue(month)) return false;
                else {
                    List<Integer> thirtyOne = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
                    List<Integer> thirty = Arrays.asList(4, 6, 9, 11);
                    if (thirtyOne.contains(month)) {
                        dayRange = ValueRange.of(1, 31);
                    } else if (thirty.contains(month)) {
                        dayRange = ValueRange.of(1, 30);
                    } else {
                        if (year % 4 == 0) {
                            if (year % 100 == 0 && year % 400 != 0) {
                                dayRange = ValueRange.of(1, 28);
                            } else {
                                dayRange = ValueRange.of(1, 29);
                            }
                        } else {
                            dayRange = ValueRange.of(1, 28);
                        }
                    }
                    return dayRange.isValidIntValue(day);
                }
            }
        }
    }

    public static String formatDate(String stringDate) throws ParseException {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static boolean compareToCurrentDate(String date) {
        LocalDate localDate = LocalDate.now();
        LocalDate inputDate = LocalDate.parse(date);
        return inputDate.compareTo(localDate) <= 0;
    }

    public static String autoCorrectFormatName(String name) {
        if(name == null){
            return null;
        }
        name = name.trim();
        StringBuilder stringBuilder = new StringBuilder(name);
        for (int i = name.length() - 1; i > 0; i--)
            if (name.charAt(i) == ' ' && name.charAt(i - 1) == ' ') stringBuilder.deleteCharAt(i);
        return stringBuilder.toString();
    }
}
