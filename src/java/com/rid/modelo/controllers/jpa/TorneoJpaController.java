/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.jpa;

import com.rid.modelo.controllers.jpa.exceptions.IllegalOrphanException;
import com.rid.modelo.controllers.jpa.exceptions.NonexistentEntityException;
import com.rid.modelo.controllers.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rid.modelo.entities.Participacion;
import com.rid.modelo.entities.Torneo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class TorneoJpaController implements Serializable {

    public TorneoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Torneo torneo) throws RollbackFailureException, Exception {
        if (torneo.getParticipacionList() == null) {
            torneo.setParticipacionList(new ArrayList<Participacion>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Participacion> attachedParticipacionList = new ArrayList<Participacion>();
            for (Participacion participacionListParticipacionToAttach : torneo.getParticipacionList()) {
                participacionListParticipacionToAttach = em.getReference(participacionListParticipacionToAttach.getClass(), participacionListParticipacionToAttach.getIdParticipacion());
                attachedParticipacionList.add(participacionListParticipacionToAttach);
            }
            torneo.setParticipacionList(attachedParticipacionList);
            em.persist(torneo);
            for (Participacion participacionListParticipacion : torneo.getParticipacionList()) {
                Torneo oldIdTorneoOfParticipacionListParticipacion = participacionListParticipacion.getIdTorneo();
                participacionListParticipacion.setIdTorneo(torneo);
                participacionListParticipacion = em.merge(participacionListParticipacion);
                if (oldIdTorneoOfParticipacionListParticipacion != null) {
                    oldIdTorneoOfParticipacionListParticipacion.getParticipacionList().remove(participacionListParticipacion);
                    oldIdTorneoOfParticipacionListParticipacion = em.merge(oldIdTorneoOfParticipacionListParticipacion);
                }
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

    public void edit(Torneo torneo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Torneo persistentTorneo = em.find(Torneo.class, torneo.getIdTorneo());
            List<Participacion> participacionListOld = persistentTorneo.getParticipacionList();
            List<Participacion> participacionListNew = torneo.getParticipacionList();
            List<String> illegalOrphanMessages = null;
            for (Participacion participacionListOldParticipacion : participacionListOld) {
                if (!participacionListNew.contains(participacionListOldParticipacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Participacion " + participacionListOldParticipacion + " since its idTorneo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Participacion> attachedParticipacionListNew = new ArrayList<Participacion>();
            for (Participacion participacionListNewParticipacionToAttach : participacionListNew) {
                participacionListNewParticipacionToAttach = em.getReference(participacionListNewParticipacionToAttach.getClass(), participacionListNewParticipacionToAttach.getIdParticipacion());
                attachedParticipacionListNew.add(participacionListNewParticipacionToAttach);
            }
            participacionListNew = attachedParticipacionListNew;
            torneo.setParticipacionList(participacionListNew);
            torneo = em.merge(torneo);
            for (Participacion participacionListNewParticipacion : participacionListNew) {
                if (!participacionListOld.contains(participacionListNewParticipacion)) {
                    Torneo oldIdTorneoOfParticipacionListNewParticipacion = participacionListNewParticipacion.getIdTorneo();
                    participacionListNewParticipacion.setIdTorneo(torneo);
                    participacionListNewParticipacion = em.merge(participacionListNewParticipacion);
                    if (oldIdTorneoOfParticipacionListNewParticipacion != null && !oldIdTorneoOfParticipacionListNewParticipacion.equals(torneo)) {
                        oldIdTorneoOfParticipacionListNewParticipacion.getParticipacionList().remove(participacionListNewParticipacion);
                        oldIdTorneoOfParticipacionListNewParticipacion = em.merge(oldIdTorneoOfParticipacionListNewParticipacion);
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
                Integer id = torneo.getIdTorneo();
                if (findTorneo(id) == null) {
                    throw new NonexistentEntityException("The torneo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Torneo torneo;
            try {
                torneo = em.getReference(Torneo.class, id);
                torneo.getIdTorneo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The torneo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Participacion> participacionListOrphanCheck = torneo.getParticipacionList();
            for (Participacion participacionListOrphanCheckParticipacion : participacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Torneo (" + torneo + ") cannot be destroyed since the Participacion " + participacionListOrphanCheckParticipacion + " in its participacionList field has a non-nullable idTorneo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(torneo);
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

    public List<Torneo> findTorneoEntities() {
        return findTorneoEntities(true, -1, -1);
    }

    public List<Torneo> findTorneoEntities(int maxResults, int firstResult) {
        return findTorneoEntities(false, maxResults, firstResult);
    }

    private List<Torneo> findTorneoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Torneo.class));
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

    public Torneo findTorneo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Torneo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTorneoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Torneo> rt = cq.from(Torneo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
