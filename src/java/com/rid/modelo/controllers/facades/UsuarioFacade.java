/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.Usuario;
import javax.ejb.Stateless;

/**
 *
 * @author karen
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
}
