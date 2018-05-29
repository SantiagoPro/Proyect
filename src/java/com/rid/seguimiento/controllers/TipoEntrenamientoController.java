/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.seguimiento.controllers;

import com.rid.modelo.controllers.facades.TipoEntrenamientoFacadeLocal;
import com.rid.modelo.entities.TipoEntrenamiento;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Santiago
 */
@Named(value = "tipoEntrenamientoController")
@ViewScoped
public class TipoEntrenamientoController implements Serializable{

    @EJB
    private TipoEntrenamientoFacadeLocal ttfl;
    
    private List<TipoEntrenamiento> tiposEntrenamiento;
    /**
     * Creates a new instance of TipoTrabajoController
     */
    public TipoEntrenamientoController() {
    }

    public List<TipoEntrenamiento> getTiposEntrenamiento() {
        if(tiposEntrenamiento == null || tiposEntrenamiento.isEmpty()){
            tiposEntrenamiento = ttfl.findAll();
        }
        return tiposEntrenamiento;
    }

    public void setTiposEntrenamiento(List<TipoEntrenamiento> tiposEntrenamiento) {
        this.tiposEntrenamiento = tiposEntrenamiento;
    }
    
}
