package ru.greenavto.daf.dto;

public class MainGroupRequestDto {

    private String searchedVin;

    public MainGroupRequestDto(String searchedVin) {
        this.searchedVin = searchedVin;
    }

    public String getSearchedVin() {
        return searchedVin;
    }

}


