package ru.greenavto.daf.model.detailedjob;

public class PartConsumption {

    private String partNumber;
    private String partsClass;
    private String partDescription;
    private String cGroupNumber;
    private String cGroupDescription;
    private String quantity;
    private boolean isOptional;

    public PartConsumption() {
    }

    public PartConsumption(String partNumber, String partsClass, String partDescription, String cGroupNumber, String cGroupDescription, String quantity, boolean isOptional) {
        this.partNumber = partNumber;
        this.partsClass = partsClass;
        this.partDescription = partDescription;
        this.cGroupNumber = cGroupNumber;
        this.cGroupDescription = cGroupDescription;
        this.quantity = quantity;
        this.isOptional = isOptional;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartsClass() {
        return partsClass;
    }

    public void setPartsClass(String partsClass) {
        this.partsClass = partsClass;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public String getcGroupNumber() {
        return cGroupNumber;
    }

    public void setcGroupNumber(String cGroupNumber) {
        this.cGroupNumber = cGroupNumber;
    }

    public String getcGroupDescription() {
        return cGroupDescription;
    }

    public void setcGroupDescription(String cGroupDescription) {
        this.cGroupDescription = cGroupDescription;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public void setOptional(boolean optional) {
        isOptional = optional;
    }

}
