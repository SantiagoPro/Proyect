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
import com.rid.modelo.entities.CategoriaDeportista;
import com.rid.modelo.entities.Participacion;
import com.rid.modelo.entities.Torneo;
import com.rid.modelo.entities.Resultado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class ParticipacionJpaController implements Serializable {

    public ParticipacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Participacion participacion) throws RollbackFailureException, Exception {
        if (participacion.getResultadoList() == null) {
            participacion.setResultadoList(new ArrayList<Resultado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoriaDeportista idCategoriaDeportista = participacion.getIdCategoriaDeportista();
            if (idCategoriaDeportista != null) {
                idCategoriaDeportista = em.getReference(idCategoriaDeportista.getClass(), idCategoriaDeportista.getIdCategoriaDeportista());
                participacion.setIdCategoriaDeportista(idCategoriaDeportista);
            }
            Torneo idTorneo = participacion.getIdTorneo();
            if (idTorneo != null) {
                idTorneo = em.getReference(idTorneo.getClass(), idTorneo.getIdTorneo());
                participacion.setIdTorneo(idTorneo);
            }
            List<Resultado> attachedResultadoList = new ArrayList<Resultado>();
            for (Resultado resultadoListResultadoToAttach : participacion.getResultadoList()) {
                resultadoListResultadoToAttach = em.getReference(resultadoListResultadoToAttach.getClass(), resultadoListResultadoToAttach.getIdResultado());
                attachedResultadoList.add(resultadoListResultadoToAttach);
            }
            participacion.setResultadoList(attachedResultadoList);
            em.persist(participacion);
            if (idCategoriaDeportista != null) {
                idCategoriaDeportista.getParticipacionList().add(participacion);
                idCategoriaDeportista = em.merge(idCategoriaDeportista);
            }
            if (idTorneo != null) {
                idTorneo.getParticipacionList().add(participacion);
                idTorneo = em.merge(idTorneo);
            }
            for (Resultado resultadoListResultado : participacion.getResultadoList()) {
                Participacion oldIdParticipacionOfResultadoListResultado = resultadoListResultado.getIdParticipacion();
                resultadoListResultado.setIdParticipacion(participacion);
                resultadoListResultado = em.merge(resultadoListResultado);
                if (oldIdParticipacionOfResultadoListResultado != null) {
                    oldIdParticipacionOfResultadoListResultado.getResultadoList().remove(resultadoListResultado);
                    oldIdParticipacionOfResultadoListResultado = em.merge(oldIdParticipacionOfResultadoListResultado);
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

    public void edit(Participacion participacion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Participacion persistentParticipacion = em.find(Participacion.class, participacion.getIdParticipacion());
            CategoriaDeportista idCategoriaDeportistaOld = persistentParticipacion.getIdCategoriaDeportista();
            CategoriaDeportista idCategoriaDeportistaNew = participacion.getIdCategoriaDeportista();
            Torneo idTorneoOld = persistentParticipacion.getIdTorneo();
            Torneo idTorneoNew = participacion.getIdTorneo();
            List<Resultado> resultadoListOld = persistentParticipacion.getResultadoList();
            List<Resultado> resultadoListNew = participacion.getResultadoList();
            List<String> illegalOrphanMessages = null;
            for (Resultado resultadoListOldResultado : resultadoListOld) {
                if (!resultadoListNew.contains(resultadoListOldResultado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Resultado " + resultadoListOldResultado + " since its idParticipacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCategoriaDeportistaNew != null) {
                idCategoriaDeportistaNew = em.getReference(idCategoriaDeportistaNew.getClass(), idCategoriaDeportistaNew.getIdCategoriaDeportista());
                participacion.setIdCategoriaDeportista(idCategoriaDeportistaNew);
            }
            if (idTorneoNew != null) {
                idTorneoNew = em.getReference(idTorneoNew.getClass(), idTorneoNew.getIdTorneo());
                participacion.setIdTorneo(idTorneoNew);
            }
            List<Resultado> attachedResultadoListNew = new ArrayList<Resultado>();
            for (Resultado resultadoListNewResultadoToAttach : resultadoListNew) {
                resultadoListNewResultadoToAttach = em.getReference(resultadoListNewResultadoToAttach.getClass(), resultadoListNewResultadoToAttach.getIdResultado());
                attachedResultadoListNew.add(resultadoListNewResultadoToAttach);
            }
            resultadoListNew = attachedResultadoListNew;
            participacion.setResultadoList(resultadoListNew);
            participacion = em.merge(participacion);
            if (idCategoriaDeportistaOld != null && !idCategoriaDeportistaOld.equals(idCategoriaDeportistaNew)) {
                idCategoriaDeportistaOld.getParticipacionList().remove(participacion);
                idCategoriaDeportistaOld = em.merge(idCategoriaDeportistaOld);
            }
            if (idCategoriaDeportistaNew != null && !idCategoriaDeportistaNew.equals(idCategoriaDeportistaOld)) {
                idCategoriaDeportistaNew.getParticipacionList().add(participacion);
                idCategoriaDeportistaNew = em.merge(idCategoriaDeportistaNew);
            }
            if (idTorneoOld != null && !idTorneoOld.equals(idTorneoNew)) {
                idTorneoOld.getParticipacionList().remove(participacion);
                idTorneoOld = em.merge(idTorneoOld);
            }
            if (idTorneoNew != null && !idTorneoNew.equals(idTorneoOld)) {
                idTorneoNew.getParticipacionList().add(participacion);
                idTorneoNew = em.merge(idTorneoNew);
            }
            for (Resultado resultadoListNewResultado : resultadoListNew) {
                if (!resultadoListOld.contains(resultadoListNewResultado)) {
                    Participacion oldIdParticipacionOfResultadoListNewResultado = resultadoListNewResultado.getIdParticipacion();
                    resultadoListNewResultado.setIdParticipacion(participacion);
                    resultadoListNewResultado = em.merge(resultadoListNewResultado);
                    if (oldIdParticipacionOfResultadoListNewResultado != null && !oldIdParticipacionOfResultadoListNewResultado.equals(participacion)) {
                        oldIdParticipacionOfResultadoListNewResultado.getResultadoList().remove(resultadoListNewResultado);
                        oldIdParticipacionOfResultadoListNewResultado = em.merge(oldIdParticipacionOfResultadoListNewResultado);
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
                Integer id = participacion.getIdParticipacion();
                if (findParticipacion(id) == null) {
                    throw new NonexistentEntityException("The participacion with id " + id + " no longer exists.");
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
            Participacion participacion;
            try {
                participacion = em.getReference(Participacion.class, id);
                participacion.getIdParticipacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The participacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Resultado> resultadoListOrphanCheck = participacion.getResultadoList();
            for (Resultado resultadoListOrphanCheckResultado : resultadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Participacion (" + participacion + ") cannot be destroyed since the Resultado " + resultadoListOrphanCheckResultado + " in its resultadoList field has a non-nullable idParticipacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CategoriaDeportista idCategoriaDeportista = participacion.getIdCategoriaDeportista();
            if (idCategoriaDeportista != null) {
                idCategoriaDeportista.getParticipacionList().remove(participacion);
                idCategoriaDeportista = em.merge(idCategoriaDeportista);
            }
            Torneo idTorneo = participacion.getIdTorneo();
            if (idTorneo != null) {
                idTorneo.getParticipacionList().remove(participacion);
                idTorneo = em.merge(idTorneo);
            }
            em.remove(participacion);
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

    public List<Participacion> findParticipacionEntities() {
        return findParticipacionEntities(true, -1, -1);
    }

    public List<Participacion> findParticipacionEntities(int maxResults, int firstResult) {
        return findParticipacionEntities(false, maxResults, firstResult);
    }

    private List<Participacion> findParticipacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Participacion.class));
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

    public Participacion findParticipacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Participacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getParticipacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Participacion> rt = cq.from(Participacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
