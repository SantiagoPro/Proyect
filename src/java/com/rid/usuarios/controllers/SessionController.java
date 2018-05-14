/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.usuarios.controllers;

import com.rid.modelo.controllers.facades.RolFacadeLocal;
import com.rid.modelo.controllers.facades.UsuarioFacadeLocal;
import com.rid.modelo.entities.Usuario;
import com.rid.modelo.entities.Rol;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Santiago
 */
@Named(value = "sessionController")
@SessionScoped
public class SessionController implements Serializable {

    /**
     * Creates a new instance of UsuarioController
     */
    @EJB
    private UsuarioFacadeLocal ufl;
    @EJB
    private RolFacadeLocal rfl;
    
    private Long idUsuario;
    private String clave;
    private Usuario user;
    private Integer idRol;
    private Rol rol;
            
    public SessionController() {
    }
    
    @PostConstruct
    public void init(){
    
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer rol) {
        this.idRol = rol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String iniciarSesion(){
        System.out.println("Id " + idUsuario);
        System.out.println("Clave " + clave);
        user = ufl.findByIduClv(idUsuario, clave);
                if (user != null) {
        return "/usuarios/Principal.entrenador.xhtml?faces-redirect=true";
        }
    return "index.xhtml";
    }
    
    public void cerrarSesion(){
        try {
            idUsuario = null;
            clave = null;
            user = null;
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.invalidateSession();
            ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
        } catch (IOException iOException) {
        }
    }
}
