package com.interviewee.OrderFullfillmentOptimizer.payload.response;

import com.interviewee.OrderFullfillmentOptimizer.model.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FullfilledOrders {

    private List<FullfilledOrder> fullfilledOrderList;

    public String getMessage() {

        String message = "You can take ";

        HashMap<Location, String> locationsAndProducts = new HashMap<>();

        for (FullfilledOrder fullfilledOrder : fullfilledOrderList) {

            String productName = fullfilledOrder.getOrder().getProductName();
            List<Location> locations = fullfilledOrder.getLocations();

            for (Location loc : locations) {

                String productNames = locationsAndProducts.get(loc);
                if (productNames == null) {
                    locationsAndProducts.put(loc, productName);
                } else
                    locationsAndProducts.put(loc, productNames + "," + productName);
            }
        }

        for (Location location : locationsAndProducts.keySet()) {

            if (message.equals("You can take "))
                message += locationsAndProducts.get(location) + " AT " + location.getAlias();
            else
                message += " and " + locationsAndProducts.get(location) + " AT " + location.getAlias();
        }

        return message;
    }
}
