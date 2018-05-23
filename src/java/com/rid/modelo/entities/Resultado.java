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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "tbl_resultado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resultado.findAll", query = "SELECT r FROM Resultado r")
    , @NamedQuery(name = "Resultado.findByIdResultado", query = "SELECT r FROM Resultado r WHERE r.idResultado = :idResultado")
    , @NamedQuery(name = "Resultado.findByPesoArranque", query = "SELECT r FROM Resultado r WHERE r.pesoArranque = :pesoArranque")
    , @NamedQuery(name = "Resultado.findByPesoEnvion", query = "SELECT r FROM Resultado r WHERE r.pesoEnvion = :pesoEnvion")})
public class Resultado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_resultado")
    private Integer idResultado;
    @Basic(optional = false)
    @Column(name = "peso_arranque")
        private Integer pesoArranque;
    @Basic(optional = false)
    @Column(name = "peso_envion")
    private Integer pesoEnvion;
    @JoinColumn(name = "id_participacion", referencedColumnName = "id_participacion")
    @ManyToOne(optional = false)
    private Participacion idParticipacion;

    public Resultado() {
    }

    public Resultado(Integer idResultado) {
        this.idResultado = idResultado;
    }

    public Resultado(Integer idResultado, int pesoArranque, int pesoEnvion) {
        this.idResultado = idResultado;
        this.pesoArranque = pesoArranque;
        this.pesoEnvion = pesoEnvion;
    }

    public Integer getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(Integer idResultado) {
        this.idResultado = idResultado;
    }

    public Integer getPesoArranque() {
        return pesoArranque;
    }

    public void setPesoArranque(Integer pesoArranque) {
        this.pesoArranque = pesoArranque;
    }

    public Integer getPesoEnvion() {
        return pesoEnvion;
    }

    public void setPesoEnvion(Integer pesoEnvion) {
        this.pesoEnvion = pesoEnvion;
    }

    public Participacion getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(Participacion idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResultado != null ? idResultado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resultado)) {
            return false;
        }
        Resultado other = (Resultado) object;
        if ((this.idResultado == null && other.idResultado != null) || (this.idResultado != null && !this.idResultado.equals(other.idResultado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.Resultado[ idResultado=" + idResultado + " ]";
    }
    
}
