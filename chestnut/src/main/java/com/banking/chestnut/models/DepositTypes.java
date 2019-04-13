/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.chestnut.models;

import java.io.Serializable;
import java.util.List;
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
@Table(name = "deposit_types")
public class DepositTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "amount")
    private Long amount;
    @Column(name = "days_period")
    private Integer daysPeriod;
    @Column(name = "interest_rate")
    private Long interestRate;
    @Column(name = "min_amount")
    private Long minAmount;
    @Size(max = 255)
    @Column(name = "name_deposit")
    private String nameDeposit;
    @JoinColumn(name = "capitalization_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Capitalization capitalizationId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depositTypeId")
    private transient List<Deposits> depositsSet;

    public DepositTypes() {
    }

    public DepositTypes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getDaysPeriod() {
        return daysPeriod;
    }

    public void setDaysPeriod(Integer daysPeriod) {
        this.daysPeriod = daysPeriod;
    }

    public Long getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Long interestRate) {
        this.interestRate = interestRate;
    }

    public Long getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Long minAmount) {
        this.minAmount = minAmount;
    }

    public String getNameDeposit() {
        return nameDeposit;
    }

    public void setNameDeposit(String nameDeposit) {
        this.nameDeposit = nameDeposit;
    }

    public Capitalization getCapitalizationId() {
        return capitalizationId;
    }

    public void setCapitalizationId(Capitalization capitalizationId) {
        this.capitalizationId = capitalizationId;
    }

    public List<Deposits> getDepositsSet() {
        return depositsSet;
    }

    public void setDepositsSet(List<Deposits> depositsSet) {
        this.depositsSet = depositsSet;
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
        if (!(object instanceof DepositTypes)) {
            return false;
        }
        DepositTypes other = (DepositTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banking.chestnut.DepositTypes[ id=" + id + " ]";
    }
    
}
