package com.oop.browser.builders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oop.browser.builders.modules.NBPWebAPIClient;
import com.oop.browser.builders.modules.URLGenerator;
import com.oop.browser.serializable.Table;

import java.io.IOException;
import java.io.Serializable;

public class TableBuilder implements IBuilder {

    public String URL;
    public String JSON;
    public Serializable serializable;

    @Override
    public TableBuilder generateURL(String[] options) {

        URLGenerator urlGenerator = new URLGenerator();
        URL = urlGenerator.generateURL(options);
        return this;
    }

    @Override
    public TableBuilder sendRequest() throws IOException {

        NBPWebAPIClient nbpWebAPIClient = new NBPWebAPIClient();
        JSON = nbpWebAPIClient.sendRequest(URL);
        return this;
    }

    public Serializable buildSerializable() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Table[] tables = objectMapper.readValue(JSON, Table[].class);
        serializable = tables;
        return serializable;
    }
}
