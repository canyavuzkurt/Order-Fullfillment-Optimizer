package com.interviewee.OrderFullfillmentOptimizer.payload.request;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrdersWrapper {

    public ArrayList<Order> orders;
}
