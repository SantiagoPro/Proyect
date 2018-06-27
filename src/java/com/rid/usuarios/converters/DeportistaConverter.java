/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.usuarios.converters;

import com.rid.modelo.controllers.facades.DeportistaFacadeLocal;
import com.rid.modelo.entities.Deportista;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author karen
 */
@FacesConverter(forClass = Deportista.class)
public class DeportistaConverter implements Converter<Deportista>{

    private DeportistaFacadeLocal dfl;
    
    public DeportistaConverter() {
        dfl = CDI.current().select(DeportistaFacadeLocal.class).get();
    }

    @Override
    public Deportista getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Integer id = Integer.valueOf(value);
            return dfl.find(id);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Deportista d) {
        if (d != null) {
            return d.getIdDeportista().toString();
        }
        return "";
    }
    
}
