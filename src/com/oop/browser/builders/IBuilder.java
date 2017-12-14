package com.oop.browser.builders;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public interface IBuilder {

    public IBuilder setURL(String[] options);

    public IBuilder sendRequest() throws IOException;

    public ArrayList<Serializable[]> buildSerializable(String typeValue) throws IOException;

}
