/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.Horario;
import javax.ejb.Stateless;


/**
 *
 * @author karen
 */
@Stateless
public class HorarioFacade extends AbstractFacade<Horario> implements HorarioFacadeLocal {


    public HorarioFacade() {
        super(Horario.class);
    }
    
}
