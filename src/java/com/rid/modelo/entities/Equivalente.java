/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author karen
 */
@Entity
@Table(name = "tbl_equivalente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equivalente.findAll", query = "SELECT e FROM Equivalente e")
    , @NamedQuery(name = "Equivalente.findByIdEquivalente", query = "SELECT e FROM Equivalente e WHERE e.idEquivalente = :idEquivalente")
    , @NamedQuery(name = "Equivalente.findByRepeticion", query = "SELECT e FROM Equivalente e WHERE e.repeticion = :repeticion")
    , @NamedQuery(name = "Equivalente.findByCoeficiente", query = "SELECT e FROM Equivalente e WHERE e.coeficiente = :coeficiente")})
public class Equivalente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_equivalente")
    private Integer idEquivalente;
    @Basic(optional = false)
    @Column(name = "repeticion")
    private int repeticion;
    @Basic(optional = false)
    @Column(name = "coeficiente")
    private double coeficiente;
    @OneToMany(mappedBy = "idEquivalente")
    private List<Rms> rmsList;

    public Equivalente() {
    }

    public Equivalente(Integer idEquivalente) {
        this.idEquivalente = idEquivalente;
    }

    public Equivalente(Integer idEquivalente, int repeticion, double coeficiente) {
        this.idEquivalente = idEquivalente;
        this.repeticion = repeticion;
        this.coeficiente = coeficiente;
    }

    public Integer getIdEquivalente() {
        return idEquivalente;
    }

    public void setIdEquivalente(Integer idEquivalente) {
        this.idEquivalente = idEquivalente;
    }

    public int getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(int repeticion) {
        this.repeticion = repeticion;
    }

    public double getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(double coeficiente) {
        this.coeficiente = coeficiente;
    }

    @XmlTransient
    public List<Rms> getRmsList() {
        return rmsList;
    }

    public void setRmsList(List<Rms> rmsList) {
        this.rmsList = rmsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEquivalente != null ? idEquivalente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equivalente)) {
            return false;
        }
        Equivalente other = (Equivalente) object;
        if ((this.idEquivalente == null && other.idEquivalente != null) || (this.idEquivalente != null && !this.idEquivalente.equals(other.idEquivalente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.Equivalente[ idEquivalente=" + idEquivalente + " ]";
    }
    
}
