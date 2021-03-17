package com.interviewee.OrderFullfillmentOptimizer.service;

import com.interviewee.OrderFullfillmentOptimizer.exception.NotEnoughStockException;
import com.interviewee.OrderFullfillmentOptimizer.model.Location;
import com.interviewee.OrderFullfillmentOptimizer.model.Product;
import com.interviewee.OrderFullfillmentOptimizer.model.Stock;
import com.interviewee.OrderFullfillmentOptimizer.payload.request.Order;
import com.interviewee.OrderFullfillmentOptimizer.payload.response.FullfilledOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    final LocationService locationService;
    final ProductService productService;

    public OrderService(LocationService locationService, ProductService productService) {

        this.locationService = locationService;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    // It is an O(n^2) algorithm.
    // Order is in terms of a product, a list of orders contains request for multiple products.
    public List<FullfilledOrder> fullfillOrders(List<Order> orders) {

        orders.removeIf(e -> e.getAmount() == 0);

        // List of Stocks that can fulfill the order by themselves for optimization.
        List<List<Stock>> singleFullfillStocksPerOrder = new ArrayList<>();
        // List of hashset of locations that the above list of stocks contains.
        // This is done to reduce the time complexity.
        List<HashSet<Location>> singleFullfillLocationsPerOrder = new ArrayList<>();

        for (Order order : orders) {

            Product product = productService.findById(order.getProductId());

            List<Stock> singleFullfillStocks = product.getStocks().stream()
                    .filter(stock -> stock.getAmount() >= order.getAmount())
                    .sorted(Comparator.comparing(Stock::getAmount).reversed())
                    .collect(Collectors.toList());

            singleFullfillStocksPerOrder.add(singleFullfillStocks);

            HashSet<Location> singleFullfillLocations = singleFullfillStocks.stream()
                    .map(stock -> stock.getLocation()).collect(
                            Collectors.toCollection(HashSet::new));

            singleFullfillLocationsPerOrder.add(singleFullfillLocations);
        }

        List<FullfilledOrder> fullfilledOrders = fullfillOrdersOptimally(orders, singleFullfillStocksPerOrder,
                singleFullfillLocationsPerOrder);

        return fullfilledOrders;

    }

    private List<FullfilledOrder> fullfillOrdersOptimally(List<Order> orders,
            List<List<Stock>> singleFullfillStocksPerOrder, List<HashSet<Location>> singleFullfillLocationsPerOrder) {

        List<FullfilledOrder> fullfilledOrders = new ArrayList<>();
        List<Location> locationsOrderedByFrequency = getLocationsOrderedByFrequency(singleFullfillStocksPerOrder);
        for (int i = 0; i < orders.size(); i++) {

            HashSet<Location> singleFullfilmentLocations = singleFullfillLocationsPerOrder.get(i);

            Location optimumLocation = null;
            for (Location loc : locationsOrderedByFrequency) {

                // Hashset Contains time complexity O(1)
                if (singleFullfilmentLocations.contains(loc)){

                    optimumLocation = loc;
                    break;
                }
            }

            // If an order for a product can be fulfilled from a single location.
            if (optimumLocation != null) {

                fullfilledOrders.add(new FullfilledOrder(orders.get(i), Arrays.asList(optimumLocation)));
            }
            // If an order for a product can not be fulfilled from a single locations.
            // For example there is 1 oven at loc1 and 2 oven at loc3,
            // for the Order of 3 Ovens this can be fulfilled from loc1 and 2 together.
            else
                fullfilledOrders.add(fullfillOrderFromMultipleLocations(orders.get(i)));
        }
        return fullfilledOrders;
    }

    private List<Location> getLocationsOrderedByFrequency(List<List<Stock>> singleFullfillStocksPerOrder) {

        HashMap<Location, Long> locationFrequency = new HashMap<>();

        for (List<Stock> singleFullfillStocks : singleFullfillStocksPerOrder) {

            for (Stock singleFullfillStock: singleFullfillStocks) {

                Location loc = singleFullfillStock.getLocation();
                Long frequency = locationFrequency.get(loc);
                if (frequency == null)
                    locationFrequency.put(loc, 1L);
                else
                    locationFrequency.put(loc, frequency + 1L);
            }
        }

        ArrayList<Location> locations = new ArrayList<>(locationFrequency.keySet());
        List<Location> locationsOrderedByFrequency = locations.stream()
                .sorted(Comparator.comparing(locationFrequency::get).reversed())
                .collect(Collectors.toList());

        return locationsOrderedByFrequency;
    }

    private Product getProductFromOrder(Order order) {

        return productService.findById(order.getProductId());
    }

    private FullfilledOrder fullfillOrderFromMultipleLocations(Order order) {

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
            else {
                fullfilledQuantity += order.getAmount() - fullfilledQuantity;
                fullfillmentLocations.add(stock.getLocation());
            }
            i++;
        }

        if (fullfilledQuantity != order.getAmount())
            throw new NotEnoughStockException(product.getName());

        return new FullfilledOrder(order, fullfillmentLocations);
    }

}
