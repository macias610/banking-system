/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.chestnut.models;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
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
@Table(name = "users")
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "employee_number")
    private String employeeNumber;
    @Size(max = 255)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 255)
    @Column(name = "surname")
    private String surname;
    @OneToMany(mappedBy = "createdBy")
    private Set<AccountHistory> accountHistorySet;
    @OneToMany(mappedBy = "createdBy")
    private Set<Cards> cardsSet;
    @OneToMany(mappedBy = "introductorId")
    private Set<Clients> clientsSet;
    @OneToMany(mappedBy = "createdBy")
    private Set<Clients> clientsSet1;
    @OneToMany(mappedBy = "deletedBy")
    private Set<Clients> clientsSet2;
    @OneToMany(mappedBy = "createdBy")
    private Set<Credits> creditsSet;
    @OneToMany(mappedBy = "deletedBy")
    private Set<Credits> creditsSet1;
    @OneToMany(mappedBy = "createdBy")
    private Set<DepositOperations> depositOperationsSet;
    @OneToMany(mappedBy = "createdBy")
    private Set<Transactions> transactionsSet;
    @OneToMany(mappedBy = "createdBy")
    private Set<DataHistoryClients> dataHistoryClientsSet;
    @OneToMany(mappedBy = "createdBy")
    private Set<Deposits> depositsSet;
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    @ManyToOne
    private Banks bankId;
    @OneToMany(mappedBy = "createdBy")
    private Set<Locations> locationsSet;

    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public Set<Clients> getClientsSet() {
        return clientsSet;
    }

    public void setClientsSet(Set<Clients> clientsSet) {
        this.clientsSet = clientsSet;
    }

    public Set<Clients> getClientsSet1() {
        return clientsSet1;
    }

    public void setClientsSet1(Set<Clients> clientsSet1) {
        this.clientsSet1 = clientsSet1;
    }

    public Set<Clients> getClientsSet2() {
        return clientsSet2;
    }

    public void setClientsSet2(Set<Clients> clientsSet2) {
        this.clientsSet2 = clientsSet2;
    }

    public Set<Credits> getCreditsSet() {
        return creditsSet;
    }

    public void setCreditsSet(Set<Credits> creditsSet) {
        this.creditsSet = creditsSet;
    }

    public Set<Credits> getCreditsSet1() {
        return creditsSet1;
    }

    public void setCreditsSet1(Set<Credits> creditsSet1) {
        this.creditsSet1 = creditsSet1;
    }

    public Set<DepositOperations> getDepositOperationsSet() {
        return depositOperationsSet;
    }

    public void setDepositOperationsSet(Set<DepositOperations> depositOperationsSet) {
        this.depositOperationsSet = depositOperationsSet;
    }

    public Set<Transactions> getTransactionsSet() {
        return transactionsSet;
    }

    public void setTransactionsSet(Set<Transactions> transactionsSet) {
        this.transactionsSet = transactionsSet;
    }

    public Set<DataHistoryClients> getDataHistoryClientsSet() {
        return dataHistoryClientsSet;
    }

    public void setDataHistoryClientsSet(Set<DataHistoryClients> dataHistoryClientsSet) {
        this.dataHistoryClientsSet = dataHistoryClientsSet;
    }

    public Set<Deposits> getDepositsSet() {
        return depositsSet;
    }

    public void setDepositsSet(Set<Deposits> depositsSet) {
        this.depositsSet = depositsSet;
    }

    public Banks getBankId() {
        return bankId;
    }

    public void setBankId(Banks bankId) {
        this.bankId = bankId;
    }

    public Set<Locations> getLocationsSet() {
        return locationsSet;
    }

    public void setLocationsSet(Set<Locations> locationsSet) {
        this.locationsSet = locationsSet;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banking.chestnut.Users[ id=" + id + " ]";
    }
    
}
