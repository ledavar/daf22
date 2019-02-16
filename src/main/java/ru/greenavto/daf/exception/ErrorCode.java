package ru.greenavto.daf.exception;

public enum ErrorCode {

    WRONG_VIN("Cant get VIN by code %s."),
    WRONG_ENCODING("Request body encoding is invalid"),
    WRONG_URI_SYNTAX("Wrong URI syntax: %s"),
    EXECUTE_REQUEST_ERROR("Error during error execution or recieving response body"),
    NO_LOGIN("Cant perform login request: %s"),
    BAD_RESPONSE("Received response with code %d. URI: %s"),
    NO_PROPERTIES("Cant get properties fils %s");


    private String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
