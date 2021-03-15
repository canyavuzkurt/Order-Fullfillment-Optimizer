package com.interviewee.OrderFullfillmentOptimizer.service;

import com.interviewee.OrderFullfillmentOptimizer.model.BaseEntity;
import com.interviewee.OrderFullfillmentOptimizer.repository.BaseRepository;

public class BaseService<T extends BaseEntity> extends ModelService<T, Long> {

    private BaseRepository<T> repository;

    public BaseService(String resourceName, BaseRepository<T> repository) {

        super(resourceName, repository);
        this.repository = repository;
    }
}
