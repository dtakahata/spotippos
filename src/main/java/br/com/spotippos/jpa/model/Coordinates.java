package br.com.spotippos.jpa.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Coordinates {

    public Coordinates() {
        super();
    }

    public Coordinates(Integer ax, Integer ay, Integer bx, Integer by) {
        super();
        this.ax = ax;
        this.ay = ay;
        this.bx = bx;
        this.by = by;
    }

    @NotNull
    @Min(0)
    @Max(1400)
    private Integer ax;

    @NotNull
    @Min(0)
    @Max(1000)
    private Integer ay;

    @NotNull
    @Min(0)
    @Max(1400)
    private Integer bx;

    @NotNull
    @Min(0)
    @Max(1000)
    private Integer by;

    public Integer getAx() {
        return ax;
    }

    public void setAx(Integer ax) {
        this.ax = ax;
    }

    public Integer getAy() {
        return ay;
    }

    public void setAy(Integer ay) {
        this.ay = ay;
    }

    public Integer getBx() {
        return bx;
    }

    public void setBx(Integer bx) {
        this.bx = bx;
    }

    public Integer getBy() {
        return by;
    }

    public void setBy(Integer by) {
        this.by = by;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
