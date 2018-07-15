/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.converters.torneos;

import com.rid.modelo.facades.TorneoFacadeLocal;
import com.rid.modelo.entities.Torneo;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author karen
 */
@FacesConverter(forClass = Torneo.class)
public class TorneoConverter implements Converter<Torneo>{

    private TorneoFacadeLocal tfl;

    public TorneoConverter() {
        tfl = CDI.current().select(TorneoFacadeLocal.class).get();
    }
    
    
    @Override
    public Torneo getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Integer id = Integer.valueOf(value);
            return tfl.find(id);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Torneo t) {
        if (t != null) {
            return t.getIdTorneo().toString();
        }
        return "";
    }
    
}
