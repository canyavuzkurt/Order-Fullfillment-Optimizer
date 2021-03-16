package com.interviewee.OrderFullfillmentOptimizer.payload.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

    private Long productId;

    private Long amount;
}
