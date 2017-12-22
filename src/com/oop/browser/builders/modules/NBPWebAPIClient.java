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

/**
 * Client for web service available at <href>http://api.nbp.pl/</href>
 */
public class NBPWebAPIClient {

    /**
     *
     * @param URLs array of url addresses which need to be requested
     * @return array of received JSONs
     * @throws InvalidArgumentsException in case of inappropriate parameters in url
     * @throws DataNotFoundException in case of lack of information for given parameters
     * @throws IOException in any other case
     */
    public String[] sendRequest(String[] URLs) throws InvalidArgumentsException, DataNotFoundException, IOException {

        ArrayList<String> result = new ArrayList<>();

        for (String URL : URLs) {
            String line = "";
            while (line.equals("") || line.contains("<")) {
                HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setRequestProperty("Accept-Charset", "utf-8");

                int responseCode = connection.getResponseCode();
                switch (responseCode) {
                    case 200:
                        InputStream response = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
                        line = reader.readLine();
                        result.add(line);
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
        }

        return result.toArray(new String[result.size()]);
    }
}
