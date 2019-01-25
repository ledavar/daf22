package ru.greenavto.daf.model.vehicle;

public class Attribute {


    private String value;

    private int securityLevel;

    private String id;

    private String name;

    public Attribute() {
    }

    public Attribute(String value, int securityLevel, String id, String name) {
        this.value = value;
        this.securityLevel = securityLevel;
        this.id = id;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "value='" + value + '\'' +
                ", securityLevel=" + securityLevel +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
