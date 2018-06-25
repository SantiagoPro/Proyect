/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.usuarios.converters;

import com.rid.modelo.controllers.facades.EntrenadorFacadeLocal;
import com.rid.modelo.entities.Entrenador;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author karen
 */
@FacesConverter(forClass = Entrenador.class)
public class EntrenadorConverter implements Converter<Entrenador>{

    private EntrenadorFacadeLocal efl;

    public EntrenadorConverter() {
        efl = CDI.current().select(EntrenadorFacadeLocal.class).get();
    }
    
    @Override
    public Entrenador getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Integer id = Integer.valueOf(value);
            return efl.find(id);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Entrenador e) {
        if (e != null) {
            return e.getIdEntrenador().toString();
        }
        return "";
    }
    
}
