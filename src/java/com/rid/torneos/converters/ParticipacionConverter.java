/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.torneos.converters;

import com.rid.modelo.controllers.facades.ParticipacionFacadeLocal;
import com.rid.modelo.entities.Participacion;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author karen
 */
@FacesConverter(forClass = Participacion.class)
public class ParticipacionConverter implements Converter<Participacion>{

    private ParticipacionFacadeLocal pfl;

    public ParticipacionConverter() {
        pfl = CDI.current().select(ParticipacionFacadeLocal.class).get();
    }
    
    
    @Override
    public Participacion getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Integer id = Integer.valueOf(value);
            return pfl.find(id);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Participacion obj) {
        if (obj != null) {
            return obj.getIdParticipacion().toString();
        }
        return "";
    }
    
}
