package com.interviewee.OrderFullfillmentOptimizer.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Product extends BaseEntity{

    @Column(unique = true)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private Set<Stock> stocks;

    public Product(String name) {

        this.name = name;
    }

    public Long getTotalAmount() {

        Long total = 0L;
        if (stocks == null || stocks.isEmpty())
            return total;

        for (Stock stock : getStocks()) {

            if (stock == null)
                continue;
            total += stock.getAmount();
        }

        return total;
    }

    public boolean addToStock(@NotNull Product product, @NotNull Long amount) {

        Stock stock = getStockOf(product);
        if (stock == null)
            getStocks().add(new Stock(product, amount, null));
        else
            stock.increase(amount);

        return true;
    }

    public boolean removeFromStock(@NotNull Product product, @NotNull Long amount) {

        Stock stock = getStockOf(product);
        if (stock == null)
            return false;
        else {

            Long newAmount = stock.getAmount() - amount;
            if (newAmount < 0)
                getStocks().remove(stock);
            else
                stock.setAmount(newAmount);
        }

        return true;
    }

    private Stock getStockOf(@NotNull Product product) {

        Optional<Stock> optionalStock = getStocks().stream()
                .filter(stock -> stock.getProduct().equals(product))
                .findFirst();

        if (optionalStock.isPresent())
            return optionalStock.get();
        else
            return null;
    }

    @Override
    public String toString() {

        return getName();
    }
}