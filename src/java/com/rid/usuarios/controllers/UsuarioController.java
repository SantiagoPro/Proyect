/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.usuarios.controllers;

import com.rid.modelo.controllers.facades.RolFacadeLocal;
import com.rid.modelo.entities.Usuario;
import com.rid.modelo.entities.TipoDocumento;
import com.rid.modelo.controllers.facades.TipoDocumentoFacadeLocal;
import com.rid.modelo.controllers.facades.UsuarioFacadeLocal;
import com.rid.modelo.entities.Rol;
import com.rid.utils.MessagesUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
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
    private RolFacadeLocal rfl;

    @EJB
    private TipoDocumentoFacadeLocal tdfl;

    private List<Usuario> usuario;
    private List<Usuario> deportista;
    private List<Usuario> entrenador;

    private Usuario usuarioSeleccionado;
    
    private Long documento;
    private String nombre;
    private String apellido;
    private String sexo;
    private Date fechaNacimiento;
    private String seguroMedico;
    private String rh;
    private String mail;
    private String telefono;
    private String direccion;
    private String clave;
    private Short estado;
    private String foto;
    private TipoDocumento tiposDocumento;
    private Rol roles;

    public UsuarioController() {
    }

    @PostConstruct
    public void init() {
    }

    public RolFacadeLocal getRfl() {
        return rfl;
    }

    public void setRfl(RolFacadeLocal rfl) {
        this.rfl = rfl;
    }

    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSeguroMedico() {
        return seguroMedico;
    }

    public void setSeguroMedico(String seguroMedico) {
        this.seguroMedico = seguroMedico;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public TipoDocumento getTiposDocumento() {
        return tiposDocumento;
    }

    public void setTiposDocumento(TipoDocumento tiposDocumento) {
        this.tiposDocumento = tiposDocumento;
    }

    public Rol getRoles() {
        return roles;
    }

    public void setRoles(Rol roles) {
        this.roles = roles;
    }
    

    public Usuario getUsuarioSeleccionado() {
        System.out.println("usuarioSeleccionado");
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

    
    public void seleccionarUsuario(Usuario u) {
        System.out.println("seleccionarUsuario");
        usuarioSeleccionado = u;
    }

    public String registrarDeportista() {
        System.out.println("documento: " +documento);
        System.out.println("clave: " +documento);
        System.out.println("direccion; " +direccion);
        System.out.println("fecha" +fechaNacimiento);
        System.out.println("rol" +roles);
        System.out.println("foto: " +foto);
        System.out.println("mail: " +mail);
        System.out.println("nombr: " +nombre);
        System.out.println("rh: " +rh);
        System.out.println("seguro: " +seguroMedico);
        System.out.println("sexo: " +sexo);
        System.out.println("telefono: " +telefono);
        System.out.println("estado: " +estado);
        System.out.println("Tipo documento weon" +tiposDocumento);
        
        
        try {
            Usuario u = new Usuario();
            
            roles = rfl.find(0);
            
            u.setIdUsuarios(documento);
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setSexo(sexo);
            u.setFechaNacimiento(fechaNacimiento);
            u.setSeguroMedico(seguroMedico);
            u.setRh(rh);
            u.setMail(mail);
            u.setTelefono(telefono);
            u.setDireccion(direccion);
            u.setClave(documento.toString());
            u.setEstado(Short.valueOf("0"));
            u.setFoto(null);
            u.setIdRol(roles);
            u.setIdTipoDocumento(tiposDocumento);
            
            ufl.create(u);
            System.out.println("ESTE PUTO SE CREÓ :e");
            MessagesUtil.info(null, "Registro exitoso", "Se ha registrado correctamente el nuevo usuario.", true);
        } catch (Exception e) {
            e.printStackTrace();
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
            usuarioSeleccionado = u;
            if (usuarioSeleccionado.getEstado() == null
                    || usuarioSeleccionado.getEstado() == 0) {
                usuarioSeleccionado.setEstado(Short.valueOf("1"));
            } else if (usuarioSeleccionado.getEstado() == 1) {
                usuarioSeleccionado.setEstado(Short.valueOf("0"));
            }
            ufl.edit(usuarioSeleccionado);
            usuario = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getClassBloqueUsuarioIcon(Usuario u) {
        return ((u.getEstado() == null) ? "icon-lock8" : ((u.getEstado() == 1) ? "icon-unlock2" : "icon-lock8"));
    }
}
