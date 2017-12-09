package com.oop.browser.serializable;

import java.io.Serializable;

public class Gold implements Serializable {

    private String data;
    private Double cena;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

}
