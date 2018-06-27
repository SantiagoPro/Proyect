/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.torneos.controllers;

import com.rid.controller.mail.Mail;
import com.rid.modelo.controllers.facades.TorneoFacadeLocal;
import com.rid.modelo.entities.Torneo;
import com.rid.modelo.entities.Usuario;
import com.rid.usuarios.controllers.UsuarioController;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

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
    
    @Inject
    private UsuarioController uc;
            
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
        List<Usuario> u = uc.getAdministrador();
        
        try {
            Torneo t = new Torneo(null, nombre, fecha, lugar);
            tdl.create(t);
            for (Usuario usuario : u) {
                Mail.sendMailHTML(usuario.getMail(), "Se ha registrado un nuevo torneo", 
                        "Señor(a) " +usuario.getNombre()+ "." +"<br> <h3>Se realizado el registro de un nuevo Torneo en el Sistema R.I.D.</h3>" + "<h5 style='color:#0f0C29;'>Nombre: " +nombre + "<br> Lugar: " +lugar + "<br>Fecha: </h5>" +fecha);                
            }
            t = null;
            return "Nuevo.torneo.xhtml";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
}
