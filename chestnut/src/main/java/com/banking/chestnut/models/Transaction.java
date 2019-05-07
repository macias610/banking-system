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
@Table(name = "transactions")
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_foreign")
    private Boolean isForeign;
    @Column(name = "is_via_bank")
    private Boolean isViaBank;
    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @Size(max = 255)
    @Column(name = "type")
    private String type;
    @Column(name = "value")
    private Long value;
    @Column(name = "is_transfer_client_acconuts")
    private Boolean isTransferClientAcconuts;
    @Size(max = 255)
    @Column(name = "title")
    private String title;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private User createdBy;
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Account senderId;
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Account receiverId;

    public Transaction() {
        this.title = "Undefined";
    }

    public void setPayoutTransation(Long value, User user, Account account){
        this.type = "OUT";
        this.isTransferClientAcconuts = false;
        this.value = value;
        this.title = "Payout from account";
        this.senderId = account;
        this.createdAt = new Date();
        this.transactionDate = new Date();
        this.isTransferClientAcconuts = false;
        this.isViaBank = false;
        this.isForeign = false;
        this.createdBy = user;
    }

    public Transaction(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsForeign() {
        return isForeign;
    }

    public void setIsForeign(Boolean isForeign) {
        this.isForeign = isForeign;
    }

    public Boolean getIsViaBank() {
        return isViaBank;
    }

    public void setIsViaBank(Boolean isViaBank) {
        this.isViaBank = isViaBank;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Boolean getIsTransferClientAcconuts() {
        return isTransferClientAcconuts;
    }

    public void setIsTransferClientAcconuts(Boolean isTransferClientAcconuts) {
        this.isTransferClientAcconuts = isTransferClientAcconuts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banking.chestnut.Transaction[ id=" + id + " ]";
    }
    
}
