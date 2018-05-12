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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author karen
 */
@Entity
@Table(name = "tbl_torneo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Torneo.findAll", query = "SELECT t FROM Torneo t")
    , @NamedQuery(name = "Torneo.findByIdTorneo", query = "SELECT t FROM Torneo t WHERE t.idTorneo = :idTorneo")
    , @NamedQuery(name = "Torneo.findByNombre", query = "SELECT t FROM Torneo t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Torneo.findByFecha", query = "SELECT t FROM Torneo t WHERE t.fecha = :fecha")
    , @NamedQuery(name = "Torneo.findByLugar", query = "SELECT t FROM Torneo t WHERE t.lugar = :lugar")})
public class Torneo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_torneo")
    private Integer idTorneo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "lugar")
    private String lugar;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTorneo")
    private List<Participacion> participacionList;

    public Torneo() {
    }

    public Torneo(Integer idTorneo) {
        this.idTorneo = idTorneo;
    }

    public Torneo(Integer idTorneo, String nombre, Date fecha, String lugar) {
        this.idTorneo = idTorneo;
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
    }

    public Integer getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(Integer idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
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
        hash += (idTorneo != null ? idTorneo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Torneo)) {
            return false;
        }
        Torneo other = (Torneo) object;
        if ((this.idTorneo == null && other.idTorneo != null) || (this.idTorneo != null && !this.idTorneo.equals(other.idTorneo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.Torneo[ idTorneo=" + idTorneo + " ]";
    }
    
}
