package ru.greenavto.daf.dto;

public class DetailedJobRequestDto {

    private String vin;
    //private String languageCode;
    private String jobId;

    public DetailedJobRequestDto(String vin, /*String languageCode,*/ String jobId) {
        this.vin = vin;
      //  this.languageCode = languageCode;
        this.jobId = jobId;
    }

    public String getVin() {
        return vin;
    }

    /*public String getLanguageCode() {
        return languageCode;
    }*/

    public String getJobId() {
        return jobId;
    }

}
