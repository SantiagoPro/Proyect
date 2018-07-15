/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.facades;

import com.rid.modelo.entities.Imc;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karen
 */
@Local
public interface ImcFacadeLocal {

    void create(Imc imc);

    void edit(Imc imc);

    void remove(Imc imc);

    Imc find(Object id);

    List<Imc> findAll();

    List<Imc> findRange(int[] range);

    int count();
    
}
