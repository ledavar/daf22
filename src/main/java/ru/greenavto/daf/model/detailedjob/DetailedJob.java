package ru.greenavto.daf.model.detailedjob;

import java.util.ArrayList;
import java.util.List;

public class DetailedJob {

    private String title;
    private boolean hasPartsViewer;
    private String tooltip;
    private String key;
    private String componentRef;
    private String componentRefVersion;
    private double duration;
    private String durationTime;
    private boolean isFolder;
    private boolean isExpanded;
    private Boolean isLazy;
    private List<DetailedJob> children;
    private List<PartConsumption> partConsumptions;
    private List<String> specialTools;

    public DetailedJob() {
    }

    public void addChild(DetailedJob child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public void addPartConsumption(PartConsumption partConsumption) {
        if (partConsumptions == null) {
            partConsumptions = new ArrayList<>();
        }
        partConsumptions.add(partConsumption);
    }

    public void addSpecialTool(String tool) {
        if (specialTools == null) {
            specialTools = new ArrayList<>();
        }
        specialTools.add(tool);
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

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
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

    public List<PartConsumption> getPartConsumptions() {
        return partConsumptions;
    }

    public void setPartConsumptions(List<PartConsumption> partConsumptions) {
        this.partConsumptions = partConsumptions;
    }

    public List<String> getSpecialTools() {
        return specialTools;
    }

    public void setSpecialTools(List<String> specialTools) {
        this.specialTools = specialTools;
    }
}
