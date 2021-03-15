package com.interviewee.OrderFullfillmentOptimizer.service;

import com.interviewee.OrderFullfillmentOptimizer.model.Location;
import com.interviewee.OrderFullfillmentOptimizer.repository.BaseRepository;
import com.interviewee.OrderFullfillmentOptimizer.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService extends BaseService<Location>{

    private LocationRepository locRepository;

    public LocationService(LocationRepository repository) {

        super("Location", repository);
        this.locRepository = repository;
    }
}
