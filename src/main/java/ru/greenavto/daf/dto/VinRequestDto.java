package ru.greenavto.daf.dto;

public class VinRequestDto {

    private String code;

    public VinRequestDto(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
