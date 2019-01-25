package ru.greenavto.daf.model.vehicle;

public class Description {


    private String title;

    public Description() {
    }

    public Description(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Description{" +
                "title='" + title + '\'' +
                '}';
    }
}
