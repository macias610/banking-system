package com.banking.chestnut.models;

import com.banking.chestnut.models.Capitalization;
import com.banking.chestnut.models.Deposit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class DepositType {
    
    @Id
    @GeneratedValue
    @Column(name = "deposit_type_id")
    private Integer id;
    
    @JsonIgnore
    @OneToMany(mappedBy = "depositType",
            fetch = FetchType.LAZY)
    private Set<Deposit> deposit;
    
    private String name;
    
    private Float maxAmount;
    private Float minAmount;
    private Float interestRate;
    
    private Integer daysPeriod;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capitalization_id")
    private Capitalization capitalization;
    
}
