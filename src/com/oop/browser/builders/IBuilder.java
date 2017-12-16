package com.oop.browser.builders;

import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public interface IBuilder {

    public IBuilder setURL(String[] options);

    public IBuilder sendRequest() throws IOException, InvalidArgumentsException, DataNotFoundException;

    public ArrayList<Serializable[]> buildSerializable(String typeValue) throws IOException;

}
