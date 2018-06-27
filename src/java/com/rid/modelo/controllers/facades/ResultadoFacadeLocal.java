/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.Resultado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karen
 */
@Local
public interface ResultadoFacadeLocal {

    void create(Resultado resultado);

    void edit(Resultado resultado);

    void remove(Resultado resultado);

    Resultado find(Object id);

    List<Resultado> findAll();

    List<Resultado> findRange(int[] range);
    
    List<Resultado> resultados(); 

    int count();
    
}
