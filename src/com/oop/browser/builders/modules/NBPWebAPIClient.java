package com.oop.browser.builders.modules;

import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NBPWebAPIClient {

    public String[] sendRequest(String[] URLs) throws InvalidArgumentsException, DataNotFoundException, IOException {

        ArrayList<String> out = new ArrayList<>();

        for (String URL : URLs) {

            HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept-Charset", "utf-8");

            int responseCode = connection.getResponseCode();
            switch (responseCode) {
                case 200:
                    InputStream response = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
                    String line = reader.readLine();

                    out.add(line);

                    reader.close();
                    response.close();

                    break;
                case 400:
                    throw new InvalidArgumentsException();
                case 404:
                    throw new DataNotFoundException();
            }

            connection.disconnect();
        }

        return out.toArray(new String[out.size()]);
    }
}
