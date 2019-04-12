/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.chestnut.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author macie
 */
@Entity
@Table(name = "payment_schedules")
public class PaymentSchedules implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @Column(name = "payment_assets")
    private Long paymentAssets;
    @Column(name = "payment_interest")
    private Long paymentInterest;
    @OneToMany(mappedBy = "paymentScheduleId")
    private transient List<Credits> creditsSet;

    public PaymentSchedules() {
    }

    public PaymentSchedules(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getPaymentAssets() {
        return paymentAssets;
    }

    public void setPaymentAssets(Long paymentAssets) {
        this.paymentAssets = paymentAssets;
    }

    public Long getPaymentInterest() {
        return paymentInterest;
    }

    public void setPaymentInterest(Long paymentInterest) {
        this.paymentInterest = paymentInterest;
    }

    public List<Credits> getCreditsSet() {
        return creditsSet;
    }

    public void setCreditsSet(List<Credits> creditsSet) {
        this.creditsSet = creditsSet;
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
        if (!(object instanceof PaymentSchedules)) {
            return false;
        }
        PaymentSchedules other = (PaymentSchedules) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banking.chestnut.PaymentSchedules[ id=" + id + " ]";
    }
    
}
