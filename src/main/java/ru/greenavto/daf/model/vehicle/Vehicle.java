package ru.greenavto.daf.model.vehicle;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {

    private List<AttributeGroup> attributeGroups;

    private String vehicleName;

    private String vehicleSerie;

    public Vehicle() {
        attributeGroups = new ArrayList<>();
    }

    public Vehicle(List<AttributeGroup> attributeGroups, String vehicleName, String vehicleSerie) {
        this.attributeGroups = attributeGroups;
        this.vehicleName = vehicleName;
        this.vehicleSerie = vehicleSerie;
    }

    public List<AttributeGroup> getAttributeGroups() {
        return attributeGroups;
    }

    public void setAttributeGroups(List<AttributeGroup> attributeGroups) {
        this.attributeGroups = attributeGroups;
    }

    public void addAttributeGroup(AttributeGroup attributeGroup) {
        attributeGroups.add(attributeGroup);
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleSerie() {
        return vehicleSerie;
    }

    public void setVehicleSerie(String vehicleSerie) {
        this.vehicleSerie = vehicleSerie;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "attributeGroups=" + attributeGroups +
                ", vehicleName='" + vehicleName + '\'' +
                ", vehicleSerie='" + vehicleSerie + '\'' +
                '}';
    }
}
