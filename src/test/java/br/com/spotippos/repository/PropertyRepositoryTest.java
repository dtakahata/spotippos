package br.com.spotippos.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.spotippos.jpa.model.Property;
import br.com.spotippos.utils.PropertyUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/data.sql")
public class PropertyRepositoryTest {

    @After
    public void after() {
        propertyRepository.deleteAll();
    }

    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    public void testFindsAll() throws IOException {
        List<Property> propertiesInserted = (List<Property>) this.propertyRepository
                .save(PropertyUtils.createProperties(100));
        List<Property> properties = (List<Property>) this.propertyRepository.findAll();

        assertThat(properties).hasSize(100);
        assertThat(properties).doesNotContainNull();
        assertThat(properties).containsAll(propertiesInserted);
    }

    @Test
    public void testFindsById() throws IOException {
        Property propertyResult = this.propertyRepository.save(PropertyUtils.createProperty());
        Property property = this.propertyRepository.findOneById(propertyResult.getId());
        assertThat(property).isNotNull();
        assertThat(property).isEqualTo(propertyResult);
    }

    @Test
    public void testFindPropertiesByCordinatesWithExactResult() throws IOException {
        List<Property> propertiesInserted = (List<Property>) this.propertyRepository
                .save(PropertyUtils.createProperties(100));
        int latitude = propertiesInserted.get(25).getLatitude();
        int longitude = propertiesInserted.get(25).getLongitude();
        List<Property> properties = this.propertyRepository.findPropertiesByCordinates(latitude, longitude, latitude,
                longitude);
        assertThat(properties).isNotNull();
        assertThat(properties).hasSize(1);
        assertThat(properties.get(0).getLatitude()).isEqualTo(latitude);
        assertThat(properties.get(0).getLongitude()).isEqualTo(longitude);
    }

    @Test
    public void testFindPropertiesByCordinatesWithManyResults() throws IOException {
        this.propertyRepository.save(PropertyUtils.createProperties(1000));
        List<Property> properties = this.propertyRepository.findPropertiesByCordinates(700, 1000, 1400, 500);
        assertThat(properties).isNotNull();
        for (Property property : properties) {
            assertThat(property.getLatitude()).isBetween(700, 1400);
            assertThat(property.getLongitude()).isBetween(500, 1000);
        }
    }

}
