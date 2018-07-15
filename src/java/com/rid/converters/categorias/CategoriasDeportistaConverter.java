/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.converters.categorias;

import com.rid.modelo.facades.CategoriaDeportistaFacadeLocal;
import com.rid.modelo.entities.CategoriaDeportista;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author karen
 */
@FacesConverter(forClass = CategoriaDeportista.class)
public class CategoriasDeportistaConverter implements Converter<CategoriaDeportista>{

    private CategoriaDeportistaFacadeLocal cdfl;

    public CategoriasDeportistaConverter() {
        cdfl = CDI.current().select(CategoriaDeportistaFacadeLocal.class).get();
    }
    
    
    @Override
    public CategoriaDeportista getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Integer id = Integer.valueOf(value);
            return cdfl.find(id);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, CategoriaDeportista cd) {
        if (cd != null) {
            return cd.getIdCategoriaDeportista().toString();
        }
        return "";
    }
    
}
