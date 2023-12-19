package com.web.recruitment.utils;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Constants Utility class");
    }

    public static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~!@#\\$%\\^\\*\\-_=\\+\\[\\{\\]\\}\\/;:,\\.\\?])[A-Za-z\\d~!@#\\$%\\^\\*-_=\\+\\[\\{\\]\\}\\/;:,\\.\\?]{8,64}$";

    public static final String PHONE_NUMBER_10_REGEX = "^0[\\d]{9}$";

    public static final String PHONE_NUMBER_11_REGEX = "^02[\\d]{9}$";

    public static final String PHONE_NUMBER_84_REGEX = "^\\+84[\\d]{9}$";

    public static final long MAX_IMAGE_SIZE_IN_BYTES = 524880; // 5MB = 5*1024*1024 = 524880 bytes

    public static final int DEFAULT_PAGE_SIZE = 30;

    public static final int DEFAULT_CURRENT_PAGE = 1;
}
