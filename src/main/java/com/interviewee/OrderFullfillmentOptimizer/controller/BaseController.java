package com.interviewee.OrderFullfillmentOptimizer.controller;


import com.interviewee.OrderFullfillmentOptimizer.model.BaseEntity;
import com.interviewee.OrderFullfillmentOptimizer.payload.response.MessageResponse;
import com.interviewee.OrderFullfillmentOptimizer.service.BaseService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BaseController<T extends BaseEntity> {

    private BaseService<T> service;

    public T getEntity(Long id) {

        return service.findById(id);
    }

    public MessageResponse addEntity(T entity) {

        entity.setId(null);
        service.save(entity);
        return new MessageResponse(service.getResourceName() + " added successfully.");
    }

    public MessageResponse putEntity(T entity) {

        service.save(entity);
        return new MessageResponse(service.getResourceName() + " edited successfully.");
    }

    public MessageResponse deleteEntity(Long id) {

        service.deleteById(id);
        return new MessageResponse(service.getResourceName() + " deleted successfully.");
    }
}
