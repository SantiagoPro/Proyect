/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.Entrenamiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karen
 */
@Local
public interface EntrenamientoFacadeLocal {

    void create(Entrenamiento entrenamiento);

    void edit(Entrenamiento entrenamiento);

    void remove(Entrenamiento entrenamiento);

    Entrenamiento find(Object id);

    List<Entrenamiento> findAll();

    List<Entrenamiento> findRange(int[] range);

    int count();
    
}
