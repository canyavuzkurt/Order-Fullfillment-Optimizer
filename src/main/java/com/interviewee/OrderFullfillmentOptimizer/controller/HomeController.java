package com.interviewee.OrderFullfillmentOptimizer.controller;

import com.interviewee.OrderFullfillmentOptimizer.model.Location;
import com.interviewee.OrderFullfillmentOptimizer.model.Product;
import com.interviewee.OrderFullfillmentOptimizer.service.LocationService;
import com.interviewee.OrderFullfillmentOptimizer.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
@Slf4j
public class HomeController {

    final ProductService productService;
    final LocationService locService;


    public HomeController(ProductService productService, LocationService locService) {

        this.productService = productService;
        this.locService = locService;
    }

    @GetMapping("")
    public String homePage(Model model) {

        List<Product> all = productService.findAll();
        model.addAttribute("products", all);
        List<Location> locs = locService.findAll();
        model.addAttribute("locations", locs);
        log.info("I'm in");
        return "home";
    }
}
