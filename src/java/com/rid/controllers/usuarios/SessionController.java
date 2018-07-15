/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controllers.usuarios;

import com.rid.modelo.facades.RolFacadeLocal;
import com.rid.modelo.facades.UsuarioFacadeLocal;
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

    public Rol getRol() {
        return rol;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String iniciarSesion() {
        user = ufl.findByIduClv(idUsuario, clave);
        if (idUsuario != user.getIdUsuarios() || clave != user.getClave()) {
            if (user != null) {
                System.out.println("pasa usuario ");
                if (user.getEstado() == 1) {
                    System.out.println("pasa estado ");
                    if (user.getIdRol() != null) {
                        System.out.println("pasa rol ");
                        if (user.getIdRol().getIdRol() != null) {
                            switch (user.getIdRol().getIdRol()) {
                                case 0:
                                    System.out.println("pasa " + user.getIdRol().getIdRol());
                                    return urlPrincipalRol(0);
                                case 1:
                                    System.out.println("pasa " + user.getIdRol().getIdRol());
                                    return urlPrincipalRol(1);
                                case 2:
                                    System.out.println("pasa " + user.getIdRol().getIdRol());
                                    return urlPrincipalRol(2);
                                default:
                                    break;
                            }
                        }
                    } else {
                        MessagesUtil.info(null, "No se pudo iniciar sesion. El usuario no tiene rol definido", "", false);
                    }
                } else {
                    MessagesUtil.info(null, "No se pudo iniciar sesion. El usuario no esta habilitado en el sistema", "", false);
                }
            } else {
                MessagesUtil.info(null, "No se pudo iniciar sesion. El usuario no existe en la base de datos", "", false);
            }
        } else {
            MessagesUtil.info(null, "No se pudo iniciar sesion. Los datos son incorrectos", "", false);
        }
        return "/index.xhtml";
    }

    public boolean isSessionStart() {
        return user != null;
    }

    public void validarSesion() throws IOException {
        if (!isSessionStart()) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath());
        }
    }

    public void validarRol(Integer idRol) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        if (isSessionStart()) {
            if (user.getIdRol().getIdRol() != idRol.intValue()) {
                ec.redirect(ec.getRequestContextPath() + urlPrincipalRol(user.getIdRol().getIdRol()));
            }
        } else {
            ec.redirect(ec.getRequestContextPath());
        }
    }

    public static String urlPrincipalRol(Integer idRol) {
        switch (idRol) {
            case 0:
                return "/usuarios/Principal.deportista.xhtml?faces-redirect=true";
            case 1:
                return "/usuarios/Principal.entrenador.xhtml?faces-redirect=true";
            case 2:
                return "/usuarios/Principal.administrador.xhtml?faces-redirect=true";
            default:
                return "index.html?faces-redirect=true";
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
            iOException.printStackTrace();
        }
    }

}