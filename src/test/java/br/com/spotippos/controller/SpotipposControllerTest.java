package br.com.spotippos.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.spotippos.jpa.model.Property;
import br.com.spotippos.jpa.model.Province;
import br.com.spotippos.utils.PropertyUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpotipposControllerTest {

    private static final int LESS_THAN_ZERO = -1;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testPostProperties() throws IOException {
        ResponseEntity<Property> responseEntity = saveProperty();
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(responseEntity.getBody()).isNotNull();
        validateProperty(responseEntity.getBody());
    }

    @Test
    public void testPostPropertiesWithLatLessThanZero() throws IOException {
        Property property = PropertyUtils.createProperty().withLatitude(LESS_THAN_ZERO);
        ResponseEntity<Property> responseEntity = saveProperty(property);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPostPropertiesWithLatGreaterThanOneThousandFourHundred() throws IOException {
        Property property = PropertyUtils.createProperty().withLatitude(1401);
        ResponseEntity<Property> responseEntity = saveProperty(property);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPostPropertiesWithLongLessThanZero() throws IOException {
        Property property = PropertyUtils.createProperty().withLongitude(LESS_THAN_ZERO);
        ResponseEntity<Property> responseEntity = saveProperty(property);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPostPropertiesWithLongGreaterThanOneThousand() throws IOException {
        Property property = PropertyUtils.createProperty().withLongitude(1001);
        ResponseEntity<Property> responseEntity = saveProperty(property);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPostPropertiesWithBedsLessThanOne() throws IOException {
        Property property = PropertyUtils.createProperty().withBeds(0);
        ResponseEntity<Property> responseEntity = saveProperty(property);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPostPropertiesWithBedsGreaterThanFive() throws IOException {
        Property property = PropertyUtils.createProperty().withBeds(6);
        ResponseEntity<Property> responseEntity = saveProperty(property);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPostPropertiesWithBathsLessThanOne() throws IOException {
        Property property = PropertyUtils.createProperty().withBaths(0);
        ResponseEntity<Property> responseEntity = saveProperty(property);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPostPropertiesWithBathsGreaterThanFour() throws IOException {
        Property property = PropertyUtils.createProperty().withBaths(5);
        ResponseEntity<Property> responseEntity = saveProperty(property);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPostPropertiesWithSquareMetersLessThanTwenty() throws IOException {
        Property property = PropertyUtils.createProperty().withSquareMeters(19);
        ResponseEntity<Property> responseEntity = saveProperty(property);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPostPropertiesWithSquareMetersGreaterThanTwoHundredForty() throws IOException {
        Property property = PropertyUtils.createProperty().withSquareMeters(241);
        ResponseEntity<Property> responseEntity = saveProperty(property);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testLoadProperties() throws IOException {
        HttpEntity<String> request = createRequest(readFile("src/test/resources/properties.json"));
        ResponseEntity<PropertyResponse> responseEntity = this.restTemplate.postForEntity("/loadProperties", request,
                PropertyResponse.class);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getFoundProperties()).isGreaterThan(0);
        assertThat(responseEntity.getBody().getProperties()).doesNotContainNull();
        for (Property property : responseEntity.getBody().getProperties()) {
            validateProperty(property);
        }
    }

    @Test
    public void testGetProperties() throws IOException {
        HttpEntity<String> request = createRequest(readFile("src/test/resources/properties.json"));
        this.restTemplate.postForEntity("/loadProperties", request, PropertyResponse.class);
        ResponseEntity<Property[]> responseEntity = this.restTemplate.getForEntity("/listProperties", Property[].class);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(responseEntity.getBody()).doesNotContainNull();
    }

    @Test
    public void testGetPropertyById() throws IOException {
        ResponseEntity<Property> responseEntitySaved = saveProperty();
        ResponseEntity<Property> responseEntity = this.restTemplate.getForEntity("/properties/{id}", Property.class,
                responseEntitySaved.getBody().getId());
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(responseEntity.getBody()).isEqualToComparingFieldByField(responseEntitySaved.getBody());
    }

    @Test
    public void testGetPropertyByIdButNotFound() throws IOException {
        ResponseEntity<Property> responseEntity = this.restTemplate.getForEntity("/properties/{id}", Property.class,
                999999999);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetPropertyByCoordinates() throws IOException {
        ResponseEntity<Property> responseEntitySaved = saveProperty();
        ResponseEntity<PropertyResponse> responseEntity = this.restTemplate.getForEntity(
                "/properties?ax={ax}&ay={ay}&bx={bx}&by={by}", PropertyResponse.class,
                responseEntitySaved.getBody().getLatitude(), responseEntitySaved.getBody().getLongitude(),
                responseEntitySaved.getBody().getLatitude(), responseEntitySaved.getBody().getLongitude());
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(responseEntity.getBody().getProperties().get(0))
                .isEqualToComparingFieldByField(responseEntitySaved.getBody());
    }

    @Test
    public void testGetPropertyByCoordinatesWithAxLessThanZero() throws IOException {
        ResponseEntity<PropertyResponse> responseEntity = this.restTemplate.getForEntity(
                "/properties?ax={ax}&ay={ay}&bx={bx}&by={by}", PropertyResponse.class, LESS_THAN_ZERO, 0, 0, 0);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetPropertyByCoordinatesWithAyLessThanZero() throws IOException {
        ResponseEntity<PropertyResponse> responseEntity = this.restTemplate.getForEntity(
                "/properties?ax={ax}&ay={ay}&bx={bx}&by={by}", PropertyResponse.class, 0, LESS_THAN_ZERO, 0, 0);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetPropertyByCoordinatesWithBxLessThanZero() throws IOException {
        ResponseEntity<PropertyResponse> responseEntity = this.restTemplate.getForEntity(
                "/properties?ax={ax}&ay={ay}&bx={bx}&by={by}", PropertyResponse.class, 0, 0, LESS_THAN_ZERO, 0);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetPropertyByCoordinatesWithByLessThanZero() throws IOException {
        ResponseEntity<PropertyResponse> responseEntity = this.restTemplate.getForEntity(
                "/properties?ax={ax}&ay={ay}&bx={bx}&by={by}", PropertyResponse.class, 0, 0, 0, LESS_THAN_ZERO);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetPropertyByCoordinatesWithAxGreaterThanOneThousandFourHundred() throws IOException {
        ResponseEntity<PropertyResponse> responseEntity = this.restTemplate.getForEntity(
                "/properties?ax={ax}&ay={ay}&bx={bx}&by={by}", PropertyResponse.class, 1401, 0, 0, 0);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetPropertyByCoordinatesWithAyGreaterThanOneThousand() throws IOException {
        ResponseEntity<PropertyResponse> responseEntity = this.restTemplate.getForEntity(
                "/properties?ax={ax}&ay={ay}&bx={bx}&by={by}", PropertyResponse.class, 0, 1001, 0, 0);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetPropertyByCoordinatesWithBxGreaterThanOneThousandFourHundred() throws IOException {
        ResponseEntity<PropertyResponse> responseEntity = this.restTemplate.getForEntity(
                "/properties?ax={ax}&ay={ay}&bx={bx}&by={by}", PropertyResponse.class, 0, 0, 1401, 0);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetPropertyByCoordinatesWithByGreaterThanOneThousand() throws IOException {
        ResponseEntity<PropertyResponse> responseEntity = this.restTemplate.getForEntity(
                "/properties?ax={ax}&ay={ay}&bx={bx}&by={by}", PropertyResponse.class, 0, 0, 0, 1001);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPostProvince() throws IOException {
        HttpEntity<String> request = createRequest(readFile("src/test/resources/province.json"));
        ResponseEntity<Province> responseEntity = this.restTemplate.postForEntity("/provinces", request,
                Province.class);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isGreaterThan(0);
    }

    @Test
    public void testGetProvinces() {
        ResponseEntity<Province[]> responseEntity = this.restTemplate.getForEntity("/provinces", Province[].class);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(responseEntity.getBody()).doesNotContainNull();
    }

    private ResponseEntity<Property> saveProperty() throws IOException {
        HttpEntity<String> request = createRequest(readFile("src/test/resources/property.json"));
        ResponseEntity<Property> responseEntity = this.restTemplate.postForEntity("/properties", request,
                Property.class);
        return responseEntity;
    }

    private ResponseEntity<Property> saveProperty(Property property) throws IOException {
        HttpEntity<String> request = createRequest(MAPPER.writeValueAsString(property));
        ResponseEntity<Property> responseEntity = this.restTemplate.postForEntity("/properties", request,
                Property.class);
        return responseEntity;
    }

    private HttpEntity<String> createRequest(String json) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(json, headers);
        return request;
    }

    private void validateProperty(Property property) {
        assertThat(property.getId()).isGreaterThan(0);
        assertThat(property.getBaths()).isBetween(1, 4);
        assertThat(property.getBeds()).isBetween(1, 5);
        assertThat(property.getLatitude()).isBetween(0, 1400);
        assertThat(property.getLongitude()).isBetween(0, 1000);
        assertThat(property.getSquareMeters()).isBetween(20, 240);
        assertThat(property.getDescription()).isNotEmpty();
        assertThat(property.getTitle()).isNotEmpty();
        assertThat(property.getPrice()).isNotNull();
    }

    private String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

}
