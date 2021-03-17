package com.interviewee.OrderFullfillmentOptimizer.controller;

import com.interviewee.OrderFullfillmentOptimizer.exception.NotEnoughStockException;
import com.interviewee.OrderFullfillmentOptimizer.model.Location;
import com.interviewee.OrderFullfillmentOptimizer.model.Product;
import com.interviewee.OrderFullfillmentOptimizer.payload.request.Order;
import com.interviewee.OrderFullfillmentOptimizer.payload.request.OrdersWrapper;
import com.interviewee.OrderFullfillmentOptimizer.payload.response.FullfilledOrder;
import com.interviewee.OrderFullfillmentOptimizer.service.LocationService;
import com.interviewee.OrderFullfillmentOptimizer.service.OrderService;
import com.interviewee.OrderFullfillmentOptimizer.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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

    // This is a rather messy way of doing things because of the thymeleaf.
    // I normally only create the api but since I had to do a basic front-end as well,
    // I used thymeleaf which is messy but works.
    @GetMapping("")
    public String homePage(Model model, @ModelAttribute("orders") final OrdersWrapper ordersWrapper, RedirectAttributes redirectAttributes) {

        List<Product> all = productService.findAll();
        model.addAttribute("products", all);
        List<Location> locs = locService.findAll();
        model.addAttribute("locations", locs);


        List<FullfilledOrder> fullfilledOrders = new ArrayList<>();
        String error = null;
        if (ordersWrapper.getOrders() != null){
            try {

                fullfilledOrders = orderService.fullfillOrders(ordersWrapper.getOrders());
            }
            catch (NotEnoughStockException e) {

                error = e.getMessage();
            }
        }
        else {

        }
        ArrayList<Order> orders = new ArrayList<>();
        for (Product product : all) {

            orders.add(new Order(product.getId(), 0L));
        }
        model.addAttribute("ordersWrapper", new OrdersWrapper(orders));
        model.addAttribute("fullfilledOrders", fullfilledOrders);
        model.addAttribute("error", error);
        log.info("I'm in");
        return "home";
    }

    @PostMapping("/order")
    public String order(OrdersWrapper orders, RedirectAttributes redirectAttrs) {

        redirectAttrs.addFlashAttribute("orders", orders);
        log.info(orders.getOrders().toString());
        return "redirect: ";
    }
}
