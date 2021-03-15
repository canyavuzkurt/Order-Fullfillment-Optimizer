package com.interviewee.OrderFullfillmentOptimizer.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Location extends BaseEntity{

    @Column(unique = true)
    @NotBlank
    private String alias;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "location")
    private Set<Stock> stocks;

    public String getStockAvailability() {

        String result = "";
        for (Stock stock : stocks) {

            if (stock != null ){

                result += stock.getProduct().getName() + "(" + stock.getAmount() + ") ";
            }
        }

        return result;
    }
}
