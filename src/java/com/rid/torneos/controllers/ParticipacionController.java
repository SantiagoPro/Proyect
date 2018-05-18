/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.torneos.controllers;

import com.rid.modelo.controllers.facades.ParticipacionFacadeLocal;
import com.rid.modelo.entities.Participacion;
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
@Named(value = "participacionController")
@ViewScoped
public class ParticipacionController implements Serializable{

    /**
     * Creates a new instance of ParticipacionController
     */
    @EJB
    private ParticipacionFacadeLocal pfl;
    private Participacion participacion;
    
    private List<Participacion> listaParticipacion;
    
    public ParticipacionController() {
    }
    
    @PostConstruct
    public void init(){
    }
    
    public List<Participacion> getParticipacion(){
        if (listaParticipacion == null || listaParticipacion.isEmpty()) {
            listaParticipacion = pfl.findAll();
        }
        return listaParticipacion;
    }
}
