package ru.greenavto.daf.model;

import com.google.gson.annotations.SerializedName;

public class Vin {
    
    private String result;
    private String wmi;

    public Vin(String result, String wmi) {
        this.result = result;
        this.wmi = wmi;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getWmi() {
        return wmi;
    }

    public void setWmi(String wmi) {
        this.wmi = wmi;
    }

    @Override
    public String toString() {
        return "Vin{" +
                "result='" + result + '\'' +
                ", wmi='" + wmi + '\'' +
                '}';
    }
}
