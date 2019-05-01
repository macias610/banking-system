/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.chestnut.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author macie
 */
@Entity
@Table(name = "clients")
@Data
public class Client implements Serializable {
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
    private User introductorId;
    @JoinColumn(name = "client_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ClientTypes clientTypeId;
    @JoinColumn(name = "client_info_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ClientInfo clientInfoId;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private User createdBy;
    @JoinColumn(name = "deleted_by", referencedColumnName = "id")
    @ManyToOne
    private User deletedBy;
    @Column(name = "client_status")
    private String clientStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private transient List<Blacklist> blacklistList;
    @OneToOne(mappedBy = "clientId")
    private Location location;
    @OneToMany(mappedBy = "clientId")
    private List<Contacts> contacts;
    @OneToMany(mappedBy = "clientId")
    private List<Document> documents;

    @JsonIgnore
    @OneToMany(mappedBy = "clientId", fetch = FetchType.LAZY)
    private List<Account> accounts;

    public Client() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Client(Integer id) {
        this.id = id;
    }

    public Client prepare(){
        Client client = new Client(this.id);
        client.setLocation(this.location);
        client.getLocation().setCreatedBy(null);
        client.getLocation().setClientId(null);
        client.setClientInfoId(this.clientInfoId);
        client.setBankId(this.bankId);
        client.setUuid(this.uuid);
        client.setClientStatus(this.clientStatus);
        client.setClientTypeId(this.clientTypeId);
        client.setIsActive(this.isActive);
        client.setCreatedAt(this.createdAt);
        client.setDocuments(this.documents);
        client.setContacts(this.contacts);
        return client;
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
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banking.chestnut.Client[ id=" + id + " ]";
    }

}
