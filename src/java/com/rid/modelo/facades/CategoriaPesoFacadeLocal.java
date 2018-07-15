/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.facades;

import com.rid.modelo.entities.CategoriaPeso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karen
 */
@Local
public interface CategoriaPesoFacadeLocal {

    void create(CategoriaPeso categoriaPeso);

    void edit(CategoriaPeso categoriaPeso);

    void remove(CategoriaPeso categoriaPeso);

    CategoriaPeso find(Object id);

    List<CategoriaPeso> findAll();

    List<CategoriaPeso> findRange(int[] range);

    int count();
    
}
