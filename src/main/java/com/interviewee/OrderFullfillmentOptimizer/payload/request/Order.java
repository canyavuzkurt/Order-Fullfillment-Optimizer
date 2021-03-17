package com.interviewee.OrderFullfillmentOptimizer.payload.request;

import com.interviewee.OrderFullfillmentOptimizer.model.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

    private Long productId;

    private Long amount;

    private String productName;

    public Order(Product product, Long amount) {

        setProductId(product.getId());
        setProductName(product.getName());
        setAmount(amount);
    }
}
