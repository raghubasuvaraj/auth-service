package com.eatco.authservice.enums;

public enum AuthConstants {

    FAILURE("Failure"),
    SUCCESS("Success"),
    RESET_MAIL_SUCCESS("Reset password email sent successfully"),
    RESET_SUCCESS("Password reset success"),
    FCM_TOKEN_SUCCESS("Fcm token added successfully"),
    SAVE_PERMISSIONS_SUCCESS("User permission saved successfully"),
    UPDATE_SUCCESS("Updated successfully"),
    PASSWORD_MAIL_SUCCESS("Password email sent successfully"),
    VERIFIED_SUCCESS("Verification success");

    private String value;

    AuthConstants(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
