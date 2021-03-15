package com.interviewee.OrderFullfillmentOptimizer.service;

import com.interviewee.OrderFullfillmentOptimizer.model.Product;
import com.interviewee.OrderFullfillmentOptimizer.repository.BaseRepository;
import com.interviewee.OrderFullfillmentOptimizer.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<Product>{

    ProductRepository productRepository;

    public ProductService(ProductRepository repository) {

        super("Product", repository);
        productRepository = repository;
    }
}
