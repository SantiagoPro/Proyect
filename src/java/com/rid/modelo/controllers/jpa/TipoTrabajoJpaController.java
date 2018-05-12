/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.jpa;

import com.rid.modelo.controllers.jpa.exceptions.NonexistentEntityException;
import com.rid.modelo.controllers.jpa.exceptions.PreexistingEntityException;
import com.rid.modelo.controllers.jpa.exceptions.RollbackFailureException;
import com.rid.modelo.entities.TipoTrabajo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rid.modelo.entities.Trabajo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class TipoTrabajoJpaController implements Serializable {

    public TipoTrabajoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTrabajo tipoTrabajo) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tipoTrabajo.getTrabajoList() == null) {
            tipoTrabajo.setTrabajoList(new ArrayList<Trabajo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Trabajo> attachedTrabajoList = new ArrayList<Trabajo>();
            for (Trabajo trabajoListTrabajoToAttach : tipoTrabajo.getTrabajoList()) {
                trabajoListTrabajoToAttach = em.getReference(trabajoListTrabajoToAttach.getClass(), trabajoListTrabajoToAttach.getIdTrabajo());
                attachedTrabajoList.add(trabajoListTrabajoToAttach);
            }
            tipoTrabajo.setTrabajoList(attachedTrabajoList);
            em.persist(tipoTrabajo);
            for (Trabajo trabajoListTrabajo : tipoTrabajo.getTrabajoList()) {
                TipoTrabajo oldIdTipoTrabajoOfTrabajoListTrabajo = trabajoListTrabajo.getIdTipoTrabajo();
                trabajoListTrabajo.setIdTipoTrabajo(tipoTrabajo);
                trabajoListTrabajo = em.merge(trabajoListTrabajo);
                if (oldIdTipoTrabajoOfTrabajoListTrabajo != null) {
                    oldIdTipoTrabajoOfTrabajoListTrabajo.getTrabajoList().remove(trabajoListTrabajo);
                    oldIdTipoTrabajoOfTrabajoListTrabajo = em.merge(oldIdTipoTrabajoOfTrabajoListTrabajo);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTipoTrabajo(tipoTrabajo.getIdTipoTrabajo()) != null) {
                throw new PreexistingEntityException("TipoTrabajo " + tipoTrabajo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTrabajo tipoTrabajo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoTrabajo persistentTipoTrabajo = em.find(TipoTrabajo.class, tipoTrabajo.getIdTipoTrabajo());
            List<Trabajo> trabajoListOld = persistentTipoTrabajo.getTrabajoList();
            List<Trabajo> trabajoListNew = tipoTrabajo.getTrabajoList();
            List<Trabajo> attachedTrabajoListNew = new ArrayList<Trabajo>();
            for (Trabajo trabajoListNewTrabajoToAttach : trabajoListNew) {
                trabajoListNewTrabajoToAttach = em.getReference(trabajoListNewTrabajoToAttach.getClass(), trabajoListNewTrabajoToAttach.getIdTrabajo());
                attachedTrabajoListNew.add(trabajoListNewTrabajoToAttach);
            }
            trabajoListNew = attachedTrabajoListNew;
            tipoTrabajo.setTrabajoList(trabajoListNew);
            tipoTrabajo = em.merge(tipoTrabajo);
            for (Trabajo trabajoListOldTrabajo : trabajoListOld) {
                if (!trabajoListNew.contains(trabajoListOldTrabajo)) {
                    trabajoListOldTrabajo.setIdTipoTrabajo(null);
                    trabajoListOldTrabajo = em.merge(trabajoListOldTrabajo);
                }
            }
            for (Trabajo trabajoListNewTrabajo : trabajoListNew) {
                if (!trabajoListOld.contains(trabajoListNewTrabajo)) {
                    TipoTrabajo oldIdTipoTrabajoOfTrabajoListNewTrabajo = trabajoListNewTrabajo.getIdTipoTrabajo();
                    trabajoListNewTrabajo.setIdTipoTrabajo(tipoTrabajo);
                    trabajoListNewTrabajo = em.merge(trabajoListNewTrabajo);
                    if (oldIdTipoTrabajoOfTrabajoListNewTrabajo != null && !oldIdTipoTrabajoOfTrabajoListNewTrabajo.equals(tipoTrabajo)) {
                        oldIdTipoTrabajoOfTrabajoListNewTrabajo.getTrabajoList().remove(trabajoListNewTrabajo);
                        oldIdTipoTrabajoOfTrabajoListNewTrabajo = em.merge(oldIdTipoTrabajoOfTrabajoListNewTrabajo);
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
                Integer id = tipoTrabajo.getIdTipoTrabajo();
                if (findTipoTrabajo(id) == null) {
                    throw new NonexistentEntityException("The tipoTrabajo with id " + id + " no longer exists.");
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
            TipoTrabajo tipoTrabajo;
            try {
                tipoTrabajo = em.getReference(TipoTrabajo.class, id);
                tipoTrabajo.getIdTipoTrabajo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTrabajo with id " + id + " no longer exists.", enfe);
            }
            List<Trabajo> trabajoList = tipoTrabajo.getTrabajoList();
            for (Trabajo trabajoListTrabajo : trabajoList) {
                trabajoListTrabajo.setIdTipoTrabajo(null);
                trabajoListTrabajo = em.merge(trabajoListTrabajo);
            }
            em.remove(tipoTrabajo);
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

    public List<TipoTrabajo> findTipoTrabajoEntities() {
        return findTipoTrabajoEntities(true, -1, -1);
    }

    public List<TipoTrabajo> findTipoTrabajoEntities(int maxResults, int firstResult) {
        return findTipoTrabajoEntities(false, maxResults, firstResult);
    }

    private List<TipoTrabajo> findTipoTrabajoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTrabajo.class));
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

    public TipoTrabajo findTipoTrabajo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTrabajo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTrabajoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTrabajo> rt = cq.from(TipoTrabajo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
