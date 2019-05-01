package com.banking.chestnut.models;

import com.banking.chestnut.deposit.dto.DepositTypeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@NoArgsConstructor
public class DepositTypes {
    
    @Getter
    @Id
    @GeneratedValue
    @Column(name = "deposit_type_id")
    private Integer id;
    
    @OneToMany(mappedBy = "depositType",
            fetch = FetchType.LAZY)
    private Set<Deposits> deposits;
    
    @Getter
    private String name;
    
    @Getter
    private Float maxAmount;
    
    @Getter
    private Float minAmount;
    
    @Getter
    private Float interestRate;
    
    @Getter
    private Integer daysPeriod;
    
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capitalization_id")
    private DepositCapitalizations capitalization;
    
    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by", referencedColumnName = "id")
    private User deletedBy;
    
    @Getter
    @Setter
    private LocalDateTime deletedAt;
    
    public DepositTypes(DepositTypeDto depositTypeDto, DepositCapitalizations capitalization) {
        this.name = depositTypeDto.getName();
        this.maxAmount = depositTypeDto.getMaxAmount();
        this.minAmount = depositTypeDto.getMinAmount();
        this.interestRate = depositTypeDto.getInterestRate();
        this.daysPeriod = depositTypeDto.getDaysPeriod();
        this.capitalization = capitalization;
    }
}
