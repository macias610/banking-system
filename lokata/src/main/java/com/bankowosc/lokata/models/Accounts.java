package com.bankowosc.lokata.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Accounts {
    
    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;
    
    @JsonIgnore
    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Deposit> deposits;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private Currencies currency;
    
    @JsonIgnore
    private Integer clientId;
    
    @JsonIgnore
    private Integer infoId;
    
    @JsonIgnore
    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive;
    
    @JsonIgnore
    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isBlocked;
    
    
    
}
