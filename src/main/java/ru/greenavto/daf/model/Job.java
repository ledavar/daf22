package ru.greenavto.daf.model;

public class Job {

    private String title;
    private String tooltip;
    private String key;

    public Job() {
    }

    public Job(String title, String tooltip, String key) {
        this.title = title;
        this.tooltip = tooltip;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return "Job{" +
                "title='" + title + '\'' +
                ", tooltip='" + tooltip + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
