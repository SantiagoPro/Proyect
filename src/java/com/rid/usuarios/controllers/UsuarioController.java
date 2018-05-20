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
import java.sql.Date;
import java.util.ArrayList;
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

    public List<TipoDocumento> getDocumento() {
        tipoDocumento = tdfl.findAll();
        return tipoDocumento;
    }

    public void seleccionarUsuario(Usuario u) {
        usuarioSeleccionado = u;
    }

    public String registrarUsuario() {
        try {
            ufl.create(nuevoUsuario);
            MessagesUtil.info(null, "Registro exitoso", "Se ha registrado correctamente el nuevo usuario.", true);
            return "/usuarios/Principal.entrenador.xhtml?faces-redirect=true";
        } catch (Exception e) {
            MessagesUtil.error(null, "Error al registrar el Deportista.", e.getMessage(), false);
        }
        return "";
    }

    public String eliminarUsuario() {
        try {
            ufl.remove(usuarioSeleccionado);
            usuario = null;
            MessagesUtil.info(null, "Eliminación exitosa", "Se ha eliminado correctamente el Deportista.", false);
        } catch (Exception e) {
            MessagesUtil.error(null, "Error al eliminar el usuario.", e.getMessage(), false);
        }
        usuarioSeleccionado = null;
        return "/usuarios/Principal.entrenador.xhtml?faces-redirect=true";
    }

    public void editarUsuario() {
        try {
            ufl.edit(usuarioSeleccionado);
            usuario = null;
        } catch (Exception e) {
            MessagesUtil.error(null, "Error al editar el usuario.", e.getMessage(), false);
        }
    }

    public String bloquearODesbloquear() {
        try {
            System.out.println("Vamo a editá el usualio:");
            System.out.println("Id:" + usuarioSeleccionado.getIdUsuarios());
            if (usuarioSeleccionado.getEstado() != null) {
                usuarioSeleccionado.setEstado(
                        (usuarioSeleccionado.getEstado() == 0) ? Short.valueOf("1") : (usuarioSeleccionado.getEstado() == 1 ? Short.valueOf("0") : usuarioSeleccionado.getEstado()));

            } else {
                usuarioSeleccionado.setEstado(Short.valueOf("0"));
            }
            ufl.edit(usuarioSeleccionado);
            usuario = null;
            MessagesUtil.info(null, "Se ha cambiado el destado del usaurio.", "", false);
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtil.error(null, "Error al cambiar el estado el usuario.", e.getMessage(), false);
        }
        usuarioSeleccionado = null;
        return "listar.xhtml?faces-redirect=true";
    }

    public String getClassBloqueUsuario(Usuario u){
        return ((u.getEstado() == null) ? "fa-hotel": ((u.getEstado() == 1) ? "fa-lock": "fa-unlock"));
    }

}
