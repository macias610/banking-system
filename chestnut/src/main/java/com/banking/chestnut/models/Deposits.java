package com.banking.chestnut.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@JsonRootName(value = "deposit")
public class Deposits {
    
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposit_id")
    private Integer id;
    
    @Getter
    @Setter
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id") //FK
    private Accounts account;
    
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_type_id")
    private DepositTypes depositType;
    
    @Getter
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    
    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    
    @Getter
    private Float amount;
    
    @JsonIgnore
    @OneToMany(mappedBy = "deposit",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<DepositOperations> operations;
    
    @Getter
    @Setter
    private Boolean isActive;
    
    
}
