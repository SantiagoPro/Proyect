/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controller.mail;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Santiago
 */
@Named(value = "sendMail")
@SessionScoped
public class SendMail implements Serializable {

    private String cuerpo;
    private String asunto;
    private String destinatarios;

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
        
    public void sendMail(){
        Mail.sendMailHTML(destinatarios, asunto, cuerpo);
    }
}