/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author karen
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    public UsuarioFacade() {
        super(Usuario.class);
    }
    private EntityManager em;

    @Override
    public Usuario findByIduClv(Long idUsuario, String clave) {
        try {
            TypedQuery<Usuario> q = getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.idUsuarios = :idU AND u.clave = :clv", Usuario.class);
            q.setParameter("idU", idUsuario);
            q.setParameter("clv", clave);
            return q.getSingleResult();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<Usuario> findByIdRol(int idRol) {
        try {
            TypedQuery<Usuario> q = getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.idRol.idRol=:idRol", Usuario.class);
            q.setParameter("idRol", idRol);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public Usuario cambioClave(Long documento) {
        try {
            TypedQuery<Usuario> q = getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.idUsuarios = :idU", Usuario.class);
            q.setParameter("idU", documento);
            return q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Usuario> findByIdRoles(int idD, int idE) {
        try {
            TypedQuery<Usuario> q = getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.idRol.idRol=:idD AND u.idRol.idRol=:idE", Usuario.class);
            q.setParameter("idD", idD);
            q.setParameter("idE", idE);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
