package com.bankowosc.lokata.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Deposit {
    
    @Id
    @GeneratedValue
    @Column(name = "deposit_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id") //FK
    private Accounts account;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_type_id")
    private DepositType depositType;
    
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @JsonIgnore
    @OneToMany(mappedBy = "deposit",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Operation> operations;
    
    private Boolean isActive;
    
    
}