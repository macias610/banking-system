package com.banking.chestnut.models.deposit;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Operation {
    
    @Id
    @GeneratedValue
    @Column(name = "operation_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;
    
    @Enumerated(EnumType.STRING)
    private OperationType type;
    
    @Temporal(TemporalType.DATE)
    private Date date;
}
