package com.banking.chestnut.deposit.dto;

import com.banking.chestnut.models.DepositOperations;
import com.banking.chestnut.models.OperationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.banking.chestnut.deposit.helpers.DateHelper.currentTimestamp;

@Data
@NoArgsConstructor
public class DepositOperationDto {
    private Integer id;
    private Integer depositId;
    private OperationType type;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    
    public DepositOperationDto(DepositOperations operation) {
        this.id = operation.getId();
        this.depositId = operation.getDeposit().getId();
        this.type = operation.getType();
        if (operation.getTimestamp() != null) {
            this.timestamp = operation.getTimestamp();
        } else {
            this.timestamp = currentTimestamp();
        }
    }
}
