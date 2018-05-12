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
import com.rid.modelo.entities.Entrenamiento;
import com.rid.modelo.entities.Equivalente;
import com.rid.modelo.entities.Rms;
import com.rid.modelo.entities.TipoEntrenamiento;
import com.rid.modelo.entities.Trabajo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class RmsJpaController implements Serializable {

    public RmsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rms rms) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Entrenamiento idEntrenamiento = rms.getIdEntrenamiento();
            if (idEntrenamiento != null) {
                idEntrenamiento = em.getReference(idEntrenamiento.getClass(), idEntrenamiento.getIdEntrenamiento());
                rms.setIdEntrenamiento(idEntrenamiento);
            }
            Equivalente idEquivalente = rms.getIdEquivalente();
            if (idEquivalente != null) {
                idEquivalente = em.getReference(idEquivalente.getClass(), idEquivalente.getIdEquivalente());
                rms.setIdEquivalente(idEquivalente);
            }
            TipoEntrenamiento idTipoEntrenamiento = rms.getIdTipoEntrenamiento();
            if (idTipoEntrenamiento != null) {
                idTipoEntrenamiento = em.getReference(idTipoEntrenamiento.getClass(), idTipoEntrenamiento.getIdTipoEntrenamiento());
                rms.setIdTipoEntrenamiento(idTipoEntrenamiento);
            }
            Trabajo idTrabajo = rms.getIdTrabajo();
            if (idTrabajo != null) {
                idTrabajo = em.getReference(idTrabajo.getClass(), idTrabajo.getIdTrabajo());
                rms.setIdTrabajo(idTrabajo);
            }
            em.persist(rms);
            if (idEntrenamiento != null) {
                idEntrenamiento.getRmsList().add(rms);
                idEntrenamiento = em.merge(idEntrenamiento);
            }
            if (idEquivalente != null) {
                idEquivalente.getRmsList().add(rms);
                idEquivalente = em.merge(idEquivalente);
            }
            if (idTipoEntrenamiento != null) {
                idTipoEntrenamiento.getRmsList().add(rms);
                idTipoEntrenamiento = em.merge(idTipoEntrenamiento);
            }
            if (idTrabajo != null) {
                idTrabajo.getRmsList().add(rms);
                idTrabajo = em.merge(idTrabajo);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRms(rms.getIdRms()) != null) {
                throw new PreexistingEntityException("Rms " + rms + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rms rms) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Rms persistentRms = em.find(Rms.class, rms.getIdRms());
            Entrenamiento idEntrenamientoOld = persistentRms.getIdEntrenamiento();
            Entrenamiento idEntrenamientoNew = rms.getIdEntrenamiento();
            Equivalente idEquivalenteOld = persistentRms.getIdEquivalente();
            Equivalente idEquivalenteNew = rms.getIdEquivalente();
            TipoEntrenamiento idTipoEntrenamientoOld = persistentRms.getIdTipoEntrenamiento();
            TipoEntrenamiento idTipoEntrenamientoNew = rms.getIdTipoEntrenamiento();
            Trabajo idTrabajoOld = persistentRms.getIdTrabajo();
            Trabajo idTrabajoNew = rms.getIdTrabajo();
            if (idEntrenamientoNew != null) {
                idEntrenamientoNew = em.getReference(idEntrenamientoNew.getClass(), idEntrenamientoNew.getIdEntrenamiento());
                rms.setIdEntrenamiento(idEntrenamientoNew);
            }
            if (idEquivalenteNew != null) {
                idEquivalenteNew = em.getReference(idEquivalenteNew.getClass(), idEquivalenteNew.getIdEquivalente());
                rms.setIdEquivalente(idEquivalenteNew);
            }
            if (idTipoEntrenamientoNew != null) {
                idTipoEntrenamientoNew = em.getReference(idTipoEntrenamientoNew.getClass(), idTipoEntrenamientoNew.getIdTipoEntrenamiento());
                rms.setIdTipoEntrenamiento(idTipoEntrenamientoNew);
            }
            if (idTrabajoNew != null) {
                idTrabajoNew = em.getReference(idTrabajoNew.getClass(), idTrabajoNew.getIdTrabajo());
                rms.setIdTrabajo(idTrabajoNew);
            }
            rms = em.merge(rms);
            if (idEntrenamientoOld != null && !idEntrenamientoOld.equals(idEntrenamientoNew)) {
                idEntrenamientoOld.getRmsList().remove(rms);
                idEntrenamientoOld = em.merge(idEntrenamientoOld);
            }
            if (idEntrenamientoNew != null && !idEntrenamientoNew.equals(idEntrenamientoOld)) {
                idEntrenamientoNew.getRmsList().add(rms);
                idEntrenamientoNew = em.merge(idEntrenamientoNew);
            }
            if (idEquivalenteOld != null && !idEquivalenteOld.equals(idEquivalenteNew)) {
                idEquivalenteOld.getRmsList().remove(rms);
                idEquivalenteOld = em.merge(idEquivalenteOld);
            }
            if (idEquivalenteNew != null && !idEquivalenteNew.equals(idEquivalenteOld)) {
                idEquivalenteNew.getRmsList().add(rms);
                idEquivalenteNew = em.merge(idEquivalenteNew);
            }
            if (idTipoEntrenamientoOld != null && !idTipoEntrenamientoOld.equals(idTipoEntrenamientoNew)) {
                idTipoEntrenamientoOld.getRmsList().remove(rms);
                idTipoEntrenamientoOld = em.merge(idTipoEntrenamientoOld);
            }
            if (idTipoEntrenamientoNew != null && !idTipoEntrenamientoNew.equals(idTipoEntrenamientoOld)) {
                idTipoEntrenamientoNew.getRmsList().add(rms);
                idTipoEntrenamientoNew = em.merge(idTipoEntrenamientoNew);
            }
            if (idTrabajoOld != null && !idTrabajoOld.equals(idTrabajoNew)) {
                idTrabajoOld.getRmsList().remove(rms);
                idTrabajoOld = em.merge(idTrabajoOld);
            }
            if (idTrabajoNew != null && !idTrabajoNew.equals(idTrabajoOld)) {
                idTrabajoNew.getRmsList().add(rms);
                idTrabajoNew = em.merge(idTrabajoNew);
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
                Integer id = rms.getIdRms();
                if (findRms(id) == null) {
                    throw new NonexistentEntityException("The rms with id " + id + " no longer exists.");
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
            Rms rms;
            try {
                rms = em.getReference(Rms.class, id);
                rms.getIdRms();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rms with id " + id + " no longer exists.", enfe);
            }
            Entrenamiento idEntrenamiento = rms.getIdEntrenamiento();
            if (idEntrenamiento != null) {
                idEntrenamiento.getRmsList().remove(rms);
                idEntrenamiento = em.merge(idEntrenamiento);
            }
            Equivalente idEquivalente = rms.getIdEquivalente();
            if (idEquivalente != null) {
                idEquivalente.getRmsList().remove(rms);
                idEquivalente = em.merge(idEquivalente);
            }
            TipoEntrenamiento idTipoEntrenamiento = rms.getIdTipoEntrenamiento();
            if (idTipoEntrenamiento != null) {
                idTipoEntrenamiento.getRmsList().remove(rms);
                idTipoEntrenamiento = em.merge(idTipoEntrenamiento);
            }
            Trabajo idTrabajo = rms.getIdTrabajo();
            if (idTrabajo != null) {
                idTrabajo.getRmsList().remove(rms);
                idTrabajo = em.merge(idTrabajo);
            }
            em.remove(rms);
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

    public List<Rms> findRmsEntities() {
        return findRmsEntities(true, -1, -1);
    }

    public List<Rms> findRmsEntities(int maxResults, int firstResult) {
        return findRmsEntities(false, maxResults, firstResult);
    }

    private List<Rms> findRmsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rms.class));
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

    public Rms findRms(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rms.class, id);
        } finally {
            em.close();
        }
    }

    public int getRmsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rms> rt = cq.from(Rms.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
