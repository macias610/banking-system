package com.banking.chestnut.models;

import com.banking.chestnut.models.Credits;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class CreditType {

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "credit_type_id")
    private Integer id;

    @JsonIgnore
    @OneToMany(mappedBy = "creditType",
            fetch = FetchType.LAZY)
    private Set<Credits> credits;

    private String name;

    private String info;

    private Float min_value;

    private Float max_value;

    private String loan_period;

    private Float interest_rate;

    public Integer getId() {
        return id;
    }
}

