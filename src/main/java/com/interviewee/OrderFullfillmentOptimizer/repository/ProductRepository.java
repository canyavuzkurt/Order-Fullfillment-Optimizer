package com.interviewee.OrderFullfillmentOptimizer.repository;

import com.interviewee.OrderFullfillmentOptimizer.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product>{

    public List<Product> findByName(String name);
}
