/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.facades;

import com.rid.modelo.entities.Permiso;
import javax.ejb.Stateless;

/**
 *
 * @author karen
 */
@Stateless
public class PermisoFacade extends AbstractFacade<Permiso> implements PermisoFacadeLocal {


    public PermisoFacade() {
        super(Permiso.class);
    }
    
}
