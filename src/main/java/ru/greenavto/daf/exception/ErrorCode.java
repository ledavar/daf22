package ru.greenavto.daf.exception;

public enum ErrorCode {

    WRONG_VIN("Cant get VIN by code %s."),
    WRONG_ENCODING("Request body encoding is invalid"),
    NO_LOGIN("Cant perform login request: %s"),
    NO_PROPERTIES("Cant get properties fils %s");


    private String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
