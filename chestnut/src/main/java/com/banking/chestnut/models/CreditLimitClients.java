/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.chestnut.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author macie
 */
@Entity
@Table(name = "credit_limit_clients")
public class CreditLimitClients implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne
    private Client clientId;
    @JoinColumn(name = "credit_limit_id", referencedColumnName = "id")
    @ManyToOne
    private CreditLimits creditLimitId;

    public CreditLimitClients() {
    }

    public CreditLimitClients(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public CreditLimits getCreditLimitId() {
        return creditLimitId;
    }

    public void setCreditLimitId(CreditLimits creditLimitId) {
        this.creditLimitId = creditLimitId;
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
        if (!(object instanceof CreditLimitClients)) {
            return false;
        }
        CreditLimitClients other = (CreditLimitClients) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banking.chestnut.CreditLimitClients[ id=" + id + " ]";
    }
    
}
