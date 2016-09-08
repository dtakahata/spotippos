package br.com.spotippos.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.spotippos.jpa.model.Coordinates;
import br.com.spotippos.jpa.model.Property;
import br.com.spotippos.jpa.model.Province;
import br.com.spotippos.service.PropertyService;
import br.com.spotippos.service.ProvinceService;

@RestController
@Validated
public class SpotipposController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ProvinceService provinceService;

    @RequestMapping(path = "/properties", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Property saveProperty(@RequestBody Property property) {
        return propertyService.save(property);
    }

    @RequestMapping(path = "/properties/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Property> properties(@PathVariable Integer id) {
        Property property = propertyService.findById(id);
        ResponseEntity<Property> responseEntity = new ResponseEntity<Property>(property,
                property == null || property.getId() == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(path = "/properties", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<PropertyResponse> properties(@Valid Coordinates coordinates) {
        List<Property> properties = propertyService.findByCoordinates(coordinates);
        ResponseEntity<PropertyResponse> responseEntity = new ResponseEntity<PropertyResponse>(
                new PropertyResponse(properties),
                properties == null || properties.size() == 0 ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(path = "/loadProperties", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody PropertyResponse loadProperties(@RequestBody PropertyRequest propertyRequest) {
        return new PropertyResponse(propertyService.save(propertyRequest.getProperties()));
    }

    @RequestMapping(path = "/listProperties", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Property>> properties() {
        return new ResponseEntity<List<Property>>(propertyService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(path = "/provinces", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Province provinces(@RequestBody Province property) {
        return provinceService.save(property);
    }

    @RequestMapping(path = "/provinces", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Province>> listProvinces() {
        return new ResponseEntity<List<Province>>(provinceService.findAll(), HttpStatus.OK);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResponseEntity<Void> handleResourceNotFoundException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Void> onValidationException(BindException e, HttpServletResponse response)
            throws IOException {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
