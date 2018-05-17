/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.jpa;

import com.rid.modelo.controllers.jpa.exceptions.NonexistentEntityException;
import com.rid.modelo.controllers.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rid.modelo.entities.Participacion;
import com.rid.modelo.entities.Resultado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class ResultadoJpaController implements Serializable {

    public ResultadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Resultado resultado) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Participacion idParticipacion = resultado.getIdParticipacion();
            if (idParticipacion != null) {
                idParticipacion = em.getReference(idParticipacion.getClass(), idParticipacion.getIdParticipacion());
                resultado.setIdParticipacion(idParticipacion);
            }
            em.persist(resultado);
            if (idParticipacion != null) {
                idParticipacion.getResultadoList().add(resultado);
                idParticipacion = em.merge(idParticipacion);
            }
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

    public void edit(Resultado resultado) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Resultado persistentResultado = em.find(Resultado.class, resultado.getIdResultado());
            Participacion idParticipacionOld = persistentResultado.getIdParticipacion();
            Participacion idParticipacionNew = resultado.getIdParticipacion();
            if (idParticipacionNew != null) {
                idParticipacionNew = em.getReference(idParticipacionNew.getClass(), idParticipacionNew.getIdParticipacion());
                resultado.setIdParticipacion(idParticipacionNew);
            }
            resultado = em.merge(resultado);
            if (idParticipacionOld != null && !idParticipacionOld.equals(idParticipacionNew)) {
                idParticipacionOld.getResultadoList().remove(resultado);
                idParticipacionOld = em.merge(idParticipacionOld);
            }
            if (idParticipacionNew != null && !idParticipacionNew.equals(idParticipacionOld)) {
                idParticipacionNew.getResultadoList().add(resultado);
                idParticipacionNew = em.merge(idParticipacionNew);
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
                Integer id = resultado.getIdResultado();
                if (findResultado(id) == null) {
                    throw new NonexistentEntityException("The resultado with id " + id + " no longer exists.");
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
            Resultado resultado;
            try {
                resultado = em.getReference(Resultado.class, id);
                resultado.getIdResultado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resultado with id " + id + " no longer exists.", enfe);
            }
            Participacion idParticipacion = resultado.getIdParticipacion();
            if (idParticipacion != null) {
                idParticipacion.getResultadoList().remove(resultado);
                idParticipacion = em.merge(idParticipacion);
            }
            em.remove(resultado);
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

    public List<Resultado> findResultadoEntities() {
        return findResultadoEntities(true, -1, -1);
    }

    public List<Resultado> findResultadoEntities(int maxResults, int firstResult) {
        return findResultadoEntities(false, maxResults, firstResult);
    }

    private List<Resultado> findResultadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Resultado.class));
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

    public Resultado findResultado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Resultado.class, id);
        } finally {
            em.close();
        }
    }

    public int getResultadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Resultado> rt = cq.from(Resultado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
