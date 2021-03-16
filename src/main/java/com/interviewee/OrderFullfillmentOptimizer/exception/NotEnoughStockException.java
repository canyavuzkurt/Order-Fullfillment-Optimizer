package com.interviewee.OrderFullfillmentOptimizer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException(String productName) {

        super("Not enough stock for product: " + productName);
    }
}
