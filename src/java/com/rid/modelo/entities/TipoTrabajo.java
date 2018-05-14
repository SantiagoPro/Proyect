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
@Table(name = "tbl_tipo_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTrabajo.findAll", query = "SELECT t FROM TipoTrabajo t")
    , @NamedQuery(name = "TipoTrabajo.findByIdTipoTrabajo", query = "SELECT t FROM TipoTrabajo t WHERE t.idTipoTrabajo = :idTipoTrabajo")
    , @NamedQuery(name = "TipoTrabajo.findByNombre", query = "SELECT t FROM TipoTrabajo t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TipoTrabajo.findByDescripcion", query = "SELECT t FROM TipoTrabajo t WHERE t.descripcion = :descripcion")})
public class TipoTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tipo_trabajo")
    private Integer idTipoTrabajo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "idTipoTrabajo")
    private List<Trabajo> trabajoList;

    public TipoTrabajo() {
    }

    public TipoTrabajo(Integer idTipoTrabajo) {
        this.idTipoTrabajo = idTipoTrabajo;
    }

    public TipoTrabajo(Integer idTipoTrabajo, String nombre, String descripcion) {
        this.idTipoTrabajo = idTipoTrabajo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdTipoTrabajo() {
        return idTipoTrabajo;
    }

    public void setIdTipoTrabajo(Integer idTipoTrabajo) {
        this.idTipoTrabajo = idTipoTrabajo;
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

    @XmlTransient
    public List<Trabajo> getTrabajoList() {
        return trabajoList;
    }

    public void setTrabajoList(List<Trabajo> trabajoList) {
        this.trabajoList = trabajoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoTrabajo != null ? idTipoTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoTrabajo)) {
            return false;
        }
        TipoTrabajo other = (TipoTrabajo) object;
        if ((this.idTipoTrabajo == null && other.idTipoTrabajo != null) || (this.idTipoTrabajo != null && !this.idTipoTrabajo.equals(other.idTipoTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.TipoTrabajo[ idTipoTrabajo=" + idTipoTrabajo + " ]";
    }
    
}
