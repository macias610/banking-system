package com.banking.chestnut.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonRootName(value = "capitalization")
public class DepositCapitalizations {
    
    @Id
    @GeneratedValue
    @Column(name = "capitalization_id")
    private Integer id;
    
    @JsonIgnore
    @OneToMany(mappedBy = "capitalization")
    private Set<DepositTypes> depositTypes;
    
    @Enumerated(EnumType.STRING)
    private CapitalizationType type;
    
    private Integer daysPeriod;
}
