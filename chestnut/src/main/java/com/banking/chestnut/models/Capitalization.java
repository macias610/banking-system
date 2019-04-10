/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.chestnut.models;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "capitalization")
public class Capitalization implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "days_period")
    private Integer daysPeriod;
    @Size(max = 255)
    @Column(name = "type_capitilization")
    private String typeCapitilization;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "capitalizationId")
    private Set<DepositTypes> depositTypesSet;

    public Capitalization() {
    }

    public Capitalization(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDaysPeriod() {
        return daysPeriod;
    }

    public void setDaysPeriod(Integer daysPeriod) {
        this.daysPeriod = daysPeriod;
    }

    public String getTypeCapitilization() {
        return typeCapitilization;
    }

    public void setTypeCapitilization(String typeCapitilization) {
        this.typeCapitilization = typeCapitilization;
    }

    public Set<DepositTypes> getDepositTypesSet() {
        return depositTypesSet;
    }

    public void setDepositTypesSet(Set<DepositTypes> depositTypesSet) {
        this.depositTypesSet = depositTypesSet;
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
        if (!(object instanceof Capitalization)) {
            return false;
        }
        Capitalization other = (Capitalization) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banking.chestnut.Capitalization[ id=" + id + " ]";
    }
    
}
