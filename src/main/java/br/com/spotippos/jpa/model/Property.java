package br.com.spotippos.jpa.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title",
        "price",
        "description",
        "lat",
        "long",
        "beds",
        "baths",
        "squareMeters"
})
@Entity
public class Property {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("description")
    private String description;

    @NotNull
    @Min(0)
    @Max(1400)
    @JsonProperty("lat")
    private Integer latitude;

    @NotNull
    @Min(0)
    @Max(1000)
    @JsonProperty("long")
    private Integer longitude;

    @NotNull
    @Min(1)
    @Max(5)
    @JsonProperty("beds")
    private Integer beds;

    @NotNull
    @Min(1)
    @Max(4)
    @JsonProperty("baths")
    private Integer baths;

    @NotNull
    @Min(20)
    @Max(240)
    @JsonProperty("squareMeters")
    private Integer squareMeters;

    private ArrayList<String> provinces = new ArrayList<String>();

    public Property() {
    }

    public Property(Integer id, String title, Integer price, String description, Integer latitude, Integer longitude,
            Integer beds, Integer baths, Integer squareMeters) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.beds = beds;
        this.baths = baths;
        this.squareMeters = squareMeters;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Property withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public Property withTitle(String title) {
        this.title = title;
        return this;
    }

    @JsonProperty("price")
    public Integer getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Integer price) {
        this.price = price;
    }

    public Property withPrice(Integer price) {
        this.price = price;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Property withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("lat")
    public Integer getLatitude() {
        return latitude;
    }

    @JsonProperty("lat")
    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Property withLatitude(Integer latitude) {
        this.latitude = latitude;
        return this;
    }

    @JsonProperty("x")
    public void setX(Integer x) {
        this.latitude = x;
    }

    @JsonProperty("long")
    public Integer getLongitude() {
        return longitude;
    }

    @JsonProperty("long")
    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Property withLongitude(Integer longitude) {
        this.longitude = longitude;
        return this;
    }

    @JsonProperty("y")
    public void setY(Integer y) {
        this.longitude = y;
    }

    @JsonProperty("beds")
    public Integer getBeds() {
        return beds;
    }

    @JsonProperty("beds")
    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Property withBeds(Integer beds) {
        this.beds = beds;
        return this;
    }

    @JsonProperty("baths")
    public Integer getBaths() {
        return baths;
    }

    @JsonProperty("baths")
    public void setBaths(Integer baths) {
        this.baths = baths;
    }

    public Property withBaths(Integer baths) {
        this.baths = baths;
        return this;
    }

    @JsonProperty("squareMeters")
    public Integer getSquareMeters() {
        return squareMeters;
    }

    @JsonProperty("squareMeters")
    public void setSquareMeters(Integer squareMeters) {
        this.squareMeters = squareMeters;
    }

    public Property withSquareMeters(Integer squareMeters) {
        this.squareMeters = squareMeters;
        return this;
    }

    public ArrayList<String> getProvinces() {
        return provinces;
    }

    public void setProvinces(ArrayList<String> provinces) {
        this.provinces = provinces;
    }

    public Property withProvinces(ArrayList<String> provinces) {
        this.provinces = provinces;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Property) == false) {
            return false;
        }
        Property rhs = ((Property) other);
        return new EqualsBuilder().append(latitude, rhs.latitude).append(longitude, rhs.longitude)
                .append(title, rhs.title).append(price, rhs.price).append(description, rhs.description)
                .append(beds, rhs.beds).append(baths, rhs.baths).append(squareMeters, rhs.squareMeters).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}