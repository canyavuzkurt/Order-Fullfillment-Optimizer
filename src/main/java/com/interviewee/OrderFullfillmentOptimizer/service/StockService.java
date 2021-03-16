package com.interviewee.OrderFullfillmentOptimizer.service;

import com.interviewee.OrderFullfillmentOptimizer.model.Stock;
import com.interviewee.OrderFullfillmentOptimizer.repository.BaseRepository;
import com.interviewee.OrderFullfillmentOptimizer.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService extends BaseService<Stock>{

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {

        super("Stock", stockRepository);
        this.stockRepository = stockRepository;
    }
}
