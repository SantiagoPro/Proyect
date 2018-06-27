/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.usuarios.controllers;

import com.rid.modelo.controllers.facades.EntrenadorFacadeLocal;
import com.rid.modelo.entities.Entrenador;
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
@Named(value = "entrenadorController")
@ViewScoped
public class EntrenadorController implements Serializable{

    /**
     * Creates a new instance of EntrenadorController
     */
    @EJB
    private EntrenadorFacadeLocal efl;
    
    private List<Entrenador> entrenadores;
    
    public EntrenadorController() {
    }
    
    @PostConstruct
    public void init(){}

    public List<Entrenador> getEntrenadores() {
        if (entrenadores == null || entrenadores.isEmpty()) {
            entrenadores = efl.findAll();
        }
        return entrenadores;
    }

    public void setEntrenadores(List<Entrenador> entrenadores) {
        this.entrenadores = entrenadores;
    }
    
    
}
