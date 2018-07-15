/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.facades;

import com.rid.modelo.entities.Participacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karen
 */
@Local
public interface ParticipacionFacadeLocal {

    void create(Participacion participacion);

    void edit(Participacion participacion);

    void remove(Participacion participacion);

    Participacion find(Object id);

    List<Participacion> findAll();

    List<Participacion> findRange(int[] range);

    int count();
    
}
