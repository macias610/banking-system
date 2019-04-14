package com.banking.chestnut.models;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@JsonRootName(value = "depositOperation")
public class DepositOperations {
    
    @Id
    @GeneratedValue
    @Column(name = "operation_id")
    private Integer id;
    
    @NonNull
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_id")
    @JsonProperty("deposit")
    private Deposits deposit;
    
    @NonNull
    @Enumerated(EnumType.STRING)
    private OperationType type;
    
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
