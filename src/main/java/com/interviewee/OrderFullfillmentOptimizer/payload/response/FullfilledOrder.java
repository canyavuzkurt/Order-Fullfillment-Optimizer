package com.interviewee.OrderFullfillmentOptimizer.payload.response;

import com.interviewee.OrderFullfillmentOptimizer.model.Location;
import com.interviewee.OrderFullfillmentOptimizer.payload.request.Order;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FullfilledOrder {

    private Order order;

    private List<Location> locations;

    public List<String> locationAliases() {

        List<String> aliases = new ArrayList<>();

        for (Location loc : locations) {

            aliases.add(loc.getAlias());
        }

        return aliases;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FullfilledOrder that = (FullfilledOrder) o;
        return Objects.equals(order, that.order) && Objects.equals(locations, that.locations);
    }

    @Override
    public int hashCode() {

        return Objects.hash(order, locations);
    }
}
