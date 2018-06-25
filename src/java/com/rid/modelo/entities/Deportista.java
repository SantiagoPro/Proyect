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
    , @NamedQuery(name = "Deportista.findByEstatura", query = "SELECT d FROM Deportista d WHERE d.estatura = :estatura")
    , @NamedQuery(name = "Deportista.findByFuerza", query = "SELECT d FROM Deportista d WHERE d.fuerza = :fuerza")
    , @NamedQuery(name = "Deportista.findByVelocidad", query = "SELECT d FROM Deportista d WHERE d.velocidad = :velocidad")
    , @NamedQuery(name = "Deportista.findBySalto", query = "SELECT d FROM Deportista d WHERE d.salto = :salto")
    , @NamedQuery(name = "Deportista.findByFlexibilidad", query = "SELECT d FROM Deportista d WHERE d.flexibilidad = :flexibilidad")
    , @NamedQuery(name = "Deportista.findByResistencia", query = "SELECT d FROM Deportista d WHERE d.resistencia = :resistencia")})

public class Deportista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_deportista")
    private Long idDeportista;
    @Basic(optional = false)
    @Column(name = "peso")
    private Double peso;
    @Basic(optional = false)
    @Column(name = "estatura")
    private Integer estatura;
    @Column(name = "fuerza")
    private Integer fuerza;
    @Column(name = "velocidad")
    private Integer velocidad;
    @Column(name = "salto")
    private Integer salto;
    @Column(name = "flexibilidad")
    private Integer flexibilidad;
    @Column(name = "resistencia")
    private Integer resistencia;
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

    public Deportista(Long idDeportista, double peso, Integer estatura, Integer fuerza, Integer velocidad, Integer salto, Integer flexibilidad, Integer resistencia) {
        this.idDeportista = idDeportista;
        this.peso = peso;
        this.estatura = estatura;
        this.fuerza = fuerza;
        this.velocidad = velocidad;
        this.salto = salto;
        this.flexibilidad = flexibilidad;
        this.resistencia = resistencia;
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

    public Integer getEstatura() {
        return estatura;
    }

    public void setEstatura(Integer estatura) {
        this.estatura = estatura;
    }

    public Integer getFuerza() {
        return fuerza;
    }

    public void setFuerza(Integer fuerza) {
        this.fuerza = fuerza;
    }

    public Integer getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Integer velocidad) {
        this.velocidad = velocidad;
    }

    public Integer getSalto() {
        return salto;
    }

    public void setSalto(Integer salto) {
        this.salto = salto;
    }

    public Integer getFlexibilidad() {
        return flexibilidad;
    }

    public void setFlexibilidad(Integer flexibilidad) {
        this.flexibilidad = flexibilidad;
    }

    public Integer getResistencia() {
        return resistencia;
    }

    public void setResistencia(Integer resistencia) {
        this.resistencia = resistencia;
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
