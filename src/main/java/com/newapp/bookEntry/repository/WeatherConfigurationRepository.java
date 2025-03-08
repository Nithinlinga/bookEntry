package com.newapp.bookEntry.repository;

import com.newapp.bookEntry.Entity.WeatherConfiguration;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeatherConfigurationRepository extends MongoRepository<WeatherConfiguration, ObjectId> {
}
