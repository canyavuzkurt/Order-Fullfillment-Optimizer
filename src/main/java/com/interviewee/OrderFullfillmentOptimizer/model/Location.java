package com.interviewee.OrderFullfillmentOptimizer.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Location extends BaseEntity{

    @Column(unique = true)
    @NotBlank
    private String alias;
}
