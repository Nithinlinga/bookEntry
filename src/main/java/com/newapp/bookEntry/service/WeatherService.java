package com.newapp.bookEntry.service;


import com.newapp.bookEntry.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class WeatherService {
    private static final String apikey="f46e4ea18951abbdb90ccb30fd064c2b";

    private static String API="https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalApi=API.replace("API_KEY",apikey).replace("CITY",city);

        ResponseEntity<WeatherResponse> responseRequestEntity=restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class);
        WeatherResponse body=responseRequestEntity.getBody();
        return body;
    }
}
