/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.chestnut.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author macie
 */
@Entity
@Table(name = "permanent_transactions")
public class PermanentTransactions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date_from")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFrom;
    @Column(name = "date_to")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTo;
    @Column(name = "interval_transaction")
    private Integer intervalTransaction;
    @Column(name = "next_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nextDate;
    @Size(max = 255)
    @Column(name = "title")
    private String title;
    @Column(name = "value")
    private long value;
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    @ManyToOne
    private Account senderId;
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    @ManyToOne
    private Account receiverId;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private User deletedBy;
    @Column(name = "is_enabled")
    private boolean isEnabled;

    public PermanentTransactions() {
    }

    public PermanentTransactions(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getIntervalTransaction() {
        return intervalTransaction;
    }

    public void setIntervalTransaction(Integer intervalTransaction) {
        this.intervalTransaction = intervalTransaction;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Account getSenderId() {
        return senderId;
    }

    public void setSenderId(Account senderId) {
        this.senderId = senderId;
    }

    public Account getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Account receiverId) {
        this.receiverId = receiverId;
    }

    public User getDeletedBy() {return deletedBy; }

    public void setDeletedBy(User deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedAt() {return deletedAt; }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
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
        if (!(object instanceof PermanentTransactions)) {
            return false;
        }
        PermanentTransactions other = (PermanentTransactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banking.chestnut.PermanentTransactions[ id=" + id + " ]";
    }

}
