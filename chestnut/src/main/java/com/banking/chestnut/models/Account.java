/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.chestnut.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author macie
 */
@Entity
@Data

@Table(name = "accounts")
public class Account implements Serializable {

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
//    @JoinColumn(name = "currency_id", referencedColumnName = "id")
//    @ManyToOne
//    private Currencies currencyId;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne
    private Client clientId;
    @JoinColumn(name = "info_id", referencedColumnName = "id")
    @ManyToOne
    private AccountInfo infoId;

    @JoinColumn(name = "deleted_by", referencedColumnName = "id")
    @ManyToOne
    private User deletedBy;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @Size(max = 3)
    @Column(name = "currency")
    private String currency;

    @JsonIgnore
    @OneToMany(mappedBy = "accountId")
    private List<Card> cards;

    @OneToMany(mappedBy = "account",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Credits> credits;

    public Account() {
    }

    public Account(String currency) {
        this.isActive = true;
        this.isBlocked = false;
        this.type = AccountType.INDIVIDUAL.type();
        this.currency = currency;
    }

    public Account(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumberClientAccount() {
        return numberClientAccount;
    }

    public void setNumberClientAccount(String numberClientAccount) {
        this.numberClientAccount = numberClientAccount;
    }

    public String getNumberBankingAccount() {
        return numberBankingAccount;
    }

    public void setNumberBankingAccount(String numberBankingAccount) {
        this.numberBankingAccount = numberBankingAccount;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

//    public Currencies getCurrencyId() {
//        return currencyId;
//    }
//
//    public void setCurrencyId(Currencies currencyId) {
//        this.currencyId = currencyId;
//    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public AccountInfo getInfoId() {
        return infoId;
    }

    public void setInfoId(AccountInfo infoId) {
        this.infoId = infoId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public User getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(User deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banking.chestnut.Account[ id=" + id + " ]";
    }



}
