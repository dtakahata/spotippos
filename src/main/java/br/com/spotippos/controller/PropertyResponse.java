package br.com.spotippos.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.spotippos.jpa.model.Property;

public class PropertyResponse {
    private int foundProperties;
    private List<Property> properties;

    public PropertyResponse() {
        properties = new ArrayList<>();
    }

    public PropertyResponse(List<Property> properties) {
        super();
        this.properties = properties;
        this.foundProperties = properties.size();
    }

    public int getFoundProperties() {
        return foundProperties;
    }

    public List<Property> getProperties() {
        return properties;
    }

}
