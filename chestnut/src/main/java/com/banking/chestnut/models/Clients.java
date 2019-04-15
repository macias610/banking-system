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
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author macie
 */
@Entity
@Table(name = "clients")
public class Clients implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "uuid")
    private String uuid;
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    @ManyToOne
    private Banks bankId;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    @JoinColumn(name = "credit_category_id", referencedColumnName = "id")
    @ManyToOne
    private CreditCategories creditCategoryId;
    @JoinColumn(name = "introductor_id", referencedColumnName = "id")
    @ManyToOne
    private Users introductorId;
    @JoinColumn(name = "client_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ClientTypes clientTypeId;
    @JoinColumn(name = "client_info_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ClientsInfo clientInfoId;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private Users createdBy;
    @JoinColumn(name = "deleted_by", referencedColumnName = "id")
    @ManyToOne
    private Users deletedBy;
    @JoinColumn(name = "client_status_id", referencedColumnName = "id")
    @ManyToOne
    private ClientStatuses clientStatusId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private transient List<Blacklist> blacklistList;
    @OneToOne(mappedBy = "clientId")
    private Locations location;
    @OneToMany(mappedBy = "clientId")
    private transient List<Contacts> contacts;

    public Clients() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Clients(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Banks getBankId() {
        return bankId;
    }

    public void setBankId(Banks bankId) {
        this.bankId = bankId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public CreditCategories getCreditCategoryId() {
        return creditCategoryId;
    }

    public void setCreditCategoryId(CreditCategories creditCategoryId) {
        this.creditCategoryId = creditCategoryId;
    }

    public Users getIntroductorId() {
        return introductorId;
    }

    public void setIntroductorId(Users introductorId) {
        this.introductorId = introductorId;
    }

    public ClientTypes getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(ClientTypes clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public ClientsInfo getClientInfoId() {
        return clientInfoId;
    }

    public void setClientInfoId(ClientsInfo clientInfoId) {
        this.clientInfoId = clientInfoId;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public Users getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Users deletedBy) {
        this.deletedBy = deletedBy;
    }

    public ClientStatuses getClientStatusId() {
        return clientStatusId;
    }

    public void setClientStatusId(ClientStatuses clientStatusId) {
        this.clientStatusId = clientStatusId;
    }

    public List<Blacklist> getBlacklistList() {
        return blacklistList;
    }

    public void setBlacklistList(List<Blacklist> blacklistList) {
        this.blacklistList = blacklistList;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }

    public List<Contacts> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contacts> contacts) {
        this.contacts = contacts;
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
        if (!(object instanceof Clients)) {
            return false;
        }
        Clients other = (Clients) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banking.chestnut.Clients[ id=" + id + " ]";
    }

}
