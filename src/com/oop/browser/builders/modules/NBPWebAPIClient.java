package com.oop.browser.builders.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class NBPWebAPIClient {


    public String[] sendRequest(String[] URL) throws IOException {

        URLConnection connection = new URL(URL[0]).openConnection();
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Accept-Charset", "utf-8");
        InputStream response = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));

        String line = reader.readLine();

        reader.close();
        response.close();

        return new String[]{line};
    }
}
