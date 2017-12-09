package com.oop.browser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oop.browser.builders.TableBuilder;
import com.oop.browser.serializable.Table;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Main {

    public static void main(String[] args) {

        try {

            ObjectMapper objectMapper = new ObjectMapper();

            TableBuilder tableBuilder = new TableBuilder();

            Serializable table = tableBuilder.generateURL(args).sendRequest().buildSerializable();

            // http://api.nbp.pl/api/exchangerates/tables/c/?format=json
            // http://api.nbp.pl/api/cenyzlota/?format=json

            URLConnection connection = new URL("http://api.nbp.pl/api/exchangerates/tables/c/?format=json").openConnection();
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            InputStream response = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));

            String line = reader.readLine();

            Table[] tables = objectMapper.readValue(line, Table[].class);

            System.out.println();

        } catch (IOException e) {

            e.printStackTrace();
        }


    }
}
