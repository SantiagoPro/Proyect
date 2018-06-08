/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author karen
*/
@FacesConverter(value = "fechaConverter")
public class FechaConverter implements Converter<Date>{

    private SimpleDateFormat sdf;
    
    public FechaConverter(){
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }
            
    @Override
    public Date getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return sdf.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Date fecha) {
        if (fecha != null) {
            return sdf.format(fecha);
        }
        return "";
    }
    
}
