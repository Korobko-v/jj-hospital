package ru.levelp.hospital.exception;

public enum ServerErrorCode {
    EMPTY_FIELD("The field can't be empty"),
    SHORT_PASSWORD("The password is too short"),
    WRONG_PASSWORD("The password should contain both lower case chars and upper case chars (or reserved chars/digits"),
    USER_EXISTS("This user is already registered"),
    USER_DOESNT_EXIST("The user doesn't exist"),
    NULL_FIELD("The field can't be null");

    private String errorString;

    private ServerErrorCode(String errorString){

        this.errorString = errorString;
    }

    private String getErrorString() {
        return errorString;
    }

}
