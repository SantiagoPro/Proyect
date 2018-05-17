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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "tbl_horario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horario.findAll", query = "SELECT h FROM Horario h")
    , @NamedQuery(name = "Horario.findByIdHorario", query = "SELECT h FROM Horario h WHERE h.idHorario = :idHorario")
    , @NamedQuery(name = "Horario.findByLugar", query = "SELECT h FROM Horario h WHERE h.lugar = :lugar")
    , @NamedQuery(name = "Horario.findByHora", query = "SELECT h FROM Horario h WHERE h.hora = :hora")
    , @NamedQuery(name = "Horario.findByDias", query = "SELECT h FROM Horario h WHERE h.dias = :dias")})
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_horario")
    private Integer idHorario;
    @Basic(optional = false)
    @Column(name = "lugar")
    private String lugar;
    @Basic(optional = false)
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Basic(optional = false)
    @Column(name = "dias")
    @Temporal(TemporalType.DATE)
    private Date dias;
    @JoinTable(name = "tbl_horario_deportista", joinColumns = {
        @JoinColumn(name = "id_horario", referencedColumnName = "id_horario")}, inverseJoinColumns = {
        @JoinColumn(name = "id_deportista", referencedColumnName = "id_deportista")})
    @ManyToMany
    private List<Deportista> deportistaList;
    @ManyToMany(mappedBy = "horarioList")
    private List<Entrenador> entrenadorList;

    public Horario() {
    }

    public Horario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public Horario(Integer idHorario, String lugar, Date hora, Date dias) {
        this.idHorario = idHorario;
        this.lugar = lugar;
        this.hora = hora;
        this.dias = dias;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Date getDias() {
        return dias;
    }

    public void setDias(Date dias) {
        this.dias = dias;
    }

    @XmlTransient
    public List<Deportista> getDeportistaList() {
        return deportistaList;
    }

    public void setDeportistaList(List<Deportista> deportistaList) {
        this.deportistaList = deportistaList;
    }

    @XmlTransient
    public List<Entrenador> getEntrenadorList() {
        return entrenadorList;
    }

    public void setEntrenadorList(List<Entrenador> entrenadorList) {
        this.entrenadorList = entrenadorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHorario != null ? idHorario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horario)) {
            return false;
        }
        Horario other = (Horario) object;
        if ((this.idHorario == null && other.idHorario != null) || (this.idHorario != null && !this.idHorario.equals(other.idHorario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.Horario[ idHorario=" + idHorario + " ]";
    }
    
}
