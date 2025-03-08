package com.newapp.bookEntry.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "weather_configuration")
@Data
@NoArgsConstructor
public class WeatherConfiguration {
    private String key;
    private String value;

}
