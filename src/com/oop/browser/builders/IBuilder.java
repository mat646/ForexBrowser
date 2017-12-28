package com.oop.browser.builders;

import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import java.io.IOException;

/**
 * Interface for builder design pattern
 */
public interface IBuilder {

    /**
     * Used to set instance state with ulrs set
     * @param urls needed to be queried
     * @return itself
     */
    public IBuilder setURL(String[] urls);

    /**
     * Transforms collection of urls into JSONs by sending requests
     * @return itself
     * @throws IOException in case of connection error
     * @throws InvalidArgumentsException in case of inappropriate passed arguments (i.e. invalid date format)
     * @throws DataNotFoundException on not finding data for given date
     */
    public IBuilder sendRequest() throws IOException, InvalidArgumentsException, DataNotFoundException;

    /**
     * Creates POJO from JSON
     * @param typeValue is key for specific POJO from serializable package
     * @return itself
     * @throws IOException in case of invalid .class form for serializable creator
     */
    public IBuilder buildSerializable(String typeValue) throws IOException;

}
