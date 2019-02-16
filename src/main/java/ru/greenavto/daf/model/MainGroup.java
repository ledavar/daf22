package ru.greenavto.daf.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MainGroup {

    @SerializedName("ID")
    private String id;
    @SerializedName("Description")
    private String description;
    private List<Component> componentList;

    public MainGroup(String id, String description) {
        this.id = id;
        this.description = description;
        componentList = new ArrayList<>();
    }

    public MainGroup(String id, String description, List<Component> componentList) {
        this(id, description);
        this.componentList = componentList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Component> getComponentList() {
        return componentList;
    }

    public void setComponentList(List<Component> componentList) {
        this.componentList = componentList;
    }

    public void addComponent(Component component) {
        componentList.add(component);
    }

    @Override
    public String toString() {
        return "MainGroup{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", componentList=" + componentList +
                '}';
    }
}
