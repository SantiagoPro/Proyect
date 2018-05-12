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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tbl_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trabajo.findAll", query = "SELECT t FROM Trabajo t")
    , @NamedQuery(name = "Trabajo.findByIdTrabajo", query = "SELECT t FROM Trabajo t WHERE t.idTrabajo = :idTrabajo")
    , @NamedQuery(name = "Trabajo.findByNombre", query = "SELECT t FROM Trabajo t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Trabajo.findByDescripcion", query = "SELECT t FROM Trabajo t WHERE t.descripcion = :descripcion")})
public class Trabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_trabajo")
    private Integer idTrabajo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_tipo_trabajo", referencedColumnName = "id_tipo_trabajo")
    @ManyToOne
    private TipoTrabajo idTipoTrabajo;
    @OneToMany(mappedBy = "idTrabajo")
    private List<Rms> rmsList;

    public Trabajo() {
    }

    public Trabajo(Integer idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public Trabajo(Integer idTrabajo, String nombre, String descripcion) {
        this.idTrabajo = idTrabajo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(Integer idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoTrabajo getIdTipoTrabajo() {
        return idTipoTrabajo;
    }

    public void setIdTipoTrabajo(TipoTrabajo idTipoTrabajo) {
        this.idTipoTrabajo = idTipoTrabajo;
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
        hash += (idTrabajo != null ? idTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trabajo)) {
            return false;
        }
        Trabajo other = (Trabajo) object;
        if ((this.idTrabajo == null && other.idTrabajo != null) || (this.idTrabajo != null && !this.idTrabajo.equals(other.idTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.Trabajo[ idTrabajo=" + idTrabajo + " ]";
    }
    
}
