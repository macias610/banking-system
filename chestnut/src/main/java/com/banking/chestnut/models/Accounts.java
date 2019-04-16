/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.chestnut.models;

import com.banking.chestnut.models.dto.AccountNumber;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author macie
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "accounts")
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "type")
    private String type;
    @Size(max = 16)
    @Column(name = "number_client_account")
    private String numberClientAccount;
    @Size(max = 26)
    @Column(name = "number_banking_account")
    private String numberBankingAccount;
    @Size(max = 28)
    @Column(name = "iban")
    private String iban;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "is_blocked")
    private Boolean isBlocked;
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Currencies currencyId;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne
    private Client clientId;
    @JoinColumn(name = "info_id", referencedColumnName = "id")
    @ManyToOne
    private AccountInfo infoId;

    @Override
    public String toString() {
        return "com.banking.chestnut.Accounts[ id=" + id + " ]";
    }



}
