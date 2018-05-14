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
import com.rid.modelo.entities.Deportista;
import com.rid.modelo.entities.Imc;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class ImcJpaController implements Serializable {

    public ImcJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Imc imc) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Deportista idDeportista = imc.getIdDeportista();
            if (idDeportista != null) {
                idDeportista = em.getReference(idDeportista.getClass(), idDeportista.getIdDeportista());
                imc.setIdDeportista(idDeportista);
            }
            em.persist(imc);
            if (idDeportista != null) {
                idDeportista.getImcList().add(imc);
                idDeportista = em.merge(idDeportista);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findImc(imc.getIdImc()) != null) {
                throw new PreexistingEntityException("Imc " + imc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Imc imc) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Imc persistentImc = em.find(Imc.class, imc.getIdImc());
            Deportista idDeportistaOld = persistentImc.getIdDeportista();
            Deportista idDeportistaNew = imc.getIdDeportista();
            if (idDeportistaNew != null) {
                idDeportistaNew = em.getReference(idDeportistaNew.getClass(), idDeportistaNew.getIdDeportista());
                imc.setIdDeportista(idDeportistaNew);
            }
            imc = em.merge(imc);
            if (idDeportistaOld != null && !idDeportistaOld.equals(idDeportistaNew)) {
                idDeportistaOld.getImcList().remove(imc);
                idDeportistaOld = em.merge(idDeportistaOld);
            }
            if (idDeportistaNew != null && !idDeportistaNew.equals(idDeportistaOld)) {
                idDeportistaNew.getImcList().add(imc);
                idDeportistaNew = em.merge(idDeportistaNew);
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
                Integer id = imc.getIdImc();
                if (findImc(id) == null) {
                    throw new NonexistentEntityException("The imc with id " + id + " no longer exists.");
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
            Imc imc;
            try {
                imc = em.getReference(Imc.class, id);
                imc.getIdImc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imc with id " + id + " no longer exists.", enfe);
            }
            Deportista idDeportista = imc.getIdDeportista();
            if (idDeportista != null) {
                idDeportista.getImcList().remove(imc);
                idDeportista = em.merge(idDeportista);
            }
            em.remove(imc);
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

    public List<Imc> findImcEntities() {
        return findImcEntities(true, -1, -1);
    }

    public List<Imc> findImcEntities(int maxResults, int firstResult) {
        return findImcEntities(false, maxResults, firstResult);
    }

    private List<Imc> findImcEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Imc.class));
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

    public Imc findImc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Imc.class, id);
        } finally {
            em.close();
        }
    }

    public int getImcCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Imc> rt = cq.from(Imc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
