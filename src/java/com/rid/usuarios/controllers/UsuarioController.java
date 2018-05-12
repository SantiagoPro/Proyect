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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Date;
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

    /**
     * Creates a new instance of UsuarioController
     */
    @EJB
    private UsuarioFacadeLocal ufl;
    
    @EJB
    private TipoDocumentoFacadeLocal tdfl;

    private List<Usuario> usuario; 
    private List<TipoDocumento> tipoDocumento;
    
    private Long idUsuario;
    private TipoDocumento tipoDocumentoList;
    private String nombre;
    private String apellido;
    private String sexo;
    private Date fachaNacimiento;
    private String seguroMedico;
    private String rh;
    private String mail;
    private String telefono;
    private String direccion;
    private String clave;
    private Short estado;
    
    
    public UsuarioController() {
    }
    
    @PostConstruct
    public void init(){
    
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TipoDocumento getTipoDocumentoList() {
        return tipoDocumentoList;
    }

    public void setTipoDocumentoList(TipoDocumento tipoDocumentoList) {
        this.tipoDocumentoList = tipoDocumentoList;
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

    public Date getFachaNacimiento() {
        return fachaNacimiento;
    }

    public void setFachaNacimiento(Date fachaNacimiento) {
        this.fachaNacimiento = fachaNacimiento;
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
    
    
    
    public List<Usuario> getUsuario(){
    
        if (usuario == null || usuario.isEmpty()) {
            usuario = ufl.findAll();
        }
        return usuario;
    }
    
    public List<TipoDocumento> getDocumento(){
        tipoDocumento = tdfl.findAll();
        return tipoDocumento;
    }
    
    
}
