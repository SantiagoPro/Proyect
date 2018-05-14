/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.TipoEntrenamiento;
import javax.ejb.Stateless;

/**
 *
 * @author karen
 */
@Stateless
public class TipoEntrenamientoFacade extends AbstractFacade<TipoEntrenamiento> implements TipoEntrenamientoFacadeLocal {


    public TipoEntrenamientoFacade() {
        super(TipoEntrenamiento.class);
    }
    
}
