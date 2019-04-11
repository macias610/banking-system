/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.chestnut.models;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "bank_id")
    private int bankId;
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
    @OneToMany(mappedBy = "clientId")
    private Set<CreditLimitClients> creditLimitClientsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Set<LoginHistory> loginHistorySet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Set<Blacklist> blacklistSet;
    @OneToMany(mappedBy = "clientId")
    private Set<ClientTaxes> clientTaxesSet;
    @OneToMany(mappedBy = "clientId")
    private Set<Locations> locationsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Set<Accounts> accountsSet;
    @OneToMany(mappedBy = "clientId")
    private Set<Contacts> contactsSet;

    public Clients() {
    }

    public Clients(Integer id) {
        this.id = id;
    }

    public Clients(Integer id, int bankId) {
        this.id = id;
        this.bankId = bankId;
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

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
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

    public Set<CreditLimitClients> getCreditLimitClientsSet() {
        return creditLimitClientsSet;
    }

    public void setCreditLimitClientsSet(Set<CreditLimitClients> creditLimitClientsSet) {
        this.creditLimitClientsSet = creditLimitClientsSet;
    }

    public Set<LoginHistory> getLoginHistorySet() {
        return loginHistorySet;
    }

    public void setLoginHistorySet(Set<LoginHistory> loginHistorySet) {
        this.loginHistorySet = loginHistorySet;
    }

    public Set<Blacklist> getBlacklistSet() {
        return blacklistSet;
    }

    public void setBlacklistSet(Set<Blacklist> blacklistSet) {
        this.blacklistSet = blacklistSet;
    }

    public Set<ClientTaxes> getClientTaxesSet() {
        return clientTaxesSet;
    }

    public void setClientTaxesSet(Set<ClientTaxes> clientTaxesSet) {
        this.clientTaxesSet = clientTaxesSet;
    }

    public Set<Locations> getLocationsSet() {
        return locationsSet;
    }

    public void setLocationsSet(Set<Locations> locationsSet) {
        this.locationsSet = locationsSet;
    }

    public Set<Accounts> getAccountsSet() {
        return accountsSet;
    }

    public void setAccountsSet(Set<Accounts> accountsSet) {
        this.accountsSet = accountsSet;
    }

    public Set<Contacts> getContactsSet() {
        return contactsSet;
    }

    public void setContactsSet(Set<Contacts> contactsSet) {
        this.contactsSet = contactsSet;
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
