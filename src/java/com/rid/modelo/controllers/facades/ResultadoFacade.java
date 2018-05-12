/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.Resultado;
import javax.ejb.Stateless;

/**
 *
 * @author karen
 */
@Stateless
public class ResultadoFacade extends AbstractFacade<Resultado> implements ResultadoFacadeLocal {


    public ResultadoFacade() {
        super(Resultado.class);
    }
    
}
