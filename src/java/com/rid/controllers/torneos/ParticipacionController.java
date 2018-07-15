/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controllers.torneos;

import com.rid.modelo.facades.ParticipacionFacadeLocal;
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
            Participacion p = new Participacion(null);
            p.setPuesto(puesto);
            p.setIdCategoriaDeportista(categoriaDeportista);
            p.setIdTorneo(torneo);
            pfl.create(p);
            p = null;
            return "participacion.xhtml";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
