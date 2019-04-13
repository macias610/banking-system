package com.banking.chestnut.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Operation {
    
    @Id
    @GeneratedValue
    @Column(name = "operation_id")
    private Integer id;
    
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;
    
    @NonNull
    @Enumerated(EnumType.STRING)
    private OperationType type;
    
    @NonNull
    @Temporal(TemporalType.DATE)
    private Date date;
}
