/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.Rms;
import javax.ejb.Stateless;

/**
 *
 * @author karen
 */
@Stateless
public class RmsFacade extends AbstractFacade<Rms> implements RmsFacadeLocal {


    public RmsFacade() {
        super(Rms.class);
    }
    
}
