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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "tbl_permiso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permiso.findAll", query = "SELECT p FROM Permiso p")
    , @NamedQuery(name = "Permiso.findByIdPermiso", query = "SELECT p FROM Permiso p WHERE p.idPermiso = :idPermiso")
    , @NamedQuery(name = "Permiso.findByNombre", query = "SELECT p FROM Permiso p WHERE p.nombre = :nombre")})
public class Permiso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_permiso")
    private Integer idPermiso;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Column(name = "url")
    private String url;
    @JoinTable(name = "tbl_rol_permiso", joinColumns = {
        @JoinColumn(name = "id_permiso", referencedColumnName = "id_permiso")}, inverseJoinColumns = {
        @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")})
    @ManyToMany
    private List<Rol> rolList;
    @OneToMany(mappedBy = "permisoPadre")
    private List<Permiso> permisoList;
    @JoinColumn(name = "permiso_padre", referencedColumnName = "id_permiso")
    @ManyToOne
    private Permiso permisoPadre;

    public Permiso() {
    }

    public Permiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }

    public Permiso(Integer idPermiso, String nombre) {
        this.idPermiso = idPermiso;
        this.nombre = nombre;
    }

    public Integer getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlTransient
    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    @XmlTransient
    public List<Permiso> getPermisoList() {
        return permisoList;
    }

    public void setPermisoList(List<Permiso> permisoList) {
        this.permisoList = permisoList;
    }

    public Permiso getPermisoPadre() {
        return permisoPadre;
    }

    public void setPermisoPadre(Permiso permisoPadre) {
        this.permisoPadre = permisoPadre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPermiso != null ? idPermiso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permiso)) {
            return false;
        }
        Permiso other = (Permiso) object;
        if ((this.idPermiso == null && other.idPermiso != null) || (this.idPermiso != null && !this.idPermiso.equals(other.idPermiso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.Permiso[ idPermiso=" + idPermiso + " ]";
    }
    
}
