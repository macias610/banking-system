package com.bankowosc.lokata.models;

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
public class Currencies {
    
    @Id
    @GeneratedValue
    @Column(name = "currency_id")
    private Long id;
    
    @JsonIgnore
    @OneToMany(mappedBy = "currency")
    private Set<Accounts> accounts;
    
    private String name;
}
