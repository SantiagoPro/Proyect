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
@Table(name = "tbl_porcentajecarga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PorcentajeCarga.findAll", query = "SELECT p FROM PorcentajeCarga p")
    , @NamedQuery(name = "PorcentajeCarga.findByIdPorcentaje", query = "SELECT p FROM PorcentajeCarga p WHERE p.idPorcentaje = :idPorcentaje")
    , @NamedQuery(name = "PorcentajeCarga.findByPorcentaje", query = "SELECT p FROM PorcentajeCarga p WHERE p.porcentaje = :porcentaje")})
public class PorcentajeCarga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_porcentaje")
    private Integer idPorcentaje;
    @Basic(optional = false)
    @Column(name = "porcentaje")
    private double porcentaje;
    @JoinColumn(name = "id_tipo_entrenamiento", referencedColumnName = "id_tipo_entrenamiento")
    @ManyToOne(optional = false)
    private TipoEntrenamiento idTipoEntrenamiento;

    public PorcentajeCarga() {
    }

    public PorcentajeCarga(Integer idPorcentaje) {
        this.idPorcentaje = idPorcentaje;
    }

    public PorcentajeCarga(Integer idPorcentaje, double porcentaje) {
        this.idPorcentaje = idPorcentaje;
        this.porcentaje = porcentaje;
    }

    public Integer getIdPorcentaje() {
        return idPorcentaje;
    }

    public void setIdPorcentaje(Integer idPorcentaje) {
        this.idPorcentaje = idPorcentaje;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public TipoEntrenamiento getIdTipoEntrenamiento() {
        return idTipoEntrenamiento;
    }

    public void setIdTipoEntrenamiento(TipoEntrenamiento idTipoEntrenamiento) {
        this.idTipoEntrenamiento = idTipoEntrenamiento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPorcentaje != null ? idPorcentaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PorcentajeCarga)) {
            return false;
        }
        PorcentajeCarga other = (PorcentajeCarga) object;
        if ((this.idPorcentaje == null && other.idPorcentaje != null) || (this.idPorcentaje != null && !this.idPorcentaje.equals(other.idPorcentaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.PorcentajeCarga[ idPorcentaje=" + idPorcentaje + " ]";
    }
    
}
