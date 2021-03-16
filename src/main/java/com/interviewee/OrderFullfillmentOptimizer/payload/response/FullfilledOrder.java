package com.interviewee.OrderFullfillmentOptimizer.payload.response;

import com.interviewee.OrderFullfillmentOptimizer.model.Location;
import com.interviewee.OrderFullfillmentOptimizer.payload.request.Order;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FullfilledOrder {

    private Order order;

    private List<Location> location;

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FullfilledOrder that = (FullfilledOrder) o;
        return Objects.equals(order, that.order) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {

        return Objects.hash(order, location);
    }
}
