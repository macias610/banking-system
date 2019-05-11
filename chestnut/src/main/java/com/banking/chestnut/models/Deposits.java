package com.banking.chestnut.models;

import com.banking.chestnut.deposit.dto.DepositDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.Set;

import static com.banking.chestnut.deposit.helpers.DateHelper.currentDate;


@Entity
@NoArgsConstructor
public class Deposits {
    
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposit_id")
    private Integer id;
    
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id") //FK
    private Account account;
    
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_type_id")
    private DepositTypes depositType;
    
    @Getter
    private Date startDate;
    
    @Getter
    @Setter
    private Date endDate;
    
    @Getter
    private Float amount;
    
    @Getter
    @OneToMany(mappedBy = "deposit",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<DepositOperations> operations;
    
    @Getter
    @Setter
    private Boolean isActive;
    
    
    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by", referencedColumnName = "id")
    private User deletedBy;
    
    @Getter
    @Setter
    private LocalDateTime deletedAt;
    
    public Deposits(DepositDto depositDto, Account account, DepositTypes depositType){
        this.account = account;
        this.depositType = depositType;
        if (depositDto.getStartDate() != null){
            this.startDate = depositDto.getStartDate();
        } else {
            this.startDate = currentDate();
        }
        this.endDate = depositDto.getEndDate();
        this.amount = depositDto.getAmount();
        this.isActive = depositDto.getIsActive();
    }
}
