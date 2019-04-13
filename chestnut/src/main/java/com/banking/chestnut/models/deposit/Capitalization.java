package com.banking.chestnut.models.deposit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Capitalization {
    
    @Id
    @GeneratedValue
    @Column(name = "capitalization_id")
    private Integer id;
    
    @JsonIgnore
    @OneToMany(mappedBy = "capitalization")
    private Set<DepositType> depositTypes;
    
    @Enumerated(EnumType.STRING)
    private CapitalizationType type;
    
    private Integer daysPeriod;
}
