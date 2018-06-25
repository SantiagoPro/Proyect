/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.usuarios.converters;

import com.rid.modelo.controllers.facades.RolFacadeLocal;
import com.rid.modelo.entities.Rol;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author karen
 */
@FacesConverter(forClass = Rol.class)
public class RolConverter implements Converter<Rol>{

    private RolFacadeLocal rfl;

    public RolConverter() {
        rfl = CDI.current().select(RolFacadeLocal.class).get();
    }
    
    
    @Override
    public Rol getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Integer id = Integer.valueOf(value);
            return rfl.find(id);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Rol obj) {
        if (obj != null) {
            return obj.getIdRol().toString();
        }
        return "";
    }   
}
