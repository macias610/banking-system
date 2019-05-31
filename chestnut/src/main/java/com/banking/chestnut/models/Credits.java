package com.banking.chestnut.models;

import com.banking.chestnut.credit.dto.CreditDto;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import javax.persistence.*;
//import java.util.Date;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

import static com.banking.chestnut.credit.helpers.DateHelper.currentDate;

@Entity
@NoArgsConstructor
//@Data
public class Credits {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id")
    private Integer id;

//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//    @JsonIdentityReference(alwaysAsId = true)
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_type_id")
    private CreditType creditType;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_balance_id")
    private CreditBalance creditBalance;

    @Getter
    @Setter
    private Long value;

    @Getter
    @Setter
    private Boolean isActive;

    //@Temporal(TemporalType.DATE)
    @Getter
    @Setter
    private Date created_at;

    //@Temporal(TemporalType.DATE)
    @Getter
    @Setter
    private Date expiration_at;

    //@Temporal(TemporalType.DATE)
    //@JsonFormat(pattern = "yyyy-MM-dd")
    @Getter
    @Setter
    private LocalDateTime deleted_at;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User created_by;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by", referencedColumnName = "id")
    private User deletedBy;

    @Getter
    @Setter
    @OneToMany(mappedBy = "credit",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<PaymentSchedule> paymentSchedule;

    public Credits(CreditDto creditDto, Account account, CreditType creditType){
        this.account = account;
        this.creditType = creditType;
        this.value = creditDto.getValue();
    }

}
