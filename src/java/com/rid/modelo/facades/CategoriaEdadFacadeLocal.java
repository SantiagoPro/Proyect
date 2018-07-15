/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.facades;

import com.rid.modelo.entities.CategoriaEdad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karen
 */
@Local
public interface CategoriaEdadFacadeLocal {

    void create(CategoriaEdad categoriaEdad);

    void edit(CategoriaEdad categoriaEdad);

    void remove(CategoriaEdad categoriaEdad);

    CategoriaEdad find(Object id);

    List<CategoriaEdad> findAll();

    List<CategoriaEdad> findRange(int[] range);

    int count();
    
}
