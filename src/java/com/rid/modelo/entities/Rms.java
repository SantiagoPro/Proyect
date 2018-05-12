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
@Table(name = "tbl_rms")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rms.findAll", query = "SELECT r FROM Rms r")
    , @NamedQuery(name = "Rms.findByIdRms", query = "SELECT r FROM Rms r WHERE r.idRms = :idRms")
    , @NamedQuery(name = "Rms.findByPesoMaximo", query = "SELECT r FROM Rms r WHERE r.pesoMaximo = :pesoMaximo")})
public class Rms implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_rms")
    private Integer idRms;
    @Basic(optional = false)
    @Column(name = "peso_maximo")
    private short pesoMaximo;
    @JoinColumn(name = "id_entrenamiento", referencedColumnName = "id_entrenamiento")
    @ManyToOne
    private Entrenamiento idEntrenamiento;
    @JoinColumn(name = "id_equivalente", referencedColumnName = "id_equivalente")
    @ManyToOne
    private Equivalente idEquivalente;
    @JoinColumn(name = "id_tipo_entrenamiento", referencedColumnName = "id_tipo_entrenamiento")
    @ManyToOne
    private TipoEntrenamiento idTipoEntrenamiento;
    @JoinColumn(name = "id_trabajo", referencedColumnName = "id_trabajo")
    @ManyToOne
    private Trabajo idTrabajo;

    public Rms() {
    }

    public Rms(Integer idRms) {
        this.idRms = idRms;
    }

    public Rms(Integer idRms, short pesoMaximo) {
        this.idRms = idRms;
        this.pesoMaximo = pesoMaximo;
    }

    public Integer getIdRms() {
        return idRms;
    }

    public void setIdRms(Integer idRms) {
        this.idRms = idRms;
    }

    public short getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(short pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    public Entrenamiento getIdEntrenamiento() {
        return idEntrenamiento;
    }

    public void setIdEntrenamiento(Entrenamiento idEntrenamiento) {
        this.idEntrenamiento = idEntrenamiento;
    }

    public Equivalente getIdEquivalente() {
        return idEquivalente;
    }

    public void setIdEquivalente(Equivalente idEquivalente) {
        this.idEquivalente = idEquivalente;
    }

    public TipoEntrenamiento getIdTipoEntrenamiento() {
        return idTipoEntrenamiento;
    }

    public void setIdTipoEntrenamiento(TipoEntrenamiento idTipoEntrenamiento) {
        this.idTipoEntrenamiento = idTipoEntrenamiento;
    }

    public Trabajo getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(Trabajo idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRms != null ? idRms.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rms)) {
            return false;
        }
        Rms other = (Rms) object;
        if ((this.idRms == null && other.idRms != null) || (this.idRms != null && !this.idRms.equals(other.idRms))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.Rms[ idRms=" + idRms + " ]";
    }
    
}
