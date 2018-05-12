/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author karen
 */
@Entity
@Table(name = "tbl_entrenamiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entrenamiento.findAll", query = "SELECT e FROM Entrenamiento e")
    , @NamedQuery(name = "Entrenamiento.findByIdEntrenamiento", query = "SELECT e FROM Entrenamiento e WHERE e.idEntrenamiento = :idEntrenamiento")
    , @NamedQuery(name = "Entrenamiento.findByFechaInicio", query = "SELECT e FROM Entrenamiento e WHERE e.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Entrenamiento.findByFechaFin", query = "SELECT e FROM Entrenamiento e WHERE e.fechaFin = :fechaFin")})
public class Entrenamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_entrenamiento")
    private Integer idEntrenamiento;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @JoinColumn(name = "id_categoria_deportista", referencedColumnName = "id_categoria_deportista")
    @ManyToOne
    private CategoriaDeportista idCategoriaDeportista;
    @JoinColumn(name = "id_entrenador", referencedColumnName = "id_entrenador")
    @ManyToOne
    private Entrenador idEntrenador;
    @OneToMany(mappedBy = "entrenamientoPadre")
    private List<Entrenamiento> entrenamientoList;
    @JoinColumn(name = "entrenamiento_padre", referencedColumnName = "id_entrenamiento")
    @ManyToOne
    private Entrenamiento entrenamientoPadre;
    @OneToMany(mappedBy = "idEntrenamiento")
    private List<Rms> rmsList;

    public Entrenamiento() {
    }

    public Entrenamiento(Integer idEntrenamiento) {
        this.idEntrenamiento = idEntrenamiento;
    }

    public Entrenamiento(Integer idEntrenamiento, Date fechaInicio, Date fechaFin) {
        this.idEntrenamiento = idEntrenamiento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Integer getIdEntrenamiento() {
        return idEntrenamiento;
    }

    public void setIdEntrenamiento(Integer idEntrenamiento) {
        this.idEntrenamiento = idEntrenamiento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public CategoriaDeportista getIdCategoriaDeportista() {
        return idCategoriaDeportista;
    }

    public void setIdCategoriaDeportista(CategoriaDeportista idCategoriaDeportista) {
        this.idCategoriaDeportista = idCategoriaDeportista;
    }

    public Entrenador getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(Entrenador idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    @XmlTransient
    public List<Entrenamiento> getEntrenamientoList() {
        return entrenamientoList;
    }

    public void setEntrenamientoList(List<Entrenamiento> entrenamientoList) {
        this.entrenamientoList = entrenamientoList;
    }

    public Entrenamiento getEntrenamientoPadre() {
        return entrenamientoPadre;
    }

    public void setEntrenamientoPadre(Entrenamiento entrenamientoPadre) {
        this.entrenamientoPadre = entrenamientoPadre;
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
        hash += (idEntrenamiento != null ? idEntrenamiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entrenamiento)) {
            return false;
        }
        Entrenamiento other = (Entrenamiento) object;
        if ((this.idEntrenamiento == null && other.idEntrenamiento != null) || (this.idEntrenamiento != null && !this.idEntrenamiento.equals(other.idEntrenamiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.Entrenamiento[ idEntrenamiento=" + idEntrenamiento + " ]";
    }
    
}
