/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.facades;

import com.rid.modelo.entities.CategoriaDeportista;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karen
 */
@Local
public interface CategoriaDeportistaFacadeLocal {

    void create(CategoriaDeportista categoriaDeportista);

    void edit(CategoriaDeportista categoriaDeportista);

    void remove(CategoriaDeportista categoriaDeportista);

    CategoriaDeportista find(Object id);

    List<CategoriaDeportista> findAll();

    List<CategoriaDeportista> findRange(int[] range);

    int count();
    
}
