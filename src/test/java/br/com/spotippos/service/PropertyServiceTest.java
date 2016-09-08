package br.com.spotippos.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.spotippos.jpa.model.Coordinates;
import br.com.spotippos.jpa.model.Property;
import br.com.spotippos.repository.PropertyRepository;
import br.com.spotippos.utils.PropertyUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PropertyServiceTest {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ProvinceService provinceService;
    
    @Autowired
    private PropertyRepository propertyRepository;
    
    @After
    public void after() {
        propertyRepository.deleteAll();
    }
   
    @Test
    public void testSave() {
        Property property = this.propertyService.save(PropertyUtils.createProperty());
        assertThat(property).isNotNull();
        assertThat(property.getId()).isNotNull();
        assertThat(property.getProvinces())
                .containsAll(provinceService.findProvincesByXY(property.getLatitude(), property.getLongitude()));
    }

    @Test
    public void testSaveList() {
        List<Property> propertiesResult = this.propertyService.save(PropertyUtils.createProperties(50));
        assertThat(propertiesResult).isNotNull();
        assertThat(propertiesResult).doesNotContainNull();
        for (Property property : propertiesResult) {
            assertThat(property.getProvinces())
                    .containsAll(provinceService.findProvincesByXY(property.getLatitude(), property.getLongitude()));
        }
    }

    @Test
    public void testFindsAll() throws IOException {
        List<Property> propertiesResult = this.propertyService.save(PropertyUtils.createProperties(50));
        List<Property> properties = (List<Property>) this.propertyService.findAll();
        assertThat(properties).isNotNull();
        assertThat(properties).doesNotContainNull();
        assertThat(properties).containsAll(propertiesResult);
    }

    @Test
    public void testFindsById() throws IOException {
        Property propertyResult = this.propertyService.save(PropertyUtils.createProperty());
        Property property = this.propertyService.findById(propertyResult.getId());
        assertThat(property).isNotNull();
        assertThat(property).isEqualTo(propertyResult);
    }

    @Test
    public void testFindPropertiesByCordinatesWithManyResults() throws IOException {
        this.propertyService.save(PropertyUtils.createProperties(1000));
        Coordinates coordinates = new Coordinates(700, 1000, 1400, 500);
        List<Property> properties = this.propertyService.findByCoordinates(coordinates);
        assertThat(properties).isNotNull();
        for (Property property : properties) {
            assertThat(property.getLatitude()).isBetween(coordinates.getAx(), coordinates.getBx());
            assertThat(property.getLongitude()).isBetween(coordinates.getBy(), coordinates.getAy());
        }
    }
}
