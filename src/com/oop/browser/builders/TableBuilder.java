package com.oop.browser.builders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oop.browser.builders.modules.NBPWebAPIClient;
import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import com.oop.browser.serializable.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Concrete implementation of builder design pattern
 */
public class TableBuilder implements IBuilder {

    public String[] URL;
    public String[] JSON;
    public ArrayList<Serializable[]> serializable;

    @Override
    public TableBuilder setURL(String[] urls) {
        URL = urls;
        return this;
    }

    @Override
    public TableBuilder sendRequest() throws IOException, InvalidArgumentsException, DataNotFoundException {
        NBPWebAPIClient nbpWebAPIClient = new NBPWebAPIClient();
        JSON = nbpWebAPIClient.sendRequest(URL);
        return this;
    }

    @Override
    public TableBuilder buildSerializable(String typeValue) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<Serializable[]> serializableArrayList = new ArrayList<>();

        for (String JSON : JSON) {
            switch (typeValue) {
                case "Gold":
                    serializableArrayList.add(objectMapper.readValue(JSON, Gold[].class));
                    break;
                case "Tables":
                    serializableArrayList.add(objectMapper.readValue(JSON, Table[].class));
                    break;
                case "Table":
                    serializableArrayList.add(new Table[]{objectMapper.readValue(JSON, Table.class)});
                    break;
            }
        }

        serializable = serializableArrayList;
        return this;
    }
}
