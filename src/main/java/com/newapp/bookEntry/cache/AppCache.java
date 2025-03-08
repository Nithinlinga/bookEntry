package com.newapp.bookEntry.cache;

import com.newapp.bookEntry.Entity.WeatherConfiguration;
import com.newapp.bookEntry.repository.WeatherConfigurationRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public enum keys{
        WEATHER_API
    }

    @Autowired
    WeatherConfigurationRepository weatherConfigurationRepository;

    public Map<String,String> ConfigData;
    @PostConstruct
    public void  init(){
        ConfigData=new HashMap<>();
        List<WeatherConfiguration> all=weatherConfigurationRepository.findAll();
        for(WeatherConfiguration weatherConfiguration : all){
            ConfigData.put(weatherConfiguration.getKey(), weatherConfiguration.getValue());

        }
    }
}
