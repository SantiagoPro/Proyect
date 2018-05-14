/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.Rol;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author karen
 */
@Stateless
public class RolFacade extends AbstractFacade<Rol> implements RolFacadeLocal {


    public RolFacade() {
        super(Rol.class);
    }

    /**
     *
     * @param idRol
     * @return
     */
    @Override
    public Rol findByIdRol(Integer idRol) {
        try {
            TypedQuery<Rol> q = getEntityManager().createQuery("SELECT r FROM Rol r WHERE r.idRol = :idR", Rol.class);
            q.setParameter("idR", idRol);
            return q.getSingleResult();
        } catch (Exception e) {
        }
        return null;
    }
    
}
