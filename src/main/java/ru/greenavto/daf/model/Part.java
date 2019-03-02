package ru.greenavto.daf.model;

import com.google.gson.annotations.SerializedName;

// https://eportal.daf.com/rweb/DAFRMI/partsViewer/GetPartsListData/
// ?compDrawingIndex=1
// &compNumber=1943534
// &compVersion=14
// &vinNumber=XLRTEH4300G124720
// &compGroup=1407
// &skipSplitComponentsFiltering=false
// &languageCode=RU
public class Part {

    @SerializedName("Number")
    private int number;
    @SerializedName("Description")
    private String description;
    @SerializedName("Quantity")
    private String quantity;
    @SerializedName("Position")
    private String position;
    @SerializedName("HasInformation")
    private boolean hasInformation;
    @SerializedName("HasBeenSuperseeded")
    private boolean hasBeenSuperseeded;
    @SerializedName("HasTrpPart")
    private boolean hasTrpPart;
    @SerializedName("HasExchangePart")
    private boolean hasExchangePart;
    @SerializedName("Information")
    private String[] information;
    @SerializedName("Supersession")
    private String[] superSession;
    @SerializedName("Trp")
    private String[] trp;
    @SerializedName("Exchange")
    private String[] exchange;
    @SerializedName("HasJobs")
    private boolean hasJobs;
    @SerializedName("HasTechnicalData")
    private boolean hasTechnicalData;
    @SerializedName("IsCleanPart")
    private boolean isCleanPart;
    @SerializedName("IsDeliverable")
    private boolean isDeliverable;
    @SerializedName("IsObsolete")
    private boolean isObsolete;
    @SerializedName("PartsBulletins")
    private String partsBulletins;
    @SerializedName("ExchangePart")
    private Part exchangePart;
    @SerializedName("LinkLDrawingPartNo")
    private String linkLDrawingPartNo;
    @SerializedName("HasLDraw")
    private boolean hasLDraw;
    @SerializedName("PartsClass")
    private String partsClass;
    @SerializedName("TechnicalDataModules")
    private String technicalDataModules;
    @SerializedName("JobsVerbs")
    private String jobsVers;
    @SerializedName("InStock")
    private int inStock;

    public Part() {
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPosition() {
        return position;
    }

    public boolean isHasInformation() {
        return hasInformation;
    }

    public boolean isHasBeenSuperseeded() {
        return hasBeenSuperseeded;
    }

    public boolean isHasTrpPart() {
        return hasTrpPart;
    }

    public boolean isHasExchangePart() {
        return hasExchangePart;
    }

    public String[] getInformation() {
        return information;
    }

    public String[] getSuperSession() {
        return superSession;
    }

    public String[] getTrp() {
        return trp;
    }

    public String[] getExchange() {
        return exchange;
    }

    public boolean isHasJobs() {
        return hasJobs;
    }

    public boolean isHasTechnicalData() {
        return hasTechnicalData;
    }

    public boolean isCleanPart() {
        return isCleanPart;
    }

    public boolean isDeliverable() {
        return isDeliverable;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public String getPartsBulletins() {
        return partsBulletins;
    }

    public Part getExchangePart() {
        return exchangePart;
    }

    public String getLinkLDrawingPartNo() {
        return linkLDrawingPartNo;
    }

    public boolean isHasLDraw() {
        return hasLDraw;
    }

    public String getPartsClass() {
        return partsClass;
    }

    public String getTechnicalDataModules() {
        return technicalDataModules;
    }

    public String getJobsVers() {
        return jobsVers;
    }

    public int getInStock() {
        return inStock;
    }
}
