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
@Table(name = "tbl_categoria_peso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaPeso.findAll", query = "SELECT c FROM CategoriaPeso c")
    , @NamedQuery(name = "CategoriaPeso.findByIdCategoriaPeso", query = "SELECT c FROM CategoriaPeso c WHERE c.idCategoriaPeso = :idCategoriaPeso")
    , @NamedQuery(name = "CategoriaPeso.findByNombre", query = "SELECT c FROM CategoriaPeso c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "CategoriaPeso.findByPesoMaximo", query = "SELECT c FROM CategoriaPeso c WHERE c.pesoMaximo = :pesoMaximo")
    , @NamedQuery(name = "CategoriaPeso.findByPesoMinimo", query = "SELECT c FROM CategoriaPeso c WHERE c.pesoMinimo = :pesoMinimo")
    , @NamedQuery(name = "CategoriaPeso.findByGenero", query = "SELECT c FROM CategoriaPeso c WHERE c.genero = :genero")})
public class CategoriaPeso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_categoria_peso")
    private Integer idCategoriaPeso;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "peso_maximo")
    private String pesoMaximo;
    @Basic(optional = false)
    @Column(name = "peso_minimo")
    private String pesoMinimo;
    @Basic(optional = false)
    @Column(name = "genero")
    private String genero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCategoriaPeso")
    private List<Categoria> categoriaList;

    public CategoriaPeso() {
    }

    public CategoriaPeso(Integer idCategoriaPeso) {
        this.idCategoriaPeso = idCategoriaPeso;
    }

    public CategoriaPeso(Integer idCategoriaPeso, String nombre, String pesoMaximo, String pesoMinimo, String genero) {
        this.idCategoriaPeso = idCategoriaPeso;
        this.nombre = nombre;
        this.pesoMaximo = pesoMaximo;
        this.pesoMinimo = pesoMinimo;
        this.genero = genero;
    }

    public Integer getIdCategoriaPeso() {
        return idCategoriaPeso;
    }

    public void setIdCategoriaPeso(Integer idCategoriaPeso) {
        this.idCategoriaPeso = idCategoriaPeso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(String pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    public String getPesoMinimo() {
        return pesoMinimo;
    }

    public void setPesoMinimo(String pesoMinimo) {
        this.pesoMinimo = pesoMinimo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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
        hash += (idCategoriaPeso != null ? idCategoriaPeso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaPeso)) {
            return false;
        }
        CategoriaPeso other = (CategoriaPeso) object;
        if ((this.idCategoriaPeso == null && other.idCategoriaPeso != null) || (this.idCategoriaPeso != null && !this.idCategoriaPeso.equals(other.idCategoriaPeso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.CategoriaPeso[ idCategoriaPeso=" + idCategoriaPeso + " ]";
    }
    
}
