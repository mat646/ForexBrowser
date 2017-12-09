package com.oop.browser.builders;

import java.io.IOException;

public interface IBuilder {

    public IBuilder generateURL(String[] options);

    public IBuilder sendRequest() throws IOException;

}
