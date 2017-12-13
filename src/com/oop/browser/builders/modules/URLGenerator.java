package com.oop.browser.builders.modules;

public class URLGenerator {

    public String[] generateURL(String[] args) {

        // http://api.nbp.pl/api/cenyzlota/?format=json
        // http://api.nbp.pl/api/exchangerates/tables/c/?format=json

        return new String[]{"http://api.nbp.pl/api/cenyzlota/?format=json"};

    }

}
