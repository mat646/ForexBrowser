package com.oop.browser.builders;

public interface IBuilder {

    public IBuilder generateURL(String[] options);

    public IBuilder sendRequest();

}
