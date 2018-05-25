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
import javax.persistence.PersistenceContext;
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

    /*
    @Override
    public Usuario findByIduClv(Long idUsuario, String clave) {
        try {
            TypedQuery<Usuario> q = getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.idUsuarios=:idU AND u.clave=:clb", Usuario.class);
            q.setParameter("idU", idUsuario);
            q.setParameter("clv", clave);
            List<Usuario> user = q.getResultList();
            if (user != null && user.size() == 1) {
                user.get(0);
            }
            return null;
        } catch (Exception e) {
        }
        return null;
    }
     */
}
