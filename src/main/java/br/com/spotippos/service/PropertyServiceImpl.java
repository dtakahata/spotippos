package br.com.spotippos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.spotippos.jpa.model.Coordinates;
import br.com.spotippos.jpa.model.Property;
import br.com.spotippos.jpa.model.Province;
import br.com.spotippos.repository.PropertyRepository;
import br.com.spotippos.repository.ProvinceRepository;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public Property save(Property property) {
        List<String> provinces = provinceRepository.findProvincesByXY(property.getLatitude(),
                property.getLongitude());
        property.getProvinces().addAll(provinces);
        return propertyRepository.save(property);
    }

    @Override
    public List<Property> save(List<Property> properties) {
        List<Province> provinces = (List<Province>) provinceRepository.findAll();
        for (Property property : properties) {
            for (Province province : provinces) {
                if (province.getUpperLeftX() <= property.getLatitude()
                        && province.getUpperLeftY() >= property.getLongitude()
                        && province.getBottomRightX() >= property.getLatitude()
                        && province.getBottomRightY() <= property.getLongitude()) {
                    property.getProvinces().add(province.getName());
                }

            }
        }
        return (List<Property>) propertyRepository.save(properties);
    }

    @Override
    public List<Property> findAll() {
        return (List<Property>) propertyRepository.findAll();
    }

    @Override
    public Property findById(int id) {
        return propertyRepository.findOneById(id);
    }

    @Override
    public List<Property> findByCoordinates(Coordinates coordinates) {
        return propertyRepository.findPropertiesByCordinates(coordinates.getAx(), coordinates.getAy(),
                coordinates.getBx(), coordinates.getBy());
    }

}
