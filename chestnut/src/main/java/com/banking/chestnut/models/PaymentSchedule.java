package com.banking.chestnut.models;

import com.banking.chestnut.models.Credits;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class PaymentSchedule {


    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "payment_schedule_id")
    private Integer id;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id")
    private Credits credit;

    @Temporal(TemporalType.DATE)
    private Date payment_date;

    private Float payment_assets;

    private Float payment_interest;

}

