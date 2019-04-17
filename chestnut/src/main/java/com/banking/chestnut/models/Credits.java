package com.banking.chestnut.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@JsonRootName(value = "credit")
public class Credits {

    @Id
    @GeneratedValue
    @Column(name = "credit_id")
    private Long id;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Accounts account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_type_id")
    private CreditType creditType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_schedule_id")
    private PaymentSchedule paymentSchedule;

    private Float value;

    private boolean is_active;

    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Temporal(TemporalType.DATE)
    private Date expiration_at;

    @Temporal(TemporalType.DATE)
    private Date deleted_at;

    private Long created_by;

    private Long deleted_by;

    @JsonIgnore
    @OneToMany(mappedBy = "credit",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<CreditBalance> creditBalance;

}
