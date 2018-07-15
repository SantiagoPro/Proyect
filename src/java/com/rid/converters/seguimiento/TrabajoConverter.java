/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.converters.seguimiento;

import com.rid.modelo.facades.TrabajoFacadeLocal;
import com.rid.modelo.entities.Trabajo;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Santiago
 */
@FacesConverter(forClass = Trabajo.class)
public class TrabajoConverter implements Converter<Trabajo>{
    
    private TrabajoFacadeLocal tfl;
    
    public TrabajoConverter() {
        tfl = CDI.current().select(TrabajoFacadeLocal.class).get();
    }
    
    @Override
    public Trabajo getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value != null) {
                return tfl.find(Integer.valueOf(value));
            }
        } catch (NumberFormatException numberFormatException) {
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Trabajo arg2) {
        if(arg2 != null){
            return arg2.getIdTrabajo().toString();
        }
        return "";
    }
}
