/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.torneos.controllers;

import com.rid.modelo.controllers.facades.ResultadoFacadeLocal;
import com.rid.modelo.entities.Participacion;
import com.rid.modelo.entities.Resultado;
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
@Named(value = "resultadosController")
@ViewScoped
public class ResultadosController implements Serializable{

    /**
     * Creates a new instance of ResultadosController
    } */
    
    @EJB
    private ResultadoFacadeLocal rfl;
    private Resultado resultado;
    
    private List<Resultado> res;   
    
    private Integer idResultado;
    private int pesoArranque;
    private int pesoEnvion;
    private Participacion idParticipacion;
    
    public ResultadosController() {
    }
    
    @PostConstruct
    public void init(){
    }

    public Integer getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(Integer idResultado) {
        this.idResultado = idResultado;
    }

    public int getPesoArranque() {
        return pesoArranque;
    }

    public void setPesoArranque(int pesoArranque) {
        this.pesoArranque = pesoArranque;
    }

    public int getPesoEnvion() {
        return pesoEnvion;
    }

    public void setPesoEnvion(int pesoEnvion) {
        this.pesoEnvion = pesoEnvion;
    }

    public Participacion getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(Participacion idParticipacion) {
        this.idParticipacion = idParticipacion;
    }
    
    public List<Resultado> getResultado(){
        if (res == null || res.isEmpty()) {
            res = rfl.findAll();
        }
        return res;
    }    
    
    public String registrarResultado(){
        try {
            Resultado r = new Resultado(null, pesoArranque, pesoEnvion);
            r.setIdParticipacion(idParticipacion);
            rfl.create(resultado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
}
