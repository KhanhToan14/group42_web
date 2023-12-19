package com.web.recruitment.utils;

public class ConstantMessages {
    // list method message
    public static final String PAGE_SIZE = "pageSize";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final String KEYWORD = "keyword";
    public static final String TOTAL = "total";
    public static final String DATA = "data";
    public static final String SORT_TYPE = "sortType";
    public static final String SORT_BY = "sortBy";
    public static final String TIME = "time";
    public static final String DESC = "desc";
    public static final String ASC = "asc";
    //

    public static final String MESSAGE = "message";
    public static final String NAME = "name";
    public static final String NAME_MUST_NOT_NULL = "NameMustNotNull";
    public static final String NAME_EXIST = "NameExist";
    public static final String ID = "id";
    public static final String ID_MUST_NOT_NULL = "IdMustNotNull";
    public static final String ID_NOT_EXIST_ERROR = "IdNotExist";
    public static final String INVALID_INPUT_MESSAGE = "InvalidInput";
    public static final String ERRORS = "errors";
    public static final String DELETE_IDS = "deleteIds";
    public static final String DELETE_IDS_MUST_NOT_NULL_OR_EMPTY = "DeleteIdsMustNotNullOrEmpty";
    public static final String LIST_ID_CAN_NOT_DELETE = "listIdCanNotDelete";
    public static final String LIST_ID_DELETED = "listIdDeleted";
    public static final String IDS_INVALID = "IdsInvalid";
    public static final String NOT_FOUND_MESSAGE = "NotFound";
    public static final String SOME_THING_WENT_WRONG_MESSAGE = "SomeThingWentWrong";

    //
    public static final String DEPARTMENT = "department";
    public static final String SUCCESS_INSERT_DEPARTMENT = "SuccessInsertDepartment";
    public static final String SUCCESS_UPDATE_DEPARTMENT = "SuccessUpdateDepartment";
    public static final String SUCCESS_DELETE_DEPARTMENT = "SuccessDeleteDepartment";
    //
    public static final String COMPANY = "company";
    public static final String SUCCESS_INSERT_COMPANY = "SuccessInsertCompany";
    public static final String SUCCESS_UPDATE_COMPANY = "SuccessUpdateCompany";
    public static final String SUCCESS_DELETE_COMPANY = "SuccessDeleteCompany";
    public static final String CODE = "code";
    public static final String CODE_MUST_NOT_NULL = "CodeMustNotNull";
    public static final String CODE_NOT_EXIST_ERROR = "CodeNotExist";
    public static final String WEBSITE = "website";
    public static final String WEBSITE_INVALID = "WebsiteInvalid";
    public static final String WEBSITE_NOT_EXIST_ERROR = "WebsiteNotExist";
    public static final String LOGO = "logo";
    public static final String LOGO_INVALID_ERROR = "LogoMustBeInBase64FormatAndSizeLessThan512KBAndIsOneOfTheFollowingTypesPngJpgJpegSvg";
    public static final String LOGO_INVALID_SIZE_ERROR = "LogoSizeMustBeSmallerOrEqualTo512KB";
    public static final String PHONE = "phone";
    public static final String AVATAR = "avatar";
    public static final String BANNER = "banner";
    public static final String PHONE_INVALID = "PhoneInvalid";
    public static final String PHONE_NOT_NULL = "PhoneNotNull";
    public static final String EMAIL = "email";
    public static final String EMAIL_NOT_NULL = "EmailNotNull";
    public static final String EMAIL_INVALID = "EmailInvalid";
    public static final String EMAIL_EXIST = "EmailExist";

    //
    public static final String DEPARTMENT_ID = "departmentId";
    public static final String DEPARTMENT_NOT_FOUND = "DepartmentNotFound";
    public static final String COMPANY_ID = "companyId";
    public static final String COMPANY_ID_NOT_EXIST = "CompanyIdNotExist";
    public static final String COMPANY_NOT_FOUND = "CompanyNotFound";
    public static final String SALARY = "salary";
    public static final String SALARY_INVALID = "SalaryInvalid";
    public static final String JOB = "job";
    public static final String EMPLOYMENT_TYPE = "employmentType";
    public static final String EXPERIENCE = "experience";
    public static final String SALARY_FROM = "salaryFrom";
    public static final String SALARY_TO = "salaryTo";
    public static final String CURRENCY = "currency";
    public static final String SUCCESS_INSERT_JOB = "SuccessInsertJob";
    public static final String SUCCESS_UPDATE_JOB = "SuccessUpdateJob";
    public static final String SUCCESS_DELETE_JOB = "SuccessDeleteJob";

