package com.banking.chestnut.models;

import com.banking.chestnut.credit.dto.CreditDto;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Credits {

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "credit_id")
    private Integer id;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_type_id")
    private CreditType creditType;

    private Long value;

    private Boolean isActive;

    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Temporal(TemporalType.DATE)
    private Date expiration_at;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deleted_at;

    private Long created_by;

    private Long deleted_by;

    @OneToMany(mappedBy = "credit",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<CreditBalance> creditBalance;

    @OneToMany(mappedBy = "credit",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<PaymentSchedule> paymentSchedule;

    public Credits(CreditDto creditDto, Account account, CreditType creditType){
        this.account = account;
        this.creditType = creditType;
        this.value = creditDto.getValue();
        this.isActive = creditDto.getIsActive();
        this.created_at = creditDto.getCreated_at();
        this.expiration_at = creditDto.getExpiration_at();
    }

}
