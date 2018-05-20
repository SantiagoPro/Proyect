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
import com.rid.utils.MessagesUtil;
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
    public void init() {

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

    public String iniciarSesion() {
        System.out.println("Id " + idUsuario);
        System.out.println("Clave " + clave);
        user = ufl.findByIduClv(idUsuario, clave);
        if (user != null) {
            System.out.println("pasa user " + user);
            if (user.getEstado() == 1) {
                System.out.println("pasa estado " + user.getEstado());
                if (user.getIdRol() != null) {
                    System.out.println("pasa rol " + getIdRol());
                    if (user.getIdRol().getIdRol() == 0) {
                        System.out.println("pasa");
                        return "/usuarios/Principal.deportista.xhtml?faces-redirect=true";
                    } else if (user.getIdRol().getIdRol() == 1) {
                        System.out.println("pasa");
                        return "/usuarios/Principal.entrenador.xhtml?faces-redirect=true";
                    } else if (user.getIdRol().getIdRol() == 2) {
                        System.out.println("pasa");
                        return "/usuarios/Principal.administrador.xhtml?faces-redirect=true";
                    }
                } else {
                    MessagesUtil.info(null, "No se pudo iniciar sesion. El usuario no tiene rol definido", "", false);
                }
            } else {
                MessagesUtil.info(null, "No se pudo iniciar sesion. El usuario no esta habilitado en el sistema", "", false);
            }
        } else {
            MessagesUtil.error(null, "No se pudo iniciar sesion. El usuario no existe en la base de datos", "", false);
        }
        return "/index.xhtml";
    }

    public boolean sessionStart() {
        return user != null;
    }

    public void validarSesion() throws IOException {
        if (!sessionStart()) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath());
        }
    }

    public void validarRolEntrenador(Integer roles) throws IOException {
        if (sessionStart()) {
            if (rol.getIdRol() != roles.intValue()) {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath()+ "/usuarios/Principal.entrenador.xhtml");
            } else {
                validarSesion();
            }
        }
    }

    public void validarRolDeportistas(Integer roles) throws IOException {
        if (sessionStart()) {
            if (rol.getIdRol() != roles.intValue()) {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath()+ "/usuarios/Principal.deportista.xhtml");
            } else {
                validarSesion();
            }
        }
    }

    public void validarRolAdministrador(Integer roles) throws IOException {
        if (sessionStart()) {
            if (rol.getIdRol() != roles.intValue()) {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/usuarios/Principal.admanistrador.xhtml" );
            } else {
                validarSesion();
            }
        }
    }

    public void cerrarSesion() {
        try {
            idUsuario = null;
            clave = null;
            user = null;
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.invalidateSession();
            ec.redirect(ec.getRequestContextPath());
        } catch (IOException iOException) {
        }
    }
}
