package com.interviewee.OrderFullfillmentOptimizer.controller;

import com.interviewee.OrderFullfillmentOptimizer.model.Product;
import com.interviewee.OrderFullfillmentOptimizer.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
@Slf4j
public class ProductController extends BaseController<Product>{

    ProductService productService;

    public ProductController(ProductService service) {

        super(service);
        productService = service;
    }

    @GetMapping("/product-page")
    public String productPage(Model model) {

        model.addAttribute("name", "heyo");
        List<Product> all = productService.findAll();
        model.addAttribute("products", all);
        log.info("I'm in");
        return "home";
    }
}
