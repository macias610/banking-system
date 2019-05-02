/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.chestnut.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author macie
 */
@Entity
@Table(name = "account_info")
public class AccountInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "available_amount")
    private Long availableAmount;
    @Column(name = "locked_amount")
    private Long lockedAmount;
    @OneToMany(mappedBy = "infoId")
    @JsonIgnore
    private List<Account> accountSet;

    public AccountInfo() {
        this.availableAmount = new Long(0);
        this.lockedAmount = new Long(500);
    }

    public AccountInfo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(Long availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Long getLockedAmount() {
        return lockedAmount;
    }

    public void setLockedAmount(Long lockedAmount) {
        this.lockedAmount = lockedAmount;
    }

    public List<Account> getAccountSet() {
        return accountSet;
    }

    public void setAccountSet(List<Account> accountSet) {
        this.accountSet = accountSet;
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
        if (!(object instanceof AccountInfo)) {
            return false;
        }
        AccountInfo other = (AccountInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banking.chestnut.AccountInfo[ id=" + id + " ]";
    }
    
}
