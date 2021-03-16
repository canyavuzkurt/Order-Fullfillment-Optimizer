package com.interviewee.OrderFullfillmentOptimizer.controller.api;

import com.interviewee.OrderFullfillmentOptimizer.model.Product;
import com.interviewee.OrderFullfillmentOptimizer.payload.response.MessageResponse;
import com.interviewee.OrderFullfillmentOptimizer.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController extends BaseController<Product>{

    ProductService productService;

    public ProductController(ProductService service) {

        super(service);
        productService = service;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable(name = "id") Long id) {

        return super.getEntity(id);
    }

    @PostMapping("")
    public MessageResponse addProduct(@RequestBody Product product) {

        return super.addEntity(product);
    }
}
