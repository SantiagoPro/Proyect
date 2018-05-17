/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karen
 */
@Entity
@Table(name = "tbl_imc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Imc.findAll", query = "SELECT i FROM Imc i")
    , @NamedQuery(name = "Imc.findByIdImc", query = "SELECT i FROM Imc i WHERE i.idImc = :idImc")
    , @NamedQuery(name = "Imc.findByValor", query = "SELECT i FROM Imc i WHERE i.valor = :valor")})
public class Imc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_imc")
    private Integer idImc;
    @Column(name = "valor")
    private Integer valor;
    @JoinColumn(name = "id_deportista", referencedColumnName = "id_deportista")
    @ManyToOne
    private Deportista idDeportista;

    public Imc() {
    }

    public Imc(Integer idImc) {
        this.idImc = idImc;
    }

    public Integer getIdImc() {
        return idImc;
    }

    public void setIdImc(Integer idImc) {
        this.idImc = idImc;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Deportista getIdDeportista() {
        return idDeportista;
    }

    public void setIdDeportista(Deportista idDeportista) {
        this.idDeportista = idDeportista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idImc != null ? idImc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imc)) {
            return false;
        }
        Imc other = (Imc) object;
        if ((this.idImc == null && other.idImc != null) || (this.idImc != null && !this.idImc.equals(other.idImc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.Imc[ idImc=" + idImc + " ]";
    }
    
}
