/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controllers.seguimiento;

import com.rid.modelo.facades.TrabajoFacadeLocal;
import com.rid.modelo.entities.Trabajo;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Santiago
 */
@Named(value = "trabajoController")
@ViewScoped
public class TrabajoController implements Serializable {

    @EJB
    private TrabajoFacadeLocal tfl;
    
    private List<Trabajo> trabajos;
    /**
     * Creates a new instance of TrabajoController
     */
    public TrabajoController() {
    }

    public List<Trabajo> getTrabajos() {
        if(trabajos == null || trabajos.isEmpty()){
            trabajos = tfl.findAll();
        }
        return trabajos;
    }

    public void setTrabajos(List<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }
}
