/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.usuarios.controllers;

import com.rid.modelo.entities.Usuario;
import com.rid.modelo.entities.TipoDocumento;
import com.rid.modelo.controllers.facades.TipoDocumentoFacadeLocal;
import com.rid.modelo.controllers.facades.UsuarioFacadeLocal;
import com.rid.utils.MessagesUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Santiago
 */
@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    @EJB
    private UsuarioFacadeLocal ufl;

    @EJB
    private TipoDocumentoFacadeLocal tdfl;

    private List<Usuario> usuario;
    private List<Usuario> deportista;
    private List<Usuario> entrenador;
    private List<TipoDocumento> tipoDocumento;

    private TipoDocumento tipoDocumentoList;
    private Usuario nuevoUsuario;
    private Usuario usuarioSeleccionado;

    public UsuarioController() {
    }

    @PostConstruct
    public void init() {
        nuevoUsuario = new Usuario();
    }

    public TipoDocumentoFacadeLocal getTdfl() {
        return tdfl;
    }

    public void setTdfl(TipoDocumentoFacadeLocal tdfl) {
        this.tdfl = tdfl;
    }

    public List<TipoDocumento> getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(List<TipoDocumento> tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public TipoDocumento getTipoDocumentoList() {
        return tipoDocumentoList;
    }

    public void setTipoDocumentoList(TipoDocumento tipoDocumentoList) {
        this.tipoDocumentoList = tipoDocumentoList;
    }

    public Usuario getNuevoUsuario() {
        return nuevoUsuario;
    }

    public void setNuevoUsuario(Usuario nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public List<Usuario> getUsuario() {

        if (usuario == null || usuario.isEmpty()) {
            usuario = ufl.findAll();
        }
        return usuario;
    }

    public List<Usuario> getDeportista() {
        if (deportista == null || deportista.isEmpty()) {
            deportista = ufl.findByIdRol(0);
        }
        return deportista;
    }

    public List<Usuario> getEntrenador() {
        if (entrenador == null || entrenador.isEmpty()) {
            entrenador = ufl.findByIdRol(1);
        }

        return entrenador;
    }

    public List<TipoDocumento> getDocumento() {
        tipoDocumento = tdfl.findAll();
        return tipoDocumento;
    }

    public void seleccionarUsuario(Usuario u) {
        usuarioSeleccionado = u;
    }

    public String registrarDeportista() {
        try {
            ufl.create(nuevoUsuario);
            setTipoDocumento(tipoDocumento);
            MessagesUtil.info(null, "Registro exitoso", "Se ha registrado correctamente el nuevo usuario.", true);
            return "/usuarios/Principal.entrenador.xhtml?faces-redirect=true";
        } catch (Exception e) {
            MessagesUtil.error(null, "Error al registrar el Deportista.", e.getMessage(), false);
        }
        return "";
    }
    
    public void eliminarUsuario() {
        try {
            ufl.remove(usuarioSeleccionado);
            usuario = null;
            MessagesUtil.info(null, "Eliminación exitosa", "Se ha eliminado correctamente al usuario.", false);
        } catch (Exception e) {
            MessagesUtil.error(null, "Error al eliminar el usuario.", e.getMessage(), false);
        }
        usuarioSeleccionado = null;
    }

    public void editarUsuario() {
        try {
            ufl.edit(usuarioSeleccionado);
            usuario = null;
        } catch (Exception e) {
            MessagesUtil.error(null, "Error al editar el usuario.", e.getMessage(), false);
        }
        usuarioSeleccionado = null;
    }

    public void cambiarEstado(Usuario u) {
        try {
            System.out.println("Cambiando estado");
            usuarioSeleccionado = u;
            if (usuarioSeleccionado.getEstado() == null
                    || usuarioSeleccionado.getEstado() == 0) {
                usuarioSeleccionado.setEstado(Short.valueOf("1"));
            System.out.println("Cambiando estado");
            } else if (usuarioSeleccionado.getEstado() == 1) {
                usuarioSeleccionado.setEstado(Short.valueOf("0"));
            System.out.println("Cambiando estado");
            }
            ufl.edit(usuarioSeleccionado);
            usuario = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getClassBloqueUsuarioIcon(Usuario u) {
        return ((u.getEstado() == null) ? "fa-eye" : ((u.getEstado() == 1) ? "fa-eye" : "fa-eye-slash"));
    }
}
