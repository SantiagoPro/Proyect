/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controllers.usuarios;

import com.rid.utils.Mail;
import com.rid.modelo.facades.RolFacadeLocal;
import com.rid.modelo.entities.Usuario;
import com.rid.modelo.entities.TipoDocumento;
import com.rid.modelo.facades.TipoDocumentoFacadeLocal;
import com.rid.modelo.facades.UsuarioFacadeLocal;
import com.rid.modelo.entities.Rol;
import com.rid.utils.FileUpload;
import java.io.File;
import com.rid.utils.MessagesUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Santiago
 */
@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    @Inject
    private FileUpload ca;
    
    
    @EJB
    private UsuarioFacadeLocal ufl;

    @EJB
    private RolFacadeLocal rfl;

    @EJB
    private TipoDocumentoFacadeLocal tdfl;

    private Usuario user;
    private List<Usuario> usuario;
    private List<Usuario> deportista;
    private List<Usuario> entrenador;
    private List<Usuario> administrador;
    
    private String imagenPerfil;

    private Usuario usuarioSeleccionado;

    private Long documento;
    private String nombre;
    private String apellido;
    private Boolean sexo;
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
    private Rol rol;

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

    public Boolean getSexo() {
        String F = "Femenino";
        String M = "Masculino";
        try {
            if (sexo.equals(0)) {
                sexo = Boolean.valueOf(F);
                return sexo;
            } else {
                sexo = Boolean.valueOf(M);
                return sexo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setSexo(Boolean sexo) {
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

    public String getImagenPerfil() {
        File f = new File(ca.getCarpeta(),user.getIdUsuarios() + ".jpg");
        if (!ca.getCarpeta().exists() && imagenPerfil == null) {
            imagenPerfil = "resouerces/images/perfiles/LOGO.jpg";
        }else if (f.exists()) {
            imagenPerfil = "imgPerfil/" + f.getName();
        }
//        else if () {
//            
//        }
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
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

    public List<Usuario> getAdministrador() {
        if (administrador == null || entrenador.isEmpty()) {
            administrador = ufl.findByIdRol(2);
        }
        return administrador;
    }

    
    public void seleccionarUsuario(Usuario u) {
        usuarioSeleccionado = u;
    }

    public String registrarDeportista() {
        try {
            Usuario u = new Usuario(); 
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
            u.setIdRol(rfl.find(0));
            u.setIdTipoDocumento(tiposDocumento);
            ufl.create(u);
            Mail.sendMail(mail, "Registro en el sistema", "Se realizado el registro de usuario en el Sistema R.I.D. Puede ingresar con el usuario "
                    + documento + " y la contraseña " + documento + ".");
            u = null;
            MessagesUtil.info(null, "Registro exitoso", "Se ha registrado correctamente el nuevo usuario.", true);
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtil.error(null, "Error al registrar el Deportista.", e.getMessage(), false);
        }
        return "/usuarios/Principal.entrenador.xhtml";
    }
   
    public String registrarEntrenador() {
        try {
            Usuario u = new Usuario();
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
            u.setIdRol(rfl.find(1));
            u.setIdTipoDocumento(tiposDocumento);
            ufl.create(u);
            Mail.sendMail(mail, "Registro en el sistema", "Se realizado el registro de usuario en el Sistema R.I.D. Puede ingresar con el usuario "
                    + documento + " y la contraseña " + documento + ".");
            u = null;
            MessagesUtil.info(null, "Registro exitoso", "Se ha registrado correctamente el nuevo usuario.", true);
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtil.error(null, "Error al registrar el Deportista.", e.getMessage(), false);
        }
       return "/usuarios/Principal.administrador.xhtml";
    }

    public String eliminarDep() {
        try {
            ufl.remove(usuarioSeleccionado);
            usuario = null;
            MessagesUtil.info(null, "Eliminación exitosa", "Se ha eliminado correctamente al usuario.", false);
        } catch (Exception e) {
            MessagesUtil.error(null, "Error al eliminar el usuario.", e.getMessage(), false);
        }
        usuarioSeleccionado = null;
        return "/usuarios/Principal.administrador.xhtml";
    }
    
    public String eliminarEnt() {
        try {
            ufl.remove(usuarioSeleccionado);
            usuario = null;
            MessagesUtil.info(null, "Eliminación exitosa", "Se ha eliminado correctamente al usuario.", false);
        } catch (Exception e) {
            MessagesUtil.error(null, "Error al eliminar el usuario.", e.getMessage(), false);
        }
        usuarioSeleccionado = null;
        return "/usuarios/Principal.entrenador.xhtml";
    }

    public String editarEnt() {
        try {
            ufl.edit(usuarioSeleccionado);
            usuario = null;
        } catch (Exception e) {
            MessagesUtil.error(null, "Error al editar el usuario.", e.getMessage(), false);
        }
        usuarioSeleccionado = null;
        return "/usuarios/Principal.administrador.xhtml";
    }
    
    public String editarDep() {
        try {
            ufl.edit(usuarioSeleccionado);
            usuario = null;
        } catch (Exception e) {
            MessagesUtil.error(null, "Error al editar el usuario.", e.getMessage(), false);
        }
        usuarioSeleccionado = null;
        return "/usuarios/Principal.entrenador.xhtml";
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
        return ((u.getEstado() == null || u.getEstado() == 0) ? "fa-eye-slash" : ((u.getEstado() == 1) ? "fa-eye" : "fa-eye-slash"));
    }
}
