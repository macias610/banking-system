package com.banking.chestnut.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@JsonRootName(value = "depositType")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class DepositTypes {
    
    @Id
    @GeneratedValue
    @Column(name = "deposit_type_id")
    private Integer id;
    
    @JsonIgnore
    @OneToMany(mappedBy = "depositType",
            fetch = FetchType.LAZY)
    private Set<Deposits> deposits;
    
    private String name;
    
    private Float maxAmount;
    private Float minAmount;
    private Float interestRate;
    
    private Integer daysPeriod;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capitalization_id")
    @JsonProperty("capitalization")
    private DepositCapitalizations capitalization;
    
}
