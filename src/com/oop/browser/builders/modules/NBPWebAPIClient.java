package com.oop.browser.builders.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.stream.Collectors;

public class NBPWebAPIClient {

    public String[] sendRequest(String[] URL) {

        return Arrays.stream(URL).map(x ->
                {
                    String line = null;
                    try {
                        URLConnection connection = new URL(x).openConnection();
                        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                        connection.setRequestProperty("Accept-Charset", "utf-8");
                        InputStream response = connection.getInputStream();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
                        line = reader.readLine();

                        reader.close();
                        response.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return line;
                }
        ).collect(Collectors.toList()).toArray(new String[URL.length]);

    }
}
