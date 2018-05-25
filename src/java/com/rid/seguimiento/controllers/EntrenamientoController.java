/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.seguimiento.controllers;

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
}
