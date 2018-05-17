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
import com.rid.modelo.entities.TipoTrabajo;
import com.rid.modelo.entities.Rms;
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
public class TrabajoJpaController implements Serializable {

    public TrabajoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trabajo trabajo) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (trabajo.getRmsList() == null) {
            trabajo.setRmsList(new ArrayList<Rms>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoTrabajo idTipoTrabajo = trabajo.getIdTipoTrabajo();
            if (idTipoTrabajo != null) {
                idTipoTrabajo = em.getReference(idTipoTrabajo.getClass(), idTipoTrabajo.getIdTipoTrabajo());
                trabajo.setIdTipoTrabajo(idTipoTrabajo);
            }
            List<Rms> attachedRmsList = new ArrayList<Rms>();
            for (Rms rmsListRmsToAttach : trabajo.getRmsList()) {
                rmsListRmsToAttach = em.getReference(rmsListRmsToAttach.getClass(), rmsListRmsToAttach.getIdRms());
                attachedRmsList.add(rmsListRmsToAttach);
            }
            trabajo.setRmsList(attachedRmsList);
            em.persist(trabajo);
            if (idTipoTrabajo != null) {
                idTipoTrabajo.getTrabajoList().add(trabajo);
                idTipoTrabajo = em.merge(idTipoTrabajo);
            }
            for (Rms rmsListRms : trabajo.getRmsList()) {
                Trabajo oldIdTrabajoOfRmsListRms = rmsListRms.getIdTrabajo();
                rmsListRms.setIdTrabajo(trabajo);
                rmsListRms = em.merge(rmsListRms);
                if (oldIdTrabajoOfRmsListRms != null) {
                    oldIdTrabajoOfRmsListRms.getRmsList().remove(rmsListRms);
                    oldIdTrabajoOfRmsListRms = em.merge(oldIdTrabajoOfRmsListRms);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTrabajo(trabajo.getIdTrabajo()) != null) {
                throw new PreexistingEntityException("Trabajo " + trabajo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trabajo trabajo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Trabajo persistentTrabajo = em.find(Trabajo.class, trabajo.getIdTrabajo());
            TipoTrabajo idTipoTrabajoOld = persistentTrabajo.getIdTipoTrabajo();
            TipoTrabajo idTipoTrabajoNew = trabajo.getIdTipoTrabajo();
            List<Rms> rmsListOld = persistentTrabajo.getRmsList();
            List<Rms> rmsListNew = trabajo.getRmsList();
            if (idTipoTrabajoNew != null) {
                idTipoTrabajoNew = em.getReference(idTipoTrabajoNew.getClass(), idTipoTrabajoNew.getIdTipoTrabajo());
                trabajo.setIdTipoTrabajo(idTipoTrabajoNew);
            }
            List<Rms> attachedRmsListNew = new ArrayList<Rms>();
            for (Rms rmsListNewRmsToAttach : rmsListNew) {
                rmsListNewRmsToAttach = em.getReference(rmsListNewRmsToAttach.getClass(), rmsListNewRmsToAttach.getIdRms());
                attachedRmsListNew.add(rmsListNewRmsToAttach);
            }
            rmsListNew = attachedRmsListNew;
            trabajo.setRmsList(rmsListNew);
            trabajo = em.merge(trabajo);
            if (idTipoTrabajoOld != null && !idTipoTrabajoOld.equals(idTipoTrabajoNew)) {
                idTipoTrabajoOld.getTrabajoList().remove(trabajo);
                idTipoTrabajoOld = em.merge(idTipoTrabajoOld);
            }
            if (idTipoTrabajoNew != null && !idTipoTrabajoNew.equals(idTipoTrabajoOld)) {
                idTipoTrabajoNew.getTrabajoList().add(trabajo);
                idTipoTrabajoNew = em.merge(idTipoTrabajoNew);
            }
            for (Rms rmsListOldRms : rmsListOld) {
                if (!rmsListNew.contains(rmsListOldRms)) {
                    rmsListOldRms.setIdTrabajo(null);
                    rmsListOldRms = em.merge(rmsListOldRms);
                }
            }
            for (Rms rmsListNewRms : rmsListNew) {
                if (!rmsListOld.contains(rmsListNewRms)) {
                    Trabajo oldIdTrabajoOfRmsListNewRms = rmsListNewRms.getIdTrabajo();
                    rmsListNewRms.setIdTrabajo(trabajo);
                    rmsListNewRms = em.merge(rmsListNewRms);
                    if (oldIdTrabajoOfRmsListNewRms != null && !oldIdTrabajoOfRmsListNewRms.equals(trabajo)) {
                        oldIdTrabajoOfRmsListNewRms.getRmsList().remove(rmsListNewRms);
                        oldIdTrabajoOfRmsListNewRms = em.merge(oldIdTrabajoOfRmsListNewRms);
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
                Integer id = trabajo.getIdTrabajo();
                if (findTrabajo(id) == null) {
                    throw new NonexistentEntityException("The trabajo with id " + id + " no longer exists.");
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
            Trabajo trabajo;
            try {
                trabajo = em.getReference(Trabajo.class, id);
                trabajo.getIdTrabajo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trabajo with id " + id + " no longer exists.", enfe);
            }
            TipoTrabajo idTipoTrabajo = trabajo.getIdTipoTrabajo();
            if (idTipoTrabajo != null) {
                idTipoTrabajo.getTrabajoList().remove(trabajo);
                idTipoTrabajo = em.merge(idTipoTrabajo);
            }
            List<Rms> rmsList = trabajo.getRmsList();
            for (Rms rmsListRms : rmsList) {
                rmsListRms.setIdTrabajo(null);
                rmsListRms = em.merge(rmsListRms);
            }
            em.remove(trabajo);
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

    public List<Trabajo> findTrabajoEntities() {
        return findTrabajoEntities(true, -1, -1);
    }

    public List<Trabajo> findTrabajoEntities(int maxResults, int firstResult) {
        return findTrabajoEntities(false, maxResults, firstResult);
    }

    private List<Trabajo> findTrabajoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trabajo.class));
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

    public Trabajo findTrabajo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trabajo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrabajoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trabajo> rt = cq.from(Trabajo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
