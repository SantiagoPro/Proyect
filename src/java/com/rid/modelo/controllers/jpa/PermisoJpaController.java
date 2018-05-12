/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.jpa;

import com.rid.modelo.controllers.jpa.exceptions.NonexistentEntityException;
import com.rid.modelo.controllers.jpa.exceptions.PreexistingEntityException;
import com.rid.modelo.controllers.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rid.modelo.entities.Permiso;
import com.rid.modelo.entities.Rol;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class PermisoJpaController implements Serializable {

    public PermisoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Permiso permiso) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (permiso.getRolList() == null) {
            permiso.setRolList(new ArrayList<Rol>());
        }
        if (permiso.getPermisoList() == null) {
            permiso.setPermisoList(new ArrayList<Permiso>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Permiso permisoPadre = permiso.getPermisoPadre();
            if (permisoPadre != null) {
                permisoPadre = em.getReference(permisoPadre.getClass(), permisoPadre.getIdPermiso());
                permiso.setPermisoPadre(permisoPadre);
            }
            List<Rol> attachedRolList = new ArrayList<Rol>();
            for (Rol rolListRolToAttach : permiso.getRolList()) {
                rolListRolToAttach = em.getReference(rolListRolToAttach.getClass(), rolListRolToAttach.getIdRol());
                attachedRolList.add(rolListRolToAttach);
            }
            permiso.setRolList(attachedRolList);
            List<Permiso> attachedPermisoList = new ArrayList<Permiso>();
            for (Permiso permisoListPermisoToAttach : permiso.getPermisoList()) {
                permisoListPermisoToAttach = em.getReference(permisoListPermisoToAttach.getClass(), permisoListPermisoToAttach.getIdPermiso());
                attachedPermisoList.add(permisoListPermisoToAttach);
            }
            permiso.setPermisoList(attachedPermisoList);
            em.persist(permiso);
            if (permisoPadre != null) {
                permisoPadre.getPermisoList().add(permiso);
                permisoPadre = em.merge(permisoPadre);
            }
            for (Rol rolListRol : permiso.getRolList()) {
                rolListRol.getPermisoList().add(permiso);
                rolListRol = em.merge(rolListRol);
            }
            for (Permiso permisoListPermiso : permiso.getPermisoList()) {
                Permiso oldPermisoPadreOfPermisoListPermiso = permisoListPermiso.getPermisoPadre();
                permisoListPermiso.setPermisoPadre(permiso);
                permisoListPermiso = em.merge(permisoListPermiso);
                if (oldPermisoPadreOfPermisoListPermiso != null) {
                    oldPermisoPadreOfPermisoListPermiso.getPermisoList().remove(permisoListPermiso);
                    oldPermisoPadreOfPermisoListPermiso = em.merge(oldPermisoPadreOfPermisoListPermiso);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPermiso(permiso.getIdPermiso()) != null) {
                throw new PreexistingEntityException("Permiso " + permiso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Permiso permiso) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Permiso persistentPermiso = em.find(Permiso.class, permiso.getIdPermiso());
            Permiso permisoPadreOld = persistentPermiso.getPermisoPadre();
            Permiso permisoPadreNew = permiso.getPermisoPadre();
            List<Rol> rolListOld = persistentPermiso.getRolList();
            List<Rol> rolListNew = permiso.getRolList();
            List<Permiso> permisoListOld = persistentPermiso.getPermisoList();
            List<Permiso> permisoListNew = permiso.getPermisoList();
            if (permisoPadreNew != null) {
                permisoPadreNew = em.getReference(permisoPadreNew.getClass(), permisoPadreNew.getIdPermiso());
                permiso.setPermisoPadre(permisoPadreNew);
            }
            List<Rol> attachedRolListNew = new ArrayList<Rol>();
            for (Rol rolListNewRolToAttach : rolListNew) {
                rolListNewRolToAttach = em.getReference(rolListNewRolToAttach.getClass(), rolListNewRolToAttach.getIdRol());
                attachedRolListNew.add(rolListNewRolToAttach);
            }
            rolListNew = attachedRolListNew;
            permiso.setRolList(rolListNew);
            List<Permiso> attachedPermisoListNew = new ArrayList<Permiso>();
            for (Permiso permisoListNewPermisoToAttach : permisoListNew) {
                permisoListNewPermisoToAttach = em.getReference(permisoListNewPermisoToAttach.getClass(), permisoListNewPermisoToAttach.getIdPermiso());
                attachedPermisoListNew.add(permisoListNewPermisoToAttach);
            }
            permisoListNew = attachedPermisoListNew;
            permiso.setPermisoList(permisoListNew);
            permiso = em.merge(permiso);
            if (permisoPadreOld != null && !permisoPadreOld.equals(permisoPadreNew)) {
                permisoPadreOld.getPermisoList().remove(permiso);
                permisoPadreOld = em.merge(permisoPadreOld);
            }
            if (permisoPadreNew != null && !permisoPadreNew.equals(permisoPadreOld)) {
                permisoPadreNew.getPermisoList().add(permiso);
                permisoPadreNew = em.merge(permisoPadreNew);
            }
            for (Rol rolListOldRol : rolListOld) {
                if (!rolListNew.contains(rolListOldRol)) {
                    rolListOldRol.getPermisoList().remove(permiso);
                    rolListOldRol = em.merge(rolListOldRol);
                }
            }
            for (Rol rolListNewRol : rolListNew) {
                if (!rolListOld.contains(rolListNewRol)) {
                    rolListNewRol.getPermisoList().add(permiso);
                    rolListNewRol = em.merge(rolListNewRol);
                }
            }
            for (Permiso permisoListOldPermiso : permisoListOld) {
                if (!permisoListNew.contains(permisoListOldPermiso)) {
                    permisoListOldPermiso.setPermisoPadre(null);
                    permisoListOldPermiso = em.merge(permisoListOldPermiso);
                }
            }
            for (Permiso permisoListNewPermiso : permisoListNew) {
                if (!permisoListOld.contains(permisoListNewPermiso)) {
                    Permiso oldPermisoPadreOfPermisoListNewPermiso = permisoListNewPermiso.getPermisoPadre();
                    permisoListNewPermiso.setPermisoPadre(permiso);
                    permisoListNewPermiso = em.merge(permisoListNewPermiso);
                    if (oldPermisoPadreOfPermisoListNewPermiso != null && !oldPermisoPadreOfPermisoListNewPermiso.equals(permiso)) {
                        oldPermisoPadreOfPermisoListNewPermiso.getPermisoList().remove(permisoListNewPermiso);
                        oldPermisoPadreOfPermisoListNewPermiso = em.merge(oldPermisoPadreOfPermisoListNewPermiso);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = permiso.getIdPermiso();
                if (findPermiso(id) == null) {
                    throw new NonexistentEntityException("The permiso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Permiso permiso;
            try {
                permiso = em.getReference(Permiso.class, id);
                permiso.getIdPermiso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permiso with id " + id + " no longer exists.", enfe);
            }
            Permiso permisoPadre = permiso.getPermisoPadre();
            if (permisoPadre != null) {
                permisoPadre.getPermisoList().remove(permiso);
                permisoPadre = em.merge(permisoPadre);
            }
            List<Rol> rolList = permiso.getRolList();
            for (Rol rolListRol : rolList) {
                rolListRol.getPermisoList().remove(permiso);
                rolListRol = em.merge(rolListRol);
            }
            List<Permiso> permisoList = permiso.getPermisoList();
            for (Permiso permisoListPermiso : permisoList) {
                permisoListPermiso.setPermisoPadre(null);
                permisoListPermiso = em.merge(permisoListPermiso);
            }
            em.remove(permiso);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Permiso> findPermisoEntities() {
        return findPermisoEntities(true, -1, -1);
    }

    public List<Permiso> findPermisoEntities(int maxResults, int firstResult) {
        return findPermisoEntities(false, maxResults, firstResult);
    }

    private List<Permiso> findPermisoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permiso.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Permiso findPermiso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permiso.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermisoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permiso> rt = cq.from(Permiso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
