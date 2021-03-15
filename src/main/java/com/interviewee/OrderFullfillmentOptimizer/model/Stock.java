package com.interviewee.OrderFullfillmentOptimizer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    private Product product;

    @NotNull(message = "Amount can not be null.")
    @Column
    private Long amount;

    public void increase(@NotNull Long amount) {

        setAmount(getAmount() + amount);
    }

    public void decrease(@NotNull Long amount) {

        setAmount(getAmount() + amount);
    }
}