    //
    public static final String USER = "user";
    public static final String USERNAME = "username";
    public static final String USERNAME_MUST_NOT_NULL = "UsernameMustNotNull";
    public static final String USERNAME_EXIST = "UsernameExist";
    public static final String USERNAME_INVALID_ERROR = "UsernameMustContainOnlyLowercaseCharacterNumber";
    public static final String FIRST_NAME = "firstName";
    public static final String FIRST_NAME_MUST_NOT_NULL = "FirstNameMustNotNull";
    public static final String FIRSTNAME_INVALID_ERROR = "FirstNameMustContainOnlyAlphabetCharacterNumberWhiteSpace";
    public static final String LAST_NAME = "lastName";
    public static final String LAST_NAME_MUST_NOT_NULL = "LastNameMustNotNull";
    public static final String LASTNAME_INVALID_ERROR = "LastNameMustContainOnlyAlphabetCharacterNumberWhiteSpace";
    public static final String DATE_OF_BIRTH = "dateOfBirth";
    public static final String DATE_OF_BIRTH_NOT_NULL_ERROR = "DateOfBirthMustNotNull";
    public static final String DATE_OF_BIRTH_MUST_BEFORE_NOW_ERROR = "DateOfBirthMustBeBeforeNow";
    public static final String DATE_OF_BIRTH_INVALID_FORMAT_ERROR = "InvalidDateInput";
    public static final String PASSWORD = "password";
    public static final String PASSWORD_NOT_NULL_ERROR = "PasswordMustNotNull";
    public static final String PASSWORD_INVALID_ERROR = "PasswordInvalid";
    public static final String SUCCESS_INSERT_USER = "SuccessInsertUser";
    public static final String SUCCESS_UPDATE_USER = "SuccessUpdateUser";
    public static final String SUCCESS_DELETE_USER = "SuccessDeleteUser";
    public static final String ROLE = "role";
    public static final String DEAL_TIME = "dealTime";
    public static final String DEAL_TIME_NOT_NULL_ERROR = "DealTimeMustNotNull";
    public static final String DEAL_TIME_MUST_AFTER_NOW_ERROR = "DealTimeMustBeAfterNow";
    public static final String DEAL_TIME_INVALID_FORMAT_ERROR = "InvalidDealTimeInput";

    //
    public static final String FILE_NAME = "fileName";
    public static final String FILE_NAME_NOT_NULL = "FileNameNotNull";
    public static final String SUCCESS_STORE_FILE = "SuccessStoreFile";
    public static final String FILE_INVALID = "FileInvalid";
    public static final String FILE_INCORRECT_FORMAT = "FileIncorrectFormat";
    public static final String FILE_EXTENSION = "fileExtension";
    public static final String FILE_SIZE = "fileSize";
    public static final String CV = "CV";

    //
    public static final String APPLICANT_FORM = "applicationForm";
    public static final String JOB_ID = "jobId";
    public static final String USER_ID = "userId";
    public static final String SUCCESS_INSERT_APPLICANT_FORM = "SuccessInsertApplicantForm";
    public static final String SUCCESS_UPDATE_APPLICANT_FORM = "SuccessUpdateApplicantForm";
    public static final String SUCCESS_DELETE_APPLICANT_FORM = "SuccessDeleteApplicantForm";

    //
    public static final String USER_EMAIL_REGISTERED_INACTIVE_ERROR = "EmailRegisteredButInactivate";
    public static final String LOCKED_TIME = "lockedTime";
    public static final String SUCCESS = "success";
    public static final String LOCKED = "locked";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String INVALID_CREDENTIALS_ERROR = "InvalidCredentials";
    public static final String OTP = "otp";
    public static final String AUTH_SUCCESS_REGISTER_USER = "SuccessRegisterUser";
    public static final String OTP_NOT_NULL = "OtpNotNull";
    public static final String EMAIL_NOT_REGISTERED = "EmailNotRegistered";
    public static final String OTP_DEACTIVATED = "OTPDeactivated";
    public static final String USER_REQUEST_NEW_OTP = "PleaseRequestToReceiveNewOTP";
    public static final String OTP_EXPIRED = "OTPExpired";
    public static final String PASSWORD_INCORRECT = "PasswordIncorrect";
    public static final String LOGIN_SUCCESS = "LoginSuccess";
    public static final String AUTH_SUCCESS_ACTIVATE_USER = "SuccessActivateUser";
    public static final String TOKEN_NOT_NULL = "TokenMustNotNull";
    public static final String TOKEN = "token";
    public static final String OTP_ALREADY_SENT_MESSAGE = "OtpAlreadySent";
    public static final String OTP_TIME_SENT = "otpTimeSent";
    public static final String NEW_OTP_GENERATED_MESSAGE = "NewOtpGenerated";
    public static final String AUTH_SUCCESS_LOGOUT = "SuccessLogout";
    public static final String LOGIN_NAME = "loginName";
    public static final String LOGIN_NAME_INVALID = "LoginNameInvalid";
    public static final String YOU_CAN_NOT_USE_THIS_FUNCTION = "YouCanNotUseThisFunction";
    public static final String CURRENT_PASSWORD = "currentPassword";
    public static final String CURRENT_PASSWORD_NOT_NULL = "CurrentPasswordMustNotNull";
    public static final String CURRENT_PASSWORD_INCORRECT = "CurrentPasswordIncorrect";
    public static final String NEW_PASSWORD = "newPassword";
    public static final String NEW_PASSWORD_NOT_NULL= "NewPasswordMustNotNull";
    public static final String NEW_PASSWORD_MUST_NOT_SAME_CURRENT_PASSWORD= "NewPasswordAndCurrentPasswordShouldNotBeSame";
    public static final String CONFIRM_NEW_PASSWORD = "confirmNewPassword";
    public static final String CONFIRM_NEW_PASSWORD_NOT_NULL = "ConfirmNewPasswordMustNotBeNull";
    public static final String CONFIRM_AND_NEW_PASSWORD_NOT_SAME = "ConfirmNewPasswordAndNewPasswordMustBeSame";
    public static final String SUCCESS_CHANGE_PASSWORD = "SuccessChangeUserPassword";
}
