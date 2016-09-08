package br.com.spotippos.service;

import java.util.List;

import br.com.spotippos.jpa.model.Coordinates;
import br.com.spotippos.jpa.model.Property;

public interface PropertyService {

    Property save(Property property);

    List<Property> save(List<Property> properties);

    List<Property> findAll();

    Property findById(int id);

    List<Property> findByCoordinates(Coordinates coordinates);
}
