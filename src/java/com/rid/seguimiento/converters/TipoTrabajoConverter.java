/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.seguimiento.converters;

import com.rid.modelo.controllers.facades.TipoTrabajoFacade;
import com.rid.modelo.controllers.facades.TipoTrabajoFacadeLocal;
import com.rid.modelo.entities.TipoTrabajo;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Santiago
 */
@FacesConverter(forClass = TipoTrabajo.class)
public class TipoTrabajoConverter implements Converter<TipoTrabajo>{

    private TipoTrabajoFacadeLocal ttfl;
    
    public TipoTrabajoConverter() {
        ttfl = CDI.current().select(TipoTrabajoFacadeLocal.class).get();
    }

    
    
    @Override
    public TipoTrabajo getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value != null) {
                return ttfl.find(Integer.valueOf(value));
            }
        } catch (NumberFormatException numberFormatException) {
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, TipoTrabajo arg2) {
        if(arg2 != null){
            return arg2.getIdTipoTrabajo().toString();
        }
        return "";
    }
    
}
