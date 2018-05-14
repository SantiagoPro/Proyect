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
@Table(name = "tbl_tipo_entrenamiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEntrenamiento.findAll", query = "SELECT t FROM TipoEntrenamiento t")
    , @NamedQuery(name = "TipoEntrenamiento.findByIdTipoEntrenamiento", query = "SELECT t FROM TipoEntrenamiento t WHERE t.idTipoEntrenamiento = :idTipoEntrenamiento")
    , @NamedQuery(name = "TipoEntrenamiento.findByNombre", query = "SELECT t FROM TipoEntrenamiento t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TipoEntrenamiento.findByDescripcion", query = "SELECT t FROM TipoEntrenamiento t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "TipoEntrenamiento.findByMinimoRepeticiones", query = "SELECT t FROM TipoEntrenamiento t WHERE t.minimoRepeticiones = :minimoRepeticiones")
    , @NamedQuery(name = "TipoEntrenamiento.findByMaximoRepeticiones", query = "SELECT t FROM TipoEntrenamiento t WHERE t.maximoRepeticiones = :maximoRepeticiones")
    , @NamedQuery(name = "TipoEntrenamiento.findByMinimoSeries", query = "SELECT t FROM TipoEntrenamiento t WHERE t.minimoSeries = :minimoSeries")
    , @NamedQuery(name = "TipoEntrenamiento.findByMaximoSeries", query = "SELECT t FROM TipoEntrenamiento t WHERE t.maximoSeries = :maximoSeries")})
public class TipoEntrenamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tipo_entrenamiento")
    private Integer idTipoEntrenamiento;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "minimo_repeticiones")
    private String minimoRepeticiones;
    @Basic(optional = false)
    @Column(name = "maximo_repeticiones")
    private String maximoRepeticiones;
    @Basic(optional = false)
    @Column(name = "minimo_series")
    private String minimoSeries;
    @Basic(optional = false)
    @Column(name = "maximo_series")
    private String maximoSeries;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoEntrenamiento")
    private List<PorcentajeCarga> porcentajeCargaList;
    @OneToMany(mappedBy = "idTipoEntrenamiento")
    private List<Rms> rmsList;

    public TipoEntrenamiento() {
    }

    public TipoEntrenamiento(Integer idTipoEntrenamiento) {
        this.idTipoEntrenamiento = idTipoEntrenamiento;
    }

    public TipoEntrenamiento(Integer idTipoEntrenamiento, String nombre, String descripcion, String minimoRepeticiones, String maximoRepeticiones, String minimoSeries, String maximoSeries) {
        this.idTipoEntrenamiento = idTipoEntrenamiento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.minimoRepeticiones = minimoRepeticiones;
        this.maximoRepeticiones = maximoRepeticiones;
        this.minimoSeries = minimoSeries;
        this.maximoSeries = maximoSeries;
    }

    public Integer getIdTipoEntrenamiento() {
        return idTipoEntrenamiento;
    }

    public void setIdTipoEntrenamiento(Integer idTipoEntrenamiento) {
        this.idTipoEntrenamiento = idTipoEntrenamiento;
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

    public String getMinimoRepeticiones() {
        return minimoRepeticiones;
    }

    public void setMinimoRepeticiones(String minimoRepeticiones) {
        this.minimoRepeticiones = minimoRepeticiones;
    }

    public String getMaximoRepeticiones() {
        return maximoRepeticiones;
    }

    public void setMaximoRepeticiones(String maximoRepeticiones) {
        this.maximoRepeticiones = maximoRepeticiones;
    }

    public String getMinimoSeries() {
        return minimoSeries;
    }

    public void setMinimoSeries(String minimoSeries) {
        this.minimoSeries = minimoSeries;
    }

    public String getMaximoSeries() {
        return maximoSeries;
    }

    public void setMaximoSeries(String maximoSeries) {
        this.maximoSeries = maximoSeries;
    }

    @XmlTransient
    public List<PorcentajeCarga> getPorcentajeCargaList() {
        return porcentajeCargaList;
    }

    public void setPorcentajeCargaList(List<PorcentajeCarga> porcentajeCargaList) {
        this.porcentajeCargaList = porcentajeCargaList;
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
        hash += (idTipoEntrenamiento != null ? idTipoEntrenamiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEntrenamiento)) {
            return false;
        }
        TipoEntrenamiento other = (TipoEntrenamiento) object;
        if ((this.idTipoEntrenamiento == null && other.idTipoEntrenamiento != null) || (this.idTipoEntrenamiento != null && !this.idTipoEntrenamiento.equals(other.idTipoEntrenamiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.TipoEntrenamiento[ idTipoEntrenamiento=" + idTipoEntrenamiento + " ]";
    }
    
}
