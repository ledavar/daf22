package ru.greenavto.daf.dto;

public class JobRequestDto {

    private String vin;
    private String compNumber;
    private String compNumberVersion;
    private String componentGroup;
    private String drawingIndex;

    public JobRequestDto() {
    }

    public JobRequestDto(String vin, String compNumber, String compNumberVersion, String componentGroup, String drawingIndex) {
        this.vin = vin;
        this.compNumber = compNumber;
        this.compNumberVersion = compNumberVersion;
        this.componentGroup = componentGroup;
        this.drawingIndex = drawingIndex;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getCompNumber() {
        return compNumber;
    }

    public void setCompNumber(String compNumber) {
        this.compNumber = compNumber;
    }

    public String getCompNumberVersion() {
        return compNumberVersion;
    }

    public void setCompNumberVersion(String compNumberVersion) {
        this.compNumberVersion = compNumberVersion;
    }

    public String getComponentGroup() {
        return componentGroup;
    }

    public void setComponentGroup(String componentGroup) {
        this.componentGroup = componentGroup;
    }

    public String getDrawingIndex() {
        return drawingIndex;
    }

    public void setDrawingIndex(String drawingIndex) {
        this.drawingIndex = drawingIndex;
    }
}
