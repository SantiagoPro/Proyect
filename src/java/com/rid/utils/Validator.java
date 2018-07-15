/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.utils;

import javax.annotation.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

/**
 *
 * @author Santiago
 */
@ManagedBean
@Named(value = "validatorController")
public class Validator {

    public void inputDocumentValidator(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        if (((Integer) arg2).longValue() < 10) {
            MessagesUtil.warn("DOC01", "Error", "Debe ingresar minimo 10 numeros", Boolean.TRUE);
        } else if (((Integer) arg2).longValue() > 13) {
            MessagesUtil.warn("DOC02", "Error", "Debe ingresar maximo 13 numeros", Boolean.TRUE);
        }
    }

}
