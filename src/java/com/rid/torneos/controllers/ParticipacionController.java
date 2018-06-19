/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.torneos.controllers;

import com.rid.modelo.controllers.facades.ParticipacionFacadeLocal;
import com.rid.modelo.entities.CategoriaDeportista;
import com.rid.modelo.entities.Participacion;
import com.rid.modelo.entities.Torneo;
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
    
    private Integer idParticipacion;
    private String puesto;
    private CategoriaDeportista categoriaDeportista;
    private Torneo torneo;

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

    public void setListaParticipacion(List<Participacion> listaParticipacion) {
        this.listaParticipacion = listaParticipacion;
    }

    public Integer getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(Integer idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public CategoriaDeportista getCategoriaDeportista() {
        return categoriaDeportista;
    }

    public void setCategoriaDeportista(CategoriaDeportista categoriaDeportista) {
        this.categoriaDeportista = categoriaDeportista;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }
    
    public String registrarParticipacion(){
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return "";
    }
}
