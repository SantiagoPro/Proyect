/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.TipoEntrenamiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karen
 */
@Local
public interface TipoEntrenamientoFacadeLocal {

    void create(TipoEntrenamiento tipoEntrenamiento);

    void edit(TipoEntrenamiento tipoEntrenamiento);

    void remove(TipoEntrenamiento tipoEntrenamiento);

    TipoEntrenamiento find(Object id);

    List<TipoEntrenamiento> findAll();

    List<TipoEntrenamiento> findRange(int[] range);

    int count();
    
}
