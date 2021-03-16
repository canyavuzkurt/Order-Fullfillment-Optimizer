package com.interviewee.OrderFullfillmentOptimizer.controller;

import com.interviewee.OrderFullfillmentOptimizer.model.Location;
import com.interviewee.OrderFullfillmentOptimizer.model.Product;
import com.interviewee.OrderFullfillmentOptimizer.service.LocationService;
import com.interviewee.OrderFullfillmentOptimizer.service.OrderService;
import com.interviewee.OrderFullfillmentOptimizer.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("")
@Slf4j
public class HomeController {

    final ProductService productService;
    final LocationService locService;
    final OrderService orderService;


    public HomeController(ProductService productService, LocationService locService, OrderService orderService) {

        this.productService = productService;
        this.locService = locService;
        this.orderService = orderService;
    }

    @GetMapping("")
    public String homePage(Model model, @RequestParam(name = "order", required = false) String order) {

        List<Product> all = productService.findAll();
        model.addAttribute("products", all);
        List<Location> locs = locService.findAll();
        model.addAttribute("locations", locs);
        model.addAttribute("fullfilledOrders", null);
        log.info("I'm in");
        return "home";
    }

    @PostMapping("/order")
    public String order(String order) {

        log.info(order);
        return "redirect:/?order=" + order;
    }
}
