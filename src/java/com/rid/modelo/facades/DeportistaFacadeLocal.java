/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.facades;

import com.rid.modelo.entities.Deportista;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karen
 */
@Local
public interface DeportistaFacadeLocal {

    void create(Deportista deportista);

    void edit(Deportista deportista);

    void remove(Deportista deportista);

    Deportista find(Object id);

    List<Deportista> findAll();

    List<Deportista> findRange(int[] range);

    int count();
    
}
