/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.facades;

import com.rid.modelo.entities.Deportista;
import javax.ejb.Stateless;

/**
 *
 * @author karen
 */
@Stateless
public class DeportistaFacade extends AbstractFacade<Deportista> implements DeportistaFacadeLocal {


    public DeportistaFacade() {
        super(Deportista.class);
    }
    
}
