/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.chestnut.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author macie
 */
@Entity
@Table(name = "data_history_clients")
public class DataHistoryClient implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne
    private Client clientId;
    @Lob
    @Column(name = "before_history")
    private String beforeHistory;
    @Lob
    @Column(name = "after_history")
    private String afterHistory;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private User createdBy;

    public DataHistoryClient() {
    }

    public DataHistoryClient(Integer id) {
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

    public String getBeforeHistory() {
        return beforeHistory;
    }

    public void setBeforeHistory(String beforeHistory) {
        this.beforeHistory = beforeHistory;
    }

    public String getAfterHistory() {
        return afterHistory;
    }

    public void setAfterHistory(String afterHistory) {
        this.afterHistory = afterHistory;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataHistoryClient)) {
            return false;
        }
        DataHistoryClient other = (DataHistoryClient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banking.chestnut.DataHistoryClientService[ id=" + id + " ]";
    }

}
