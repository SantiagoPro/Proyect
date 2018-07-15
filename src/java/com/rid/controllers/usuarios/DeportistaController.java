/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controllers.usuarios;

import com.rid.modelo.facades.DeportistaFacadeLocal;
import com.rid.modelo.entities.Deportista;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author karen
 */
@Named(value = "deportistaController")
@ViewScoped
public class DeportistaController implements Serializable{

    /**
     * Creates a new instance of DeportistaController
    **/
    @EJB
    private DeportistaFacadeLocal dfl;
    
    private List<Deportista> deportistas;
    
    private Long idDeportista;
    private double peso;
    private Integer estatura;
    private Integer fuerza;
    private Integer velocidad;
    private Integer salto;
    private Integer flexibilidad;
    private Integer resistencia;
    
    public DeportistaController() {
    }
    
    @PostConstruct
    public void init(){}

    public List<Deportista> getDeportistas() {
        if (deportistas == null || deportistas.isEmpty()) {
            deportistas = dfl.findAll();
        }
        return deportistas;
    }

    public void setDeportistas(List<Deportista> deportistas) {
        this.deportistas = deportistas;
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
    
    public String registrarDeportista(){
        try {
            Deportista d = new Deportista(idDeportista, 0, estatura, fuerza, velocidad, salto, flexibilidad, resistencia);
            dfl.create(d);
            
            d = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "";
    }
}
