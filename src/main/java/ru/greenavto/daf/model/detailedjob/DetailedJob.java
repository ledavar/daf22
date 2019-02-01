package ru.greenavto.daf.model.detailedjob;

import java.util.List;

public class DetailedJob {

    private String title;
    private boolean hasPartsViewer;
    private String tooltip;
    private String key;
    private String componentRef;
    private String componentRefVersion;
    private int duration;
    private String durationTime;
    private boolean isFolder;
    private boolean isExpanded;
    private Boolean isLazy;
    private List<DetailedJob> children;
    private PartConsumption partConsumption;

    public DetailedJob() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasPartsViewer() {
        return hasPartsViewer;
    }

    public void setHasPartsViewer(boolean hasPartsViewer) {
        this.hasPartsViewer = hasPartsViewer;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComponentRef() {
        return componentRef;
    }

    public void setComponentRef(String componentRef) {
        this.componentRef = componentRef;
    }

    public String getComponentRefVersion() {
        return componentRefVersion;
    }

    public void setComponentRefVersion(String componentRefVersion) {
        this.componentRefVersion = componentRefVersion;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean folder) {
        isFolder = folder;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public Boolean getLazy() {
        return isLazy;
    }

    public void setLazy(Boolean lazy) {
        isLazy = lazy;
    }

    public List<DetailedJob> getChildren() {
        return children;
    }

    public void setChildren(List<DetailedJob> children) {
        this.children = children;
    }

    public PartConsumption getPartConsumption() {
        return partConsumption;
    }

    public void setPartConsumption(PartConsumption partConsumption) {
        this.partConsumption = partConsumption;
    }
}
