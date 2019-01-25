package ru.greenavto.daf.dto;

public class ComponentRequestDto {

    private String searchedVin;
    private String mainGroupId;

    public ComponentRequestDto(String searchedVin, String mainGroupId) {
        this.searchedVin = searchedVin;
        this.mainGroupId = mainGroupId;
    }

    public String getSearchedVin() {
        return searchedVin;
    }

    public String getMainGroupId() {
        return mainGroupId;
    }
}
