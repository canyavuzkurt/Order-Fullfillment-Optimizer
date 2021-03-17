package com.interviewee.OrderFullfillmentOptimizer.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Location extends BaseEntity {

    @Column(unique = true)
    @NotBlank
    private String alias;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "location")
    @OrderBy("amount desc")
    private Set<Stock> stocks;

    public Location(@NotBlank String alias) {

        this.alias = alias;
    }

    public String getStockAvailability() {

        String result = "";
        for (Stock stock : stocks) {

            if (stock != null) {

                result += stock.getProduct().getName() + "(" + stock.getAmount() + ") ";
            }
        }

        return result;
    }
}
