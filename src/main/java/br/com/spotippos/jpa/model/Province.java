package br.com.spotippos.jpa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class Province implements Serializable {

    private static final long serialVersionUID = -4474307385522357413L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    @Min(0)
    @Max(1400)
    private Integer upperLeftX;

    @NotNull
    @Min(0)
    @Max(1000)
    private Integer upperLeftY;

    @NotNull
    @Min(0)
    @Max(1400)
    private Integer bottomRightX;

    @NotNull
    @Min(0)
    @Max(1000)
    private Integer bottomRightY;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Province withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUpperLeftX() {
        return upperLeftX;
    }

    public void setUpperLeftX(Integer upperLeftX) {
        this.upperLeftX = upperLeftX;
    }

    public Integer getUpperLeftY() {
        return upperLeftY;
    }

    public void setUpperLeftY(Integer upperLeftY) {
        this.upperLeftY = upperLeftY;
    }

    public Integer getBottomRightX() {
        return bottomRightX;
    }

    public void setBottomRightX(Integer bottomRightX) {
        this.bottomRightX = bottomRightX;
    }

    public Integer getBottomRightY() {
        return bottomRightY;
    }

    public void setBottomRightY(Integer bottomRightY) {
        this.bottomRightY = bottomRightY;
    }

    public Province withName(String name) {
        setName(name);
        return this;
    }

    public Province withUpperLeftX(Integer upperLeftX) {
        setUpperLeftX(upperLeftX);
        return this;
    }

    public Province withUpperLeftY(Integer upperLeftY) {
        setUpperLeftY(upperLeftY);
        return this;
    }

    public Province withBottomRightX(Integer bottomRightX) {
        setBottomRightX(bottomRightX);
        return this;
    }

    public Province withBottomRightY(Integer bottomRightY) {
        setBottomRightY(bottomRightY);
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Province) == false) {
            return false;
        }
        Province rhs = ((Province) other);
        return new EqualsBuilder().append(name, rhs.name).append(upperLeftX, rhs.upperLeftX)
                .append(upperLeftY, rhs.upperLeftY).append(bottomRightX, rhs.bottomRightX)
                .append(bottomRightY, rhs.bottomRightY).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
