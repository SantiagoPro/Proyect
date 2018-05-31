/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.seguimiento.controllers;

import com.rid.modelo.entities.Entrenamiento;
import com.rid.modelo.entities.PorcentajeCarga;
import com.rid.modelo.entities.TipoEntrenamiento;
import com.rid.modelo.entities.TipoTrabajo;
import com.rid.modelo.entities.Trabajo;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Santiago
 */
@Named(value = "entrenamientoController")
@ViewScoped
public class EntrenamientoController implements Serializable{

    private TipoTrabajo tipoTrabajo;
    private Trabajo trabajo;
    private Entrenamiento entrenamiento;
    private PorcentajeCarga porcentajeCargar;
    private TipoEntrenamiento tipoEntrenamiento;
    
    /**
     * Creates a new instance of EntrenamientoController
     */
    public EntrenamientoController() {
    }

    public TipoTrabajo getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public Entrenamiento getEntrenamiento() {
        return entrenamiento;
    }

    public void setEntrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

    public PorcentajeCarga getPorcentajeCargar() {
        return porcentajeCargar;
    }

    public void setPorcentajeCargar(PorcentajeCarga porcentajeCargar) {
        this.porcentajeCargar = porcentajeCargar;
    }

    public TipoEntrenamiento getTipoEntrenamiento() {
        return tipoEntrenamiento;
    }

    public void setTipoEntrenamiento(TipoEntrenamiento tipoEntrenamiento) {
        this.tipoEntrenamiento = tipoEntrenamiento;
    }
    
}
