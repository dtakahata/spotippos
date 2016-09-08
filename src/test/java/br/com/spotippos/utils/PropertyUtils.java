package br.com.spotippos.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang.math.RandomUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.spotippos.controller.PropertyRequest;
import br.com.spotippos.jpa.model.Property;
import br.com.spotippos.repository.PropertyRepository;

public class PropertyUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static List<Property> createProperties(int x) {
        List<Property> properties = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            properties.add(PropertyUtils.createProperty());
        }
        return properties;
    }

    public static Property createProperty() {
        return new Property()
                .withBaths(ThreadLocalRandom.current().nextInt(1, 4))
                .withBeds(ThreadLocalRandom.current().nextInt(1, 5))
                .withDescription("description")
                .withLatitude(RandomUtils.nextInt(1400))
                .withLongitude(RandomUtils.nextInt(1000))
                .withPrice(RandomUtils.nextInt())
                .withSquareMeters(ThreadLocalRandom.current().nextInt(20, 240))
                .withTitle("title");
    }

    public static List<Property> loadProperties(PropertyRepository propertyRepository)
            throws IOException, JsonParseException, JsonMappingException {
        String json = readFile("src/test/resources/properties.json");
        PropertyRequest propertyRequest = MAPPER.readValue(json, PropertyRequest.class);
        List<Property> propertiesInserted = (List<Property>) propertyRepository
                .save(propertyRequest.getProperties());
        return propertiesInserted;
    }

    private static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}
