package com.oop.browser.serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;

public class Gold implements Serializable {


    private Date date;
    private Double price;

    @JsonProperty("data")
    public Date getDate() {
        return date;
    }

    @JsonProperty("data")
    public void setDate(Date date) {
        this.date = date;
    }

    @JsonProperty("cena")
    public Double getPrice() {
        return price;
    }

    @JsonProperty("cena")
    public void setPrice(Double price) {
        this.price = price;
    }

}
