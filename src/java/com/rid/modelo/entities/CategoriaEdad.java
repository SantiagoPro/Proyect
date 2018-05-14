/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.entities;

import java.io.Serializable;
import java.util.List;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author karen
 */
@Entity
@Table(name = "tbl_categoria_edad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaEdad.findAll", query = "SELECT c FROM CategoriaEdad c")
    , @NamedQuery(name = "CategoriaEdad.findByIdCategoriaEdad", query = "SELECT c FROM CategoriaEdad c WHERE c.idCategoriaEdad = :idCategoriaEdad")
    , @NamedQuery(name = "CategoriaEdad.findByNombre", query = "SELECT c FROM CategoriaEdad c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "CategoriaEdad.findByEdadMaxima", query = "SELECT c FROM CategoriaEdad c WHERE c.edadMaxima = :edadMaxima")
    , @NamedQuery(name = "CategoriaEdad.findByEdadMinima", query = "SELECT c FROM CategoriaEdad c WHERE c.edadMinima = :edadMinima")})
public class CategoriaEdad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_categoria_edad")
    private Integer idCategoriaEdad;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "edad_maxima")
    private String edadMaxima;
    @Basic(optional = false)
    @Column(name = "edad_minima")
    private String edadMinima;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCategoriaEdad")
    private List<Categoria> categoriaList;

    public CategoriaEdad() {
    }

    public CategoriaEdad(Integer idCategoriaEdad) {
        this.idCategoriaEdad = idCategoriaEdad;
    }

    public CategoriaEdad(Integer idCategoriaEdad, String nombre, String edadMaxima, String edadMinima) {
        this.idCategoriaEdad = idCategoriaEdad;
        this.nombre = nombre;
        this.edadMaxima = edadMaxima;
        this.edadMinima = edadMinima;
    }

    public Integer getIdCategoriaEdad() {
        return idCategoriaEdad;
    }

    public void setIdCategoriaEdad(Integer idCategoriaEdad) {
        this.idCategoriaEdad = idCategoriaEdad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdadMaxima() {
        return edadMaxima;
    }

    public void setEdadMaxima(String edadMaxima) {
        this.edadMaxima = edadMaxima;
    }

    public String getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(String edadMinima) {
        this.edadMinima = edadMinima;
    }

    @XmlTransient
    public List<Categoria> getCategoriaList() {
        return categoriaList;
    }

    public void setCategoriaList(List<Categoria> categoriaList) {
        this.categoriaList = categoriaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoriaEdad != null ? idCategoriaEdad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaEdad)) {
            return false;
        }
        CategoriaEdad other = (CategoriaEdad) object;
        if ((this.idCategoriaEdad == null && other.idCategoriaEdad != null) || (this.idCategoriaEdad != null && !this.idCategoriaEdad.equals(other.idCategoriaEdad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.CategoriaEdad[ idCategoriaEdad=" + idCategoriaEdad + " ]";
    }
    
}
