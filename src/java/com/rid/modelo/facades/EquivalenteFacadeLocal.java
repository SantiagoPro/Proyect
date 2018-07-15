/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.facades;

import com.rid.modelo.entities.Equivalente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karen
 */
@Local
public interface EquivalenteFacadeLocal {

    void create(Equivalente equivalente);

    void edit(Equivalente equivalente);

    void remove(Equivalente equivalente);

    Equivalente find(Object id);

    List<Equivalente> findAll();

    List<Equivalente> findRange(int[] range);

    int count();
    
}