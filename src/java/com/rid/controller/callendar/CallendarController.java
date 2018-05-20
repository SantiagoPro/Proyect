/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controller.callendar;

import com.rid.modelo.controllers.facades.HorarioFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.convert.DateTimeConverter;
import javax.inject.Named;

/**
 *
 * @author Santiago
 */
@Named(value = "callendarController")
@SessionScoped
public class CallendarController implements Serializable{
    
    @EJB
    private HorarioFacadeLocal hfl;
    
    private Integer id;
    private String lugar;
    private DateTimeConverter hora;
    private String dia;
    
    @PostConstruct
    public void init(){
    
    }

    public CallendarController() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public DateTimeConverter getHora() {
        return hora;
    }

    public void setHora(DateTimeConverter hora) {
        this.hora = hora;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
    
    
    
    
}
