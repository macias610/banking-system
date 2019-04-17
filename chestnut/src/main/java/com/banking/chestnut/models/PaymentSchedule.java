package com.banking.chestnut.models;

import com.banking.chestnut.models.Credits;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class PaymentSchedule {

    @Id
    @GeneratedValue
    @Column(name = "payment_schedule_id")
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "paymentSchedule",
            fetch = FetchType.LAZY)
    private Set<Credits> credits;

    @Temporal(TemporalType.DATE)
    private Date payment_date;

    private Float payment_assets;

    private Float payment_interest;

}

