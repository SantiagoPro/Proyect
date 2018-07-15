/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controllers.seguimiento;

import com.rid.modelo.facades.TipoTrabajoFacadeLocal;
import com.rid.modelo.entities.TipoTrabajo;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Santiago
 */
@Named(value = "tipoTrabajoController")
@ViewScoped
public class TipoTrabajoController implements Serializable{

    @EJB
    private TipoTrabajoFacadeLocal ttfl;
    
    private List<TipoTrabajo> tiposTrabajo;
    /**
     * Creates a new instance of TipoTrabajoController
     */
    public TipoTrabajoController() {
    }

    public List<TipoTrabajo> getTiposTrabajo() {
        if(tiposTrabajo == null || tiposTrabajo.isEmpty()){
            tiposTrabajo = ttfl.findAll();
        }
        return tiposTrabajo;
    }

    public void setTiposTrabajo(List<TipoTrabajo> tiposTrabajo) {
        this.tiposTrabajo = tiposTrabajo;
    }
    
}
