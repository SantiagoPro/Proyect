/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.Equivalente;
import javax.ejb.Stateless;

/**
 *
 * @author karen
 */
@Stateless
public class EquivalenteFacade extends AbstractFacade<Equivalente> implements EquivalenteFacadeLocal {


    public EquivalenteFacade() {
        super(Equivalente.class);
    }
    
}
