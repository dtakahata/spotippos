package br.com.spotippos.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.spotippos.jpa.model.Property;

public class PropertyRequest {
    private int totalProperties;
    private List<Property> properties;

    public PropertyRequest() {
        properties = new ArrayList<>();
    }

    public PropertyRequest(List<Property> properties) {
        super();
        this.properties = properties;

    }

    public int getTotalProperties() {
        return totalProperties;
    }

    public void setTotalProperties(int totalProperties) {
        this.totalProperties = totalProperties;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

}
