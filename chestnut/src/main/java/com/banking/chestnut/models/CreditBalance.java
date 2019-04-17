package com.banking.chestnut.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class CreditBalance {

    @Id
    @GeneratedValue
    @Column(name = "credit_balance_id")
    private Long id;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id")
    private Credits credit;

    private Float debt_asset;

    private Float debt_interest;

    private Boolean is_overdue;

    private Long payments_left;


}

