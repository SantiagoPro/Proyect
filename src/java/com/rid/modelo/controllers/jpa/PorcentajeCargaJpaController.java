/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.jpa;

import com.rid.modelo.controllers.jpa.exceptions.NonexistentEntityException;
import com.rid.modelo.controllers.jpa.exceptions.PreexistingEntityException;
import com.rid.modelo.controllers.jpa.exceptions.RollbackFailureException;
import com.rid.modelo.entities.PorcentajeCarga;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rid.modelo.entities.TipoEntrenamiento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class PorcentajeCargaJpaController implements Serializable {

    public PorcentajeCargaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PorcentajeCarga porcentajeCarga) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoEntrenamiento idTipoEntrenamiento = porcentajeCarga.getIdTipoEntrenamiento();
            if (idTipoEntrenamiento != null) {
                idTipoEntrenamiento = em.getReference(idTipoEntrenamiento.getClass(), idTipoEntrenamiento.getIdTipoEntrenamiento());
                porcentajeCarga.setIdTipoEntrenamiento(idTipoEntrenamiento);
            }
            em.persist(porcentajeCarga);
            if (idTipoEntrenamiento != null) {
                idTipoEntrenamiento.getPorcentajeCargaList().add(porcentajeCarga);
                idTipoEntrenamiento = em.merge(idTipoEntrenamiento);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPorcentajeCarga(porcentajeCarga.getIdPorcentaje()) != null) {
                throw new PreexistingEntityException("PorcentajeCarga " + porcentajeCarga + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PorcentajeCarga porcentajeCarga) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PorcentajeCarga persistentPorcentajeCarga = em.find(PorcentajeCarga.class, porcentajeCarga.getIdPorcentaje());
            TipoEntrenamiento idTipoEntrenamientoOld = persistentPorcentajeCarga.getIdTipoEntrenamiento();
            TipoEntrenamiento idTipoEntrenamientoNew = porcentajeCarga.getIdTipoEntrenamiento();
            if (idTipoEntrenamientoNew != null) {
                idTipoEntrenamientoNew = em.getReference(idTipoEntrenamientoNew.getClass(), idTipoEntrenamientoNew.getIdTipoEntrenamiento());
                porcentajeCarga.setIdTipoEntrenamiento(idTipoEntrenamientoNew);
            }
            porcentajeCarga = em.merge(porcentajeCarga);
            if (idTipoEntrenamientoOld != null && !idTipoEntrenamientoOld.equals(idTipoEntrenamientoNew)) {
                idTipoEntrenamientoOld.getPorcentajeCargaList().remove(porcentajeCarga);
                idTipoEntrenamientoOld = em.merge(idTipoEntrenamientoOld);
            }
            if (idTipoEntrenamientoNew != null && !idTipoEntrenamientoNew.equals(idTipoEntrenamientoOld)) {
                idTipoEntrenamientoNew.getPorcentajeCargaList().add(porcentajeCarga);
                idTipoEntrenamientoNew = em.merge(idTipoEntrenamientoNew);
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
                Integer id = porcentajeCarga.getIdPorcentaje();
                if (findPorcentajeCarga(id) == null) {
                    throw new NonexistentEntityException("The porcentajeCarga with id " + id + " no longer exists.");
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
            PorcentajeCarga porcentajeCarga;
            try {
                porcentajeCarga = em.getReference(PorcentajeCarga.class, id);
                porcentajeCarga.getIdPorcentaje();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The porcentajeCarga with id " + id + " no longer exists.", enfe);
            }
            TipoEntrenamiento idTipoEntrenamiento = porcentajeCarga.getIdTipoEntrenamiento();
            if (idTipoEntrenamiento != null) {
                idTipoEntrenamiento.getPorcentajeCargaList().remove(porcentajeCarga);
                idTipoEntrenamiento = em.merge(idTipoEntrenamiento);
            }
            em.remove(porcentajeCarga);
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

    public List<PorcentajeCarga> findPorcentajeCargaEntities() {
        return findPorcentajeCargaEntities(true, -1, -1);
    }

    public List<PorcentajeCarga> findPorcentajeCargaEntities(int maxResults, int firstResult) {
        return findPorcentajeCargaEntities(false, maxResults, firstResult);
    }

    private List<PorcentajeCarga> findPorcentajeCargaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PorcentajeCarga.class));
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

    public PorcentajeCarga findPorcentajeCarga(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PorcentajeCarga.class, id);
        } finally {
            em.close();
        }
    }

    public int getPorcentajeCargaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PorcentajeCarga> rt = cq.from(PorcentajeCarga.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
