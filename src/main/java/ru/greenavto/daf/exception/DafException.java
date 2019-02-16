package ru.greenavto.daf.exception;

public class DafException extends Exception {

    public DafException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    public DafException(ErrorCode errorCode, String param) {
        super(String.format(errorCode.getMessage(), param));
    }

    public DafException(ErrorCode errorCode, int param) {
        super(String.format(errorCode.getMessage(), param));
    }

    public DafException(ErrorCode errorCode, int param, String param1) {
        super(String.format(errorCode.getMessage(), param, param1));
    }

}
