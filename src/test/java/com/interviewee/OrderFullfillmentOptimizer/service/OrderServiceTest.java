package com.interviewee.OrderFullfillmentOptimizer.service;

import com.interviewee.OrderFullfillmentOptimizer.exception.NotEnoughStockException;
import com.interviewee.OrderFullfillmentOptimizer.model.Location;
import com.interviewee.OrderFullfillmentOptimizer.model.Product;
import com.interviewee.OrderFullfillmentOptimizer.model.Stock;
import com.interviewee.OrderFullfillmentOptimizer.payload.request.Order;
import com.interviewee.OrderFullfillmentOptimizer.payload.response.FullfilledOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    LocationService locationService;

    @Mock
    ProductService productService;

    @Test
    void shouldSelectSingleLocationFromSingleProductOrder() {

        Product product1 = new Product("test1");
        product1.setId(0L);
        Product product2 = new Product("test2");
        product2.setId(1L);
        Product product3 = new Product("test3");
        product3.setId(2L);

        Location location1 = new Location("loc1");
        Location location2 = new Location("loc2");

        Stock stock1 = new Stock(product1, 5L, location1);
        Stock stock2 = new Stock(product1, 4L, location2);
        Stock stock3 = new Stock(product2, 2L, location2);
        Stock stock4 = new Stock(product3, 7L, location1);

        product1.setStocks(new HashSet<>(Arrays.asList(stock1, stock2)));
        product2.setStocks(new HashSet<>(Arrays.asList(stock3)));
        product3.setStocks(new HashSet<>(Arrays.asList(stock4)));

        assertThat(stock1.getProduct()).isEqualTo(product1);

        location1.setStocks(new HashSet<>(Arrays.asList(stock1, stock4)));
        location2.setStocks(new HashSet<>(Arrays.asList(stock2, stock3)));

        Order order1 = new Order(product1, 4L);

        FullfilledOrder result1 = new FullfilledOrder(order1, Arrays.asList(location1));

        given(productService.findById(0L)).willReturn(product1);

        List<FullfilledOrder> expected = Arrays.asList(result1);

        assertThat(orderService.fullfillOrders(Arrays.asList(order1))).isEqualTo(expected);
    }

    @Test
    void shouldSelectSingleLocationFromMultipleProductOrder() {

        Product product1 = new Product("test1");
        product1.setId(0L);
        Product product2 = new Product("test2");
        product2.setId(1L);
        Product product3 = new Product("test3");
        product3.setId(2L);

        Location location1 = new Location("loc1");
        Location location2 = new Location("loc2");

        Stock stock1 = new Stock(product1, 5L, location1);
        Stock stock2 = new Stock(product1, 4L, location2);
        Stock stock3 = new Stock(product2, 2L, location2);
        Stock stock4 = new Stock(product3, 7L, location1);

        product1.setStocks(new HashSet<>(Arrays.asList(stock1, stock2)));
        product2.setStocks(new HashSet<>(Arrays.asList(stock3)));
        product3.setStocks(new HashSet<>(Arrays.asList(stock4)));

        assertThat(stock1.getProduct()).isEqualTo(product1);

        location1.setStocks(new HashSet<>(Arrays.asList(stock1, stock4)));
        location2.setStocks(new HashSet<>(Arrays.asList(stock2, stock3)));

        Order order1 = new Order(product1, 4L);
        Order order2 = new Order(product2, 2L);

        FullfilledOrder result1 = new FullfilledOrder(order1, Arrays.asList(location2));
        FullfilledOrder result2 = new FullfilledOrder(order2, Arrays.asList(location2));

        given(productService.findById(0L)).willReturn(product1);
        given(productService.findById(1L)).willReturn(product2);

        List<FullfilledOrder> expected = Arrays.asList(result1, result2);

        assertThat(orderService.fullfillOrders(Arrays.asList(order1, order2))).isEqualTo(expected);
    }

    @Test
    void shouldSelectMultipleLocationFromSingleProductOrder() {

        Product product1 = new Product("test1");
        product1.setId(0L);
        Product product2 = new Product("test2");
        product2.setId(1L);
        Product product3 = new Product("test3");
        product2.setId(2L);

        Location location1 = new Location("loc1");
        Location location2 = new Location("loc2");

        Stock stock1 = new Stock(product1, 5L, location1);
        Stock stock2 = new Stock(product1, 4L, location2);
        Stock stock3 = new Stock(product2, 2L, location2);
        Stock stock4 = new Stock(product3, 7L, location1);

        product1.setStocks(new HashSet<>(Arrays.asList(stock1, stock2)));
        product2.setStocks(new HashSet<>(Arrays.asList(stock3)));
        product3.setStocks(new HashSet<>(Arrays.asList(stock4)));

        assertThat(stock1.getProduct()).isEqualTo(product1);

        location1.setStocks(new HashSet<>(Arrays.asList(stock1, stock4)));
        location2.setStocks(new HashSet<>(Arrays.asList(stock2, stock3)));

        Order order1 = new Order(product1, 9L);
        FullfilledOrder result1 = new FullfilledOrder(order1, Arrays.asList(location1, location2));

        given(productService.findById(0L)).willReturn(product1);

        List<FullfilledOrder> expected = Arrays.asList(result1);

        assertThat(orderService.fullfillOrders(Arrays.asList(order1))).isEqualTo(expected);
    }

    @Test
    void shouldSelectMultipleLocationFromMultipleProductOrder() {

        Product product1 = new Product("test1");
        product1.setId(0L);
        Product product2 = new Product("test2");
        product2.setId(1L);
        Product product3 = new Product("test3");
        product3.setId(2L);

        Location location1 = new Location("loc1");
        Location location2 = new Location("loc2");
        Location location3 = new Location("loc3");

        Stock stock1 = new Stock(product1, 4L, location1);
        Stock stock2 = new Stock(product1, 5L, location2);
        Stock stock3 = new Stock(product2, 2L, location2);
        Stock stock4 = new Stock(product3, 7L, location1);
        Stock stock5 = new Stock(product3, 10L, location3);

        product1.setStocks(new HashSet<>(Arrays.asList(stock1, stock2)));
        product2.setStocks(new HashSet<>(Arrays.asList(stock3)));
        product3.setStocks(new HashSet<>(Arrays.asList(stock4, stock5)));

        assertThat(stock1.getProduct()).isEqualTo(product1);

        location1.setStocks(new HashSet<>(Arrays.asList(stock1, stock4)));
        location2.setStocks(new HashSet<>(Arrays.asList(stock2, stock3, stock5)));
        location3.setStocks(new HashSet<>(Arrays.asList(stock5)));

        Order order1 = new Order(product1, 5L);
        Order order2 = new Order(product3, 7L);

        FullfilledOrder result1 = new FullfilledOrder(order1, Arrays.asList(location2));
        FullfilledOrder result2 = new FullfilledOrder(order2, Arrays.asList(location1));

        given(productService.findById(0L)).willReturn(product1);
        given(productService.findById(2L)).willReturn(product3);

        List<FullfilledOrder> expected = Arrays.asList(result1, result2);

        assertThat(orderService.fullfillOrders(Arrays.asList(order1, order2))).isEqualTo(expected);
    }

    @Test
    void shouldThrowNotEnoughStockException() {

        Product product1 = new Product("test1");
        product1.setId(0L);
        Product product2 = new Product("test2");
        product2.setId(1L);
        Product product3 = new Product("test3");
        product3.setId(2L);

        Location location1 = new Location("loc1");
        Location location2 = new Location("loc2");

        Stock stock1 = new Stock(product1, 4L, location1);
        Stock stock2 = new Stock(product1, 5L, location2);
        Stock stock3 = new Stock(product2, 2L, location2);
        Stock stock4 = new Stock(product3, 7L, location1);

        product1.setStocks(new HashSet<>(Arrays.asList(stock1, stock2)));
        product2.setStocks(new HashSet<>(Arrays.asList(stock3)));
        product3.setStocks(new HashSet<>(Arrays.asList(stock4)));

        assertThat(stock1.getProduct()).isEqualTo(product1);

        location1.setStocks(new HashSet<>(Arrays.asList(stock1, stock4)));
        location2.setStocks(new HashSet<>(Arrays.asList(stock2, stock3)));

        Order order1 = new Order(product1, 10L);
        Order order2 = new Order(product3, 7L);

        FullfilledOrder result1 = new FullfilledOrder(order1, Arrays.asList(location2));
        FullfilledOrder result2 = new FullfilledOrder(order2, Arrays.asList(location1));

        given(productService.findById(0L)).willReturn(product1);
        given(productService.findById(2L)).willReturn(product3);

        List<FullfilledOrder> expected = Arrays.asList(result1, result2);

        assertThrows(NotEnoughStockException.class, () -> orderService.fullfillOrders(Arrays.asList(order1, order2)));
    }
}