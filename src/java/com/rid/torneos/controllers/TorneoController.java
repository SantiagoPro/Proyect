/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.torneos.controllers;

import com.rid.modelo.controllers.facades.TorneoFacadeLocal;
import com.rid.modelo.entities.Torneo;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author karen
 */
@Named(value = "torneoController")
@ViewScoped
public class TorneoController implements Serializable {

    /**
     * Creates a new instance of TorneoController
     */
    @EJB
    private TorneoFacadeLocal tdl;
    private Torneo torneo;
    
    private List<Torneo> listaTorneo;
    
    private String nombre;
    private Date fecha;
    private String lugar;
            
    public TorneoController() {
    }
    
    @PostConstruct
    public void init(){
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    
    public List<Torneo> getTorneo(){
        if (listaTorneo == null || listaTorneo.isEmpty()) {
            listaTorneo = tdl.findAll();
        }
        return listaTorneo;
    }
    public String registrarTorneo(){
       
        System.out.println("nombre : " + nombre);
        System.out.println("fecha : " + fecha);
        System.out.println("lugar : " + lugar);
        
        try {
            Torneo t = new Torneo(null, nombre, fecha, lugar);
            tdl.create(t);
            t = null;
            return "Nuevo.torneo.xhtml";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
}
