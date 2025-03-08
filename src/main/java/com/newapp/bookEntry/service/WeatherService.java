package com.newapp.bookEntry.service;


import com.newapp.bookEntry.api.response.WeatherResponse;
import com.newapp.bookEntry.cache.AppCache;
import com.newapp.bookEntry.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class WeatherService {
    @Value("${weather_api_key}")
    private String apikey;


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {
        String finalApi=appCache.ConfigData.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolders.API_KEY,apikey).replace(PlaceHolders.CITY,city);

        ResponseEntity<WeatherResponse> responseRequestEntity=restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class);
        WeatherResponse body=responseRequestEntity.getBody();
        return body;
    }
}
