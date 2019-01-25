package ru.greenavto.daf.exception;

public class DafException extends Exception {

    public DafException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    public DafException(ErrorCode errorCode, String param) {
        super(String.format(errorCode.getMessage(), param));
    }

}
