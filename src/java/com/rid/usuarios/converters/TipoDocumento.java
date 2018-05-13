/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.usuarios.converters;

import com.rid.modelo.controllers.facades.TipoDocumentoFacadeLocal;
import com.rid.modelo.entities.TipoDocumento;
import javax.ejb.EJB;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

/**
 *
 * @author Santiago
 */
@FacesConverter(value = "tipoDocumento", managed = true)
public class TipoDocumento implements Converter<TipoDocumento>{

    @EJB
    private TipoDocumentoFacadeLocal tdfl;
    
    public TipoDocumento(){
        tdfl = CDI.current().select(TipoDocumentoFacadeLocal.class).get();
    }
    
    @Override
    public TipoDocumento getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Long id = Long.valueOf(value);
            return tdfl.find(id);
        } catch (NumberFormatException numberFormatException) {
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, TipoDocumento arg2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
