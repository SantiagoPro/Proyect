/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controllers.seguimiento;

import com.rid.modelo.facades.EntrenadorFacadeLocal;
import com.rid.modelo.facades.EntrenamientoFacadeLocal;
import com.rid.modelo.entities.CategoriaDeportista;
import com.rid.modelo.entities.Entrenador;
import com.rid.modelo.entities.Entrenamiento;
import com.rid.modelo.entities.PorcentajeCarga;
import com.rid.modelo.entities.TipoEntrenamiento;
import com.rid.modelo.entities.TipoTrabajo;
import com.rid.modelo.entities.Trabajo;
import com.rid.controllers.usuarios.DeportistaController;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

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
    @EJB
    private EntrenamientoFacadeLocal efl;
    private List<Entrenamiento> entrenamientos;
    
    private Date fechaInicio;
    private Date fechaFin;
    private CategoriaDeportista idCategoriaDeportista;
    private Entrenador idEntrenador;
    private Entrenamiento entrenamientoPadre;
    
    @Inject
    private DeportistaController dc;
    
    public EntrenamientoController() {
        dc = new DeportistaController();
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

    public List<Entrenamiento> getEntrenamientos() {
        if (entrenamientos == null || entrenamientos.isEmpty()) {
            entrenamientos = efl.findAll();
        }
        return entrenamientos;
    }

    public void setEntrenamientos(List<Entrenamiento> entrenamientos) {
        this.entrenamientos = entrenamientos;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public CategoriaDeportista getIdCategoriaDeportista() {
        return idCategoriaDeportista;
    }

    public void setIdCategoriaDeportista(CategoriaDeportista idCategoriaDeportista) {
        this.idCategoriaDeportista = idCategoriaDeportista;
    }

    public Entrenador getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(Entrenador idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public Entrenamiento getEntrenamientoPadre() {
        return entrenamientoPadre;
    }

    public void setEntrenamientoPadre(Entrenamiento entrenamientoPadre) {
        this.entrenamientoPadre = entrenamientoPadre;
    }

    public String registrarEntrenamiento(){
        try {
            Entrenamiento e = new Entrenamiento(null);
            e.setFechaInicio(fechaInicio);
            e.setFechaFin(fechaFin);
            e.setIdCategoriaDeportista(idCategoriaDeportista);
            e.setIdEntrenador(idEntrenador);
            e.setEntrenamientoPadre(entrenamientoPadre);
            efl.create(e);
            
            e = null;
            return "Registro.entrenamiento.xhtml";
                
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
}
