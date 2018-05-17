/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.TipoTrabajo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karen
 */
@Local
public interface TipoTrabajoFacadeLocal {

    void create(TipoTrabajo tipoTrabajo);

    void edit(TipoTrabajo tipoTrabajo);

    void remove(TipoTrabajo tipoTrabajo);

    TipoTrabajo find(Object id);

    List<TipoTrabajo> findAll();

    List<TipoTrabajo> findRange(int[] range);

    int count();
    
}
