/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author ismael
 */
public class MessagesUtil {
    
    public final static void addMessage(String clientId, String message, String detail,
            FacesMessage.Severity severity, Boolean prop){
        if(prop){
            prop();
        }
        FacesMessage fm = new FacesMessage(severity, message, detail);
        FacesContext.getCurrentInstance().addMessage(clientId, fm);
    }
    
    public final static void fatal(String clientId, String message, String detail, Boolean prop){
        addMessage(clientId, message, detail, FacesMessage.SEVERITY_FATAL, prop);
    }
    public final static void error(String clientId, String message, String detail, Boolean prop){
        addMessage(clientId, message, detail, FacesMessage.SEVERITY_ERROR, prop);
    }
    
    public final static void warn(String clientId, String message, String detail, Boolean prop){
        addMessage(clientId, message, detail, FacesMessage.SEVERITY_WARN, prop);
    }
    
    public final static void info(String clientId, String message, String detail, Boolean prop){
        addMessage(clientId, message, detail, FacesMessage.SEVERITY_INFO, prop);
    }
    
    public final static void prop(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
    
}
