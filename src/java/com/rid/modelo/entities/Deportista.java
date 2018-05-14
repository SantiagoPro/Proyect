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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author karen
 */
@Entity
@Table(name = "tbl_deportista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deportista.findAll", query = "SELECT d FROM Deportista d")
    , @NamedQuery(name = "Deportista.findByIdDeportista", query = "SELECT d FROM Deportista d WHERE d.idDeportista = :idDeportista")
    , @NamedQuery(name = "Deportista.findByPeso", query = "SELECT d FROM Deportista d WHERE d.peso = :peso")
    , @NamedQuery(name = "Deportista.findByEstatura", query = "SELECT d FROM Deportista d WHERE d.estatura = :estatura")})
public class Deportista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_deportista")
    private Long idDeportista;
    @Basic(optional = false)
    @Column(name = "peso")
    private double peso;
    @Basic(optional = false)
    @Column(name = "estatura")
    private double estatura;
    @ManyToMany(mappedBy = "deportistaList")
    private List<Horario> horarioList;
    @JoinColumn(name = "id_deportista", referencedColumnName = "id_usuarios", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDeportista")
    private List<CategoriaDeportista> categoriaDeportistaList;
    @OneToMany(mappedBy = "idDeportista")
    private List<Imc> imcList;

    public Deportista() {
    }

    public Deportista(Long idDeportista) {
        this.idDeportista = idDeportista;
    }

    public Deportista(Long idDeportista, double peso, double estatura) {
        this.idDeportista = idDeportista;
        this.peso = peso;
        this.estatura = estatura;
    }

    public Long getIdDeportista() {
        return idDeportista;
    }

    public void setIdDeportista(Long idDeportista) {
        this.idDeportista = idDeportista;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    @XmlTransient
    public List<Horario> getHorarioList() {
        return horarioList;
    }

    public void setHorarioList(List<Horario> horarioList) {
        this.horarioList = horarioList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public List<CategoriaDeportista> getCategoriaDeportistaList() {
        return categoriaDeportistaList;
    }

    public void setCategoriaDeportistaList(List<CategoriaDeportista> categoriaDeportistaList) {
        this.categoriaDeportistaList = categoriaDeportistaList;
    }

    @XmlTransient
    public List<Imc> getImcList() {
        return imcList;
    }

    public void setImcList(List<Imc> imcList) {
        this.imcList = imcList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDeportista != null ? idDeportista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deportista)) {
            return false;
        }
        Deportista other = (Deportista) object;
        if ((this.idDeportista == null && other.idDeportista != null) || (this.idDeportista != null && !this.idDeportista.equals(other.idDeportista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rid.modelo.entities.Deportista[ idDeportista=" + idDeportista + " ]";
    }
    
}
