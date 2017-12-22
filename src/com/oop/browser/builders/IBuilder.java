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
     *
     * @return itself
     * @throws IOException
     * @throws InvalidArgumentsException
     * @throws DataNotFoundException
     */
    public IBuilder sendRequest() throws IOException, InvalidArgumentsException, DataNotFoundException;

    /**
     *
     * @param typeValue is key for specific POJO from serializable package
     * @return itself
     * @throws IOException
     */
    public IBuilder buildSerializable(String typeValue) throws IOException;

}
