package com.banking.chestnut.models;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DepositOperations {
    
    @Id
    @GeneratedValue
    @Column(name = "operation_id")
    private Integer id;
    
    @NonNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_id")
    private Deposits deposit;
    
    @NonNull
    @Enumerated(EnumType.STRING)
    private OperationType type;
    
    @NonNull
    private LocalDateTime timestamp;
}
