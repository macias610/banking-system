package com.banking.chestnut.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class CreditBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_balance_id")
    private Integer id;

    @JsonIgnore
    @OneToMany(mappedBy = "creditBalance",
            fetch = FetchType.LAZY)
    private Set<Credits> credits;

    private Float debt_asset;

    private Float debt_interest;

    private Long payments_left;
}

