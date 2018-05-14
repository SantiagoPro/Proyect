/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.Rms;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karen
 */
@Local
public interface RmsFacadeLocal {

    void create(Rms rms);

    void edit(Rms rms);

    void remove(Rms rms);

    Rms find(Object id);

    List<Rms> findAll();

    List<Rms> findRange(int[] range);

    int count();
    
}
