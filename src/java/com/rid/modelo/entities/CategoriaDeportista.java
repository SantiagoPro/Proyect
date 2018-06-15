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
@Table(name = "tbl_categoria_deportista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaDeportista.findAll", query = "SELECT c FROM CategoriaDeportista c")
    , @NamedQuery(name = "CategoriaDeportista.findByIdCategoriaDeportista", query = "SELECT c FROM CategoriaDeportista c WHERE c.idCategoriaDeportista = :idCategoriaDeportista")})
public class CategoriaDeportista implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_categoria_deportista")
    private Integer idCategoriaDeportista;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne
    private Categoria idCategoria;
    @JoinColumn(name = "id_deportista", referencedColumnName = "id_deportista")
    @ManyToOne(optional = false)
    private Deportista idDeportista;
    @Column(name = "estado_categoria")
    private Short estadoCategoria;
    @OneToMany(mappedBy = "idCategoriaDeportista")
    private List<Entrenamiento> entrenamientoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCategoriaDeportista")
    private List<Participacion> participacionList;

    public CategoriaDeportista() {
    }

    public CategoriaDeportista(Integer idCategoriaDeportista) {
        this.idCategoriaDeportista = idCategoriaDeportista;
    }

    public Integer getIdCategoriaDeportista() {
        return idCategoriaDeportista;
    }

    public void setIdCategoriaDeportista(Integer idCategoriaDeportista) {
        this.idCategoriaDeportista = idCategoriaDeportista;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Deportista getIdDeportista() {
        return idDeportista;
    }

    public void setIdDeportista(Deportista idDeportista) {
        this.idDeportista = idDeportista;
    }

    public Short getEstadoCategoria() {
        return estadoCategoria;
    }

    public void setEstadoCategoria(Short estadoCategoria) {
        this.estadoCategoria = estadoCategoria;
    }
    
    
    @XmlTransient
    public List<Entrenamiento> getEntrenamientoList() {
        return entrenamientoList;
    }

    public void setEntrenamientoList(List<Entrenamiento> entrenamientoList) {
        this.entrenamientoList = entrenamientoList;
    }

    @XmlTransient
    public List<Participacion> getParticipacionList() {
        return participacionList;
    }

    public void setParticipacionList(List<Participacion> participacionList) {
        this.participacionList = participacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoriaDeportista != null ? idCategoriaDeportista.hashCode() : 0);
        return hash;
    }

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaDeportista)) {
            return false;
        }
        CategoriaDeportista other = (CategoriaDeportista) object;
        if ((this.idCategoriaDeportista == null && other.idCategoriaDeportista != null) || (this.idCategoriaDeportista != null && !this.idCategoriaDeportista.equals(other.idCategoriaDeportista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.CategoriaDeportista[ idCategoriaDeportista=" + idCategoriaDeportista + " ]";
    }

    
}
