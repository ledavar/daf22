package ru.greenavto.daf.dto;

public class VehicleRequestDto {

    private String searchedVin;

    public VehicleRequestDto(String searchedVin) {
        this.searchedVin = searchedVin;
    }

    public String getSearchedVin() {
        return searchedVin;
    }



}
