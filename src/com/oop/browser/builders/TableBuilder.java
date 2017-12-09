package com.oop.browser.builders;

import com.oop.browser.builders.modules.NBPWebAPIClient;
import com.oop.browser.builders.modules.URLGenerator;

import java.io.Serializable;

public class TableBuilder implements IBuilder {

    public String URL;
    public Serializable serializable;

    @Override
    public TableBuilder generateURL(String[] options) {

        URLGenerator urlGenerator = new URLGenerator();
        URL = null;
        return this;
    }

    @Override
    public TableBuilder sendRequest() {

        NBPWebAPIClient nbpWebAPIClient = new NBPWebAPIClient();
        serializable = null;
        return this;
    }

    public Serializable buildSerializable() {
        return serializable;
    }
}
