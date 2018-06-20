/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controller.mail;

import com.rid.modelo.entities.Usuario;
import com.rid.modelo.controllers.facades.UsuarioFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Santiago
 */
@Named(value = "sendMail")
@SessionScoped
public class SendMail implements Serializable {

    @EJB
    private UsuarioFacadeLocal ufl;
    
    private Usuario user;
    private String cuerpo;
    private String asunto;
    private String destinatarios;
    private Long   documento;
    
    private String clave;
    private String clave2;
    

    public SendMail() {
    }

    @PostConstruct
    public void init() {

    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(String destinatarios) {
        this.destinatarios = destinatarios;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getClave2() {
        return clave2;
    }

    public void setClave2(String clave2) {
        this.clave2 = clave2;
    }

    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }
    
        
    public void sendMail(){
        Mail.sendMailHTML(destinatarios, asunto, cuerpo);
    }
    
    public void sendMailRecuperacion(){
        Mail.sendMailHTML(destinatarios, "Cambio de Contraseña", "<p>Se ha notificado un cambio de contraseña, para confirmar ingrese al siguiente link</p>"
                + "http://10.1.17.104:8080//recuperarClave.xhtml");
    }
    
    public String validarContraseña(){
        user = ufl.cambioClave(documento);
        try {
            if (user != null) {
                if (clave.equals(clave2)) {
                user.setClave(clave);
                ufl.edit(user);
                System.out.println("contraseña Cambiada");
                return "/index.xhtml?faces-redirect=true";
                }
            } else {
                System.out.println("Usuario no existe");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    }