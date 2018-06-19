/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.usuarios.converters;

import com.rid.modelo.controllers.facades.TipoDocumentoFacadeLocal;
import com.rid.modelo.entities.TipoDocumento;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author karen
 */
@FacesConverter(forClass = TipoDocumento.class)
public class TipoDocumentoConverter implements Converter<TipoDocumento>{

    private TipoDocumentoFacadeLocal tdfl;

    public TipoDocumentoConverter() {
        tdfl = CDI.current().select(TipoDocumentoFacadeLocal.class).get();
    }
    
    
    @Override
    public TipoDocumento getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Integer id = Integer.valueOf(value);
            return tdfl.find(id);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, TipoDocumento obj) {
        if (obj != null) {
            return obj.getIdTipoDocumento().toString();
        }
        return "";
    }   
}
