/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.jpa;

import com.rid.modelo.controllers.jpa.exceptions.NonexistentEntityException;
import com.rid.modelo.controllers.jpa.exceptions.PreexistingEntityException;
import com.rid.modelo.controllers.jpa.exceptions.RollbackFailureException;
import com.rid.modelo.entities.Equivalente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rid.modelo.entities.Rms;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class EquivalenteJpaController implements Serializable {

    public EquivalenteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equivalente equivalente) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (equivalente.getRmsList() == null) {
            equivalente.setRmsList(new ArrayList<Rms>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Rms> attachedRmsList = new ArrayList<Rms>();
            for (Rms rmsListRmsToAttach : equivalente.getRmsList()) {
                rmsListRmsToAttach = em.getReference(rmsListRmsToAttach.getClass(), rmsListRmsToAttach.getIdRms());
                attachedRmsList.add(rmsListRmsToAttach);
            }
            equivalente.setRmsList(attachedRmsList);
            em.persist(equivalente);
            for (Rms rmsListRms : equivalente.getRmsList()) {
                Equivalente oldIdEquivalenteOfRmsListRms = rmsListRms.getIdEquivalente();
                rmsListRms.setIdEquivalente(equivalente);
                rmsListRms = em.merge(rmsListRms);
                if (oldIdEquivalenteOfRmsListRms != null) {
                    oldIdEquivalenteOfRmsListRms.getRmsList().remove(rmsListRms);
                    oldIdEquivalenteOfRmsListRms = em.merge(oldIdEquivalenteOfRmsListRms);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEquivalente(equivalente.getIdEquivalente()) != null) {
                throw new PreexistingEntityException("Equivalente " + equivalente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equivalente equivalente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Equivalente persistentEquivalente = em.find(Equivalente.class, equivalente.getIdEquivalente());
            List<Rms> rmsListOld = persistentEquivalente.getRmsList();
            List<Rms> rmsListNew = equivalente.getRmsList();
            List<Rms> attachedRmsListNew = new ArrayList<Rms>();
            for (Rms rmsListNewRmsToAttach : rmsListNew) {
                rmsListNewRmsToAttach = em.getReference(rmsListNewRmsToAttach.getClass(), rmsListNewRmsToAttach.getIdRms());
                attachedRmsListNew.add(rmsListNewRmsToAttach);
            }
            rmsListNew = attachedRmsListNew;
            equivalente.setRmsList(rmsListNew);
            equivalente = em.merge(equivalente);
            for (Rms rmsListOldRms : rmsListOld) {
                if (!rmsListNew.contains(rmsListOldRms)) {
                    rmsListOldRms.setIdEquivalente(null);
                    rmsListOldRms = em.merge(rmsListOldRms);
                }
            }
            for (Rms rmsListNewRms : rmsListNew) {
                if (!rmsListOld.contains(rmsListNewRms)) {
                    Equivalente oldIdEquivalenteOfRmsListNewRms = rmsListNewRms.getIdEquivalente();
                    rmsListNewRms.setIdEquivalente(equivalente);
                    rmsListNewRms = em.merge(rmsListNewRms);
                    if (oldIdEquivalenteOfRmsListNewRms != null && !oldIdEquivalenteOfRmsListNewRms.equals(equivalente)) {
                        oldIdEquivalenteOfRmsListNewRms.getRmsList().remove(rmsListNewRms);
                        oldIdEquivalenteOfRmsListNewRms = em.merge(oldIdEquivalenteOfRmsListNewRms);
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
                Integer id = equivalente.getIdEquivalente();
                if (findEquivalente(id) == null) {
                    throw new NonexistentEntityException("The equivalente with id " + id + " no longer exists.");
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
            Equivalente equivalente;
            try {
                equivalente = em.getReference(Equivalente.class, id);
                equivalente.getIdEquivalente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equivalente with id " + id + " no longer exists.", enfe);
            }
            List<Rms> rmsList = equivalente.getRmsList();
            for (Rms rmsListRms : rmsList) {
                rmsListRms.setIdEquivalente(null);
                rmsListRms = em.merge(rmsListRms);
            }
            em.remove(equivalente);
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

    public List<Equivalente> findEquivalenteEntities() {
        return findEquivalenteEntities(true, -1, -1);
    }

    public List<Equivalente> findEquivalenteEntities(int maxResults, int firstResult) {
        return findEquivalenteEntities(false, maxResults, firstResult);
    }

    private List<Equivalente> findEquivalenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equivalente.class));
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

    public Equivalente findEquivalente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equivalente.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquivalenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equivalente> rt = cq.from(Equivalente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
