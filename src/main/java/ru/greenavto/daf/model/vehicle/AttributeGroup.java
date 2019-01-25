package ru.greenavto.daf.model.vehicle;

import java.util.ArrayList;
import java.util.List;

public class AttributeGroup {


    private Description description;

    private List<Attribute> attributes;

    private String id;

    public AttributeGroup() {
        attributes = new ArrayList<>();
    }

    public AttributeGroup(Description description, List<Attribute> attributes, String id) {
        this.description = description;
        this.attributes = attributes;
        this.id = id;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AttributeGroup{" +
                "description=" + description +
                ",attributes=" + attributes +
                ",id='" + id + '\'' +
                '}';
    }
}
