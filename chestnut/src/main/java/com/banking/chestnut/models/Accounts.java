/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.chestnut.models;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author macie
 */
@Entity
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
    @OneToMany(mappedBy = "accountId")
    private Set<Reports> reportsSet;
    @OneToMany(mappedBy = "accountId")
    private Set<AccountHistory> accountHistorySet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private Set<Cards> cardsSet;
    @OneToMany(mappedBy = "accountId")
    private Set<Credits> creditsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "senderId")
    private Set<Transactions> transactionsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiverId")
    private Set<Transactions> transactionsSet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private Set<Deposits> depositsSet;
    @OneToMany(mappedBy = "senderId")
    private Set<PermanentTransactions> permanentTransactionsSet;
    @OneToMany(mappedBy = "receiverId")
    private Set<PermanentTransactions> permanentTransactionsSet1;
    @OneToMany(mappedBy = "accountId")
    private Set<DirectDebits> directDebitsSet;
    @OneToMany(mappedBy = "providerId")
    private Set<DirectDebits> directDebitsSet1;
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Currencies currencyId;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Clients clientId;
    @JoinColumn(name = "info_id", referencedColumnName = "id")
    @ManyToOne
    private AccountInfo infoId;

    public Accounts() {
    }

    public Accounts(Integer id) {
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

    public Set<Reports> getReportsSet() {
        return reportsSet;
    }

    public void setReportsSet(Set<Reports> reportsSet) {
        this.reportsSet = reportsSet;
    }

    public Set<AccountHistory> getAccountHistorySet() {
        return accountHistorySet;
    }

    public void setAccountHistorySet(Set<AccountHistory> accountHistorySet) {
        this.accountHistorySet = accountHistorySet;
    }

    public Set<Cards> getCardsSet() {
        return cardsSet;
    }

    public void setCardsSet(Set<Cards> cardsSet) {
        this.cardsSet = cardsSet;
    }

    public Set<Credits> getCreditsSet() {
        return creditsSet;
    }

    public void setCreditsSet(Set<Credits> creditsSet) {
        this.creditsSet = creditsSet;
    }

    public Set<Transactions> getTransactionsSet() {
        return transactionsSet;
    }

    public void setTransactionsSet(Set<Transactions> transactionsSet) {
        this.transactionsSet = transactionsSet;
    }

    public Set<Transactions> getTransactionsSet1() {
        return transactionsSet1;
    }

    public void setTransactionsSet1(Set<Transactions> transactionsSet1) {
        this.transactionsSet1 = transactionsSet1;
    }

    public Set<Deposits> getDepositsSet() {
        return depositsSet;
    }

    public void setDepositsSet(Set<Deposits> depositsSet) {
        this.depositsSet = depositsSet;
    }

    public Set<PermanentTransactions> getPermanentTransactionsSet() {
        return permanentTransactionsSet;
    }

    public void setPermanentTransactionsSet(Set<PermanentTransactions> permanentTransactionsSet) {
        this.permanentTransactionsSet = permanentTransactionsSet;
    }

    public Set<PermanentTransactions> getPermanentTransactionsSet1() {
        return permanentTransactionsSet1;
    }

    public void setPermanentTransactionsSet1(Set<PermanentTransactions> permanentTransactionsSet1) {
        this.permanentTransactionsSet1 = permanentTransactionsSet1;
    }

    public Set<DirectDebits> getDirectDebitsSet() {
        return directDebitsSet;
    }

    public void setDirectDebitsSet(Set<DirectDebits> directDebitsSet) {
        this.directDebitsSet = directDebitsSet;
    }

    public Set<DirectDebits> getDirectDebitsSet1() {
        return directDebitsSet1;
    }

    public void setDirectDebitsSet1(Set<DirectDebits> directDebitsSet1) {
        this.directDebitsSet1 = directDebitsSet1;
    }

    public Currencies getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currencies currencyId) {
        this.currencyId = currencyId;
    }

    public Clients getClientId() {
        return clientId;
    }

    public void setClientId(Clients clientId) {
        this.clientId = clientId;
    }

    public AccountInfo getInfoId() {
        return infoId;
    }

    public void setInfoId(AccountInfo infoId) {
        this.infoId = infoId;
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
        if (!(object instanceof Accounts)) {
            return false;
        }
        Accounts other = (Accounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banking.chestnut.Accounts[ id=" + id + " ]";
    }
    
}
