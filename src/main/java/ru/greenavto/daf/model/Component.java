package ru.greenavto.daf.model;

public class Component {

    private String id;
    private String mainGroupId;
    private String componentReference;
    private String version;
    private String description;
    private String index; // "drawingIndex" parameter in URI

    public Component(String id, String mainGroupId, String componentReference, String version, String description, String index) {
        this.id = id;
        this.mainGroupId = mainGroupId;
        this.componentReference = componentReference;
        this.version = version;
        this.description = description;
        this.index = index;
    }

    public Component() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMainGroupId() {
        return mainGroupId;
    }

    public void setMainGroupId(String mainGroupId) {
        this.mainGroupId = mainGroupId;
    }

    public String getComponentReference() {
        return componentReference;
    }

    public void setComponentReference(String componentReference) {
        this.componentReference = componentReference;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Component{" +
                "id='" + id + '\'' +
                ", mainGroupId='" + mainGroupId + '\'' +
                ", componentReference='" + componentReference + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", index='" + index + '\'' +
                '}';
    }
}

