package com.interviewee.OrderFullfillmentOptimizer.service;

import com.interviewee.OrderFullfillmentOptimizer.exception.NotEnoughStockException;
import com.interviewee.OrderFullfillmentOptimizer.model.Location;
import com.interviewee.OrderFullfillmentOptimizer.model.Product;
import com.interviewee.OrderFullfillmentOptimizer.model.Stock;
import com.interviewee.OrderFullfillmentOptimizer.payload.request.Order;
import com.interviewee.OrderFullfillmentOptimizer.payload.response.FullfilledOrder;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    final LocationService locationService;
    final ProductService productService;

    public OrderService(LocationService locationService, ProductService productService) {

        this.locationService = locationService;
        this.productService = productService;
    }

    public List<FullfilledOrder> fullfillOrders(String order) {

        return fullfillOrders(parseOrder(order));
    }

    @Transactional(readOnly = true)
    public List<FullfilledOrder> fullfillOrders(List<Order> orders) {

        boolean first = true;
        List<Location> commonLocations = new ArrayList<>();
        List<Set<Stock>> possibleStocksPerProduct = new ArrayList<>();

        for (Order order : orders) {

            Product product = productService.findById(order.getProductId());

            Set<Stock> singleFullfillStocks = product.getStocks().stream()
                    .filter(stock -> stock.getAmount() >= order.getAmount()).collect(Collectors.toSet());

            List<Location> singleFullfillLocations = singleFullfillStocks.stream()
                    .map(stock -> stock.getLocation()).collect(
                    Collectors.toList());
            if (first && !singleFullfillStocks.isEmpty()){

                commonLocations.addAll(singleFullfillLocations);
                first = false;
            }
            else if (!singleFullfillStocks.isEmpty() && !commonLocations.isEmpty()){

                List<Location> newLocations = new ArrayList<>();

                for (Location loc : singleFullfillLocations) {
                    if (commonLocations.contains(loc))
                        newLocations.add(loc);
                }

                commonLocations = newLocations;
            }
            else
                possibleStocksPerProduct.add(product.getStocks());
        }

        List<FullfilledOrder> fullfilledOrders = new ArrayList<>();

        // Order can be fullfilled from single location.
        if (!commonLocations.isEmpty()) {
            Location location = commonLocations.get(0);
            Set<Stock> stocks = location.getStocks();
            for (Order order : orders) {

                Product orderProduct = getProductFromOrder(order);
                fullfilledOrders.add(
                        new FullfilledOrder(order, Arrays.asList(stocks.stream()
                                        .filter(stock -> stock.getProduct().equals(orderProduct))
                                        .map(stock -> stock.getLocation())
                                        .findFirst().get())));
            }
        }
        else {

            for (Order order : orders)
                fullfilledOrders.add(fullfillOrder(order));
        }

        return fullfilledOrders;
    }

    public List<Order> parseOrder(String order) {

        String[] splitOrder = order.split(" ");
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < splitOrder.length; i+=2) {

            orders.add(new Order(Long.valueOf(splitOrder[i]), Long.valueOf(splitOrder[i+1])));
        }

        return orders;
    }

    private List<Product> fetchProducts(List<Order> orders) {

        List<Product> products = new ArrayList<>();
        for (Order order : orders) {

            products.add(getProductFromOrder(order));
        }

        return products;
    }

    private Product getProductFromOrder(Order order) {

        return productService.findById(order.getProductId());
    }

    private FullfilledOrder fullfillOrder(Order order) {

        Product product = getProductFromOrder(order);
        Set<Stock> stocks = product.getStocks();
        List<Stock> sortedStocks = stocks.stream().sorted(Comparator.comparing(Stock::getAmount).reversed())
                .collect(Collectors.toList());

        List<Location> fullfillmentLocations = new ArrayList<>();

        Long fullfilledQuantity = 0L;

        int i = 0;
        while (fullfilledQuantity < order.getAmount() && i<sortedStocks.size()) {

            Stock stock = sortedStocks.get(i);
            if (stock.getAmount() <= order.getAmount() - fullfilledQuantity) {

                fullfilledQuantity += stock.getAmount();
                fullfillmentLocations.add(stock.getLocation());
            }
            else
                fullfilledQuantity += order.getAmount() - fullfilledQuantity;
            i++;
        }

        if (fullfilledQuantity != order.getAmount())
            throw new NotEnoughStockException(product.getName());

        return new FullfilledOrder(order, fullfillmentLocations);
    }

}
