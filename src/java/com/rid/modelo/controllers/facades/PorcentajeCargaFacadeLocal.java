/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.PorcentajeCarga;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karen
 */
@Local
public interface PorcentajeCargaFacadeLocal {

    void create(PorcentajeCarga porcentajeCarga);

    void edit(PorcentajeCarga porcentajeCarga);

    void remove(PorcentajeCarga porcentajeCarga);

    PorcentajeCarga find(Object id);

    List<PorcentajeCarga> findAll();

    List<PorcentajeCarga> findRange(int[] range);

    int count();
    
}
