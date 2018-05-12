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
import com.rid.modelo.entities.Categoria;
import com.rid.modelo.entities.CategoriaDeportista;
import com.rid.modelo.entities.Deportista;
import com.rid.modelo.entities.Entrenamiento;
import java.util.ArrayList;
import java.util.List;
import com.rid.modelo.entities.Participacion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class CategoriaDeportistaJpaController implements Serializable {

    public CategoriaDeportistaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaDeportista categoriaDeportista) throws RollbackFailureException, Exception {
        if (categoriaDeportista.getEntrenamientoList() == null) {
            categoriaDeportista.setEntrenamientoList(new ArrayList<Entrenamiento>());
        }
        if (categoriaDeportista.getParticipacionList() == null) {
            categoriaDeportista.setParticipacionList(new ArrayList<Participacion>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Categoria idCategoria = categoriaDeportista.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getIdCategoria());
                categoriaDeportista.setIdCategoria(idCategoria);
            }
            Deportista idDeportista = categoriaDeportista.getIdDeportista();
            if (idDeportista != null) {
                idDeportista = em.getReference(idDeportista.getClass(), idDeportista.getIdDeportista());
                categoriaDeportista.setIdDeportista(idDeportista);
            }
            List<Entrenamiento> attachedEntrenamientoList = new ArrayList<Entrenamiento>();
            for (Entrenamiento entrenamientoListEntrenamientoToAttach : categoriaDeportista.getEntrenamientoList()) {
                entrenamientoListEntrenamientoToAttach = em.getReference(entrenamientoListEntrenamientoToAttach.getClass(), entrenamientoListEntrenamientoToAttach.getIdEntrenamiento());
                attachedEntrenamientoList.add(entrenamientoListEntrenamientoToAttach);
            }
            categoriaDeportista.setEntrenamientoList(attachedEntrenamientoList);
            List<Participacion> attachedParticipacionList = new ArrayList<Participacion>();
            for (Participacion participacionListParticipacionToAttach : categoriaDeportista.getParticipacionList()) {
                participacionListParticipacionToAttach = em.getReference(participacionListParticipacionToAttach.getClass(), participacionListParticipacionToAttach.getIdParticipacion());
                attachedParticipacionList.add(participacionListParticipacionToAttach);
            }
            categoriaDeportista.setParticipacionList(attachedParticipacionList);
            em.persist(categoriaDeportista);
            if (idCategoria != null) {
                idCategoria.getCategoriaDeportistaList().add(categoriaDeportista);
                idCategoria = em.merge(idCategoria);
            }
            if (idDeportista != null) {
                idDeportista.getCategoriaDeportistaList().add(categoriaDeportista);
                idDeportista = em.merge(idDeportista);
            }
            for (Entrenamiento entrenamientoListEntrenamiento : categoriaDeportista.getEntrenamientoList()) {
                CategoriaDeportista oldIdCategoriaDeportistaOfEntrenamientoListEntrenamiento = entrenamientoListEntrenamiento.getIdCategoriaDeportista();
                entrenamientoListEntrenamiento.setIdCategoriaDeportista(categoriaDeportista);
                entrenamientoListEntrenamiento = em.merge(entrenamientoListEntrenamiento);
                if (oldIdCategoriaDeportistaOfEntrenamientoListEntrenamiento != null) {
                    oldIdCategoriaDeportistaOfEntrenamientoListEntrenamiento.getEntrenamientoList().remove(entrenamientoListEntrenamiento);
                    oldIdCategoriaDeportistaOfEntrenamientoListEntrenamiento = em.merge(oldIdCategoriaDeportistaOfEntrenamientoListEntrenamiento);
                }
            }
            for (Participacion participacionListParticipacion : categoriaDeportista.getParticipacionList()) {
                CategoriaDeportista oldIdCategoriaDeportistaOfParticipacionListParticipacion = participacionListParticipacion.getIdCategoriaDeportista();
                participacionListParticipacion.setIdCategoriaDeportista(categoriaDeportista);
                participacionListParticipacion = em.merge(participacionListParticipacion);
                if (oldIdCategoriaDeportistaOfParticipacionListParticipacion != null) {
                    oldIdCategoriaDeportistaOfParticipacionListParticipacion.getParticipacionList().remove(participacionListParticipacion);
                    oldIdCategoriaDeportistaOfParticipacionListParticipacion = em.merge(oldIdCategoriaDeportistaOfParticipacionListParticipacion);
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

    public void edit(CategoriaDeportista categoriaDeportista) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoriaDeportista persistentCategoriaDeportista = em.find(CategoriaDeportista.class, categoriaDeportista.getIdCategoriaDeportista());
            Categoria idCategoriaOld = persistentCategoriaDeportista.getIdCategoria();
            Categoria idCategoriaNew = categoriaDeportista.getIdCategoria();
            Deportista idDeportistaOld = persistentCategoriaDeportista.getIdDeportista();
            Deportista idDeportistaNew = categoriaDeportista.getIdDeportista();
            List<Entrenamiento> entrenamientoListOld = persistentCategoriaDeportista.getEntrenamientoList();
            List<Entrenamiento> entrenamientoListNew = categoriaDeportista.getEntrenamientoList();
            List<Participacion> participacionListOld = persistentCategoriaDeportista.getParticipacionList();
            List<Participacion> participacionListNew = categoriaDeportista.getParticipacionList();
            List<String> illegalOrphanMessages = null;
            for (Participacion participacionListOldParticipacion : participacionListOld) {
                if (!participacionListNew.contains(participacionListOldParticipacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Participacion " + participacionListOldParticipacion + " since its idCategoriaDeportista field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getIdCategoria());
                categoriaDeportista.setIdCategoria(idCategoriaNew);
            }
            if (idDeportistaNew != null) {
                idDeportistaNew = em.getReference(idDeportistaNew.getClass(), idDeportistaNew.getIdDeportista());
                categoriaDeportista.setIdDeportista(idDeportistaNew);
            }
            List<Entrenamiento> attachedEntrenamientoListNew = new ArrayList<Entrenamiento>();
            for (Entrenamiento entrenamientoListNewEntrenamientoToAttach : entrenamientoListNew) {
                entrenamientoListNewEntrenamientoToAttach = em.getReference(entrenamientoListNewEntrenamientoToAttach.getClass(), entrenamientoListNewEntrenamientoToAttach.getIdEntrenamiento());
                attachedEntrenamientoListNew.add(entrenamientoListNewEntrenamientoToAttach);
            }
            entrenamientoListNew = attachedEntrenamientoListNew;
            categoriaDeportista.setEntrenamientoList(entrenamientoListNew);
            List<Participacion> attachedParticipacionListNew = new ArrayList<Participacion>();
            for (Participacion participacionListNewParticipacionToAttach : participacionListNew) {
                participacionListNewParticipacionToAttach = em.getReference(participacionListNewParticipacionToAttach.getClass(), participacionListNewParticipacionToAttach.getIdParticipacion());
                attachedParticipacionListNew.add(participacionListNewParticipacionToAttach);
            }
            participacionListNew = attachedParticipacionListNew;
            categoriaDeportista.setParticipacionList(participacionListNew);
            categoriaDeportista = em.merge(categoriaDeportista);
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getCategoriaDeportistaList().remove(categoriaDeportista);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getCategoriaDeportistaList().add(categoriaDeportista);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            if (idDeportistaOld != null && !idDeportistaOld.equals(idDeportistaNew)) {
                idDeportistaOld.getCategoriaDeportistaList().remove(categoriaDeportista);
                idDeportistaOld = em.merge(idDeportistaOld);
            }
            if (idDeportistaNew != null && !idDeportistaNew.equals(idDeportistaOld)) {
                idDeportistaNew.getCategoriaDeportistaList().add(categoriaDeportista);
                idDeportistaNew = em.merge(idDeportistaNew);
            }
            for (Entrenamiento entrenamientoListOldEntrenamiento : entrenamientoListOld) {
                if (!entrenamientoListNew.contains(entrenamientoListOldEntrenamiento)) {
                    entrenamientoListOldEntrenamiento.setIdCategoriaDeportista(null);
                    entrenamientoListOldEntrenamiento = em.merge(entrenamientoListOldEntrenamiento);
                }
            }
            for (Entrenamiento entrenamientoListNewEntrenamiento : entrenamientoListNew) {
                if (!entrenamientoListOld.contains(entrenamientoListNewEntrenamiento)) {
                    CategoriaDeportista oldIdCategoriaDeportistaOfEntrenamientoListNewEntrenamiento = entrenamientoListNewEntrenamiento.getIdCategoriaDeportista();
                    entrenamientoListNewEntrenamiento.setIdCategoriaDeportista(categoriaDeportista);
                    entrenamientoListNewEntrenamiento = em.merge(entrenamientoListNewEntrenamiento);
                    if (oldIdCategoriaDeportistaOfEntrenamientoListNewEntrenamiento != null && !oldIdCategoriaDeportistaOfEntrenamientoListNewEntrenamiento.equals(categoriaDeportista)) {
                        oldIdCategoriaDeportistaOfEntrenamientoListNewEntrenamiento.getEntrenamientoList().remove(entrenamientoListNewEntrenamiento);
                        oldIdCategoriaDeportistaOfEntrenamientoListNewEntrenamiento = em.merge(oldIdCategoriaDeportistaOfEntrenamientoListNewEntrenamiento);
                    }
                }
            }
            for (Participacion participacionListNewParticipacion : participacionListNew) {
                if (!participacionListOld.contains(participacionListNewParticipacion)) {
                    CategoriaDeportista oldIdCategoriaDeportistaOfParticipacionListNewParticipacion = participacionListNewParticipacion.getIdCategoriaDeportista();
                    participacionListNewParticipacion.setIdCategoriaDeportista(categoriaDeportista);
                    participacionListNewParticipacion = em.merge(participacionListNewParticipacion);
                    if (oldIdCategoriaDeportistaOfParticipacionListNewParticipacion != null && !oldIdCategoriaDeportistaOfParticipacionListNewParticipacion.equals(categoriaDeportista)) {
                        oldIdCategoriaDeportistaOfParticipacionListNewParticipacion.getParticipacionList().remove(participacionListNewParticipacion);
                        oldIdCategoriaDeportistaOfParticipacionListNewParticipacion = em.merge(oldIdCategoriaDeportistaOfParticipacionListNewParticipacion);
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
                Integer id = categoriaDeportista.getIdCategoriaDeportista();
                if (findCategoriaDeportista(id) == null) {
                    throw new NonexistentEntityException("The categoriaDeportista with id " + id + " no longer exists.");
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
            CategoriaDeportista categoriaDeportista;
            try {
                categoriaDeportista = em.getReference(CategoriaDeportista.class, id);
                categoriaDeportista.getIdCategoriaDeportista();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaDeportista with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Participacion> participacionListOrphanCheck = categoriaDeportista.getParticipacionList();
            for (Participacion participacionListOrphanCheckParticipacion : participacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CategoriaDeportista (" + categoriaDeportista + ") cannot be destroyed since the Participacion " + participacionListOrphanCheckParticipacion + " in its participacionList field has a non-nullable idCategoriaDeportista field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categoria idCategoria = categoriaDeportista.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getCategoriaDeportistaList().remove(categoriaDeportista);
                idCategoria = em.merge(idCategoria);
            }
            Deportista idDeportista = categoriaDeportista.getIdDeportista();
            if (idDeportista != null) {
                idDeportista.getCategoriaDeportistaList().remove(categoriaDeportista);
                idDeportista = em.merge(idDeportista);
            }
            List<Entrenamiento> entrenamientoList = categoriaDeportista.getEntrenamientoList();
            for (Entrenamiento entrenamientoListEntrenamiento : entrenamientoList) {
                entrenamientoListEntrenamiento.setIdCategoriaDeportista(null);
                entrenamientoListEntrenamiento = em.merge(entrenamientoListEntrenamiento);
            }
            em.remove(categoriaDeportista);
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

    public List<CategoriaDeportista> findCategoriaDeportistaEntities() {
        return findCategoriaDeportistaEntities(true, -1, -1);
    }

    public List<CategoriaDeportista> findCategoriaDeportistaEntities(int maxResults, int firstResult) {
        return findCategoriaDeportistaEntities(false, maxResults, firstResult);
    }

    private List<CategoriaDeportista> findCategoriaDeportistaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaDeportista.class));
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

    public CategoriaDeportista findCategoriaDeportista(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaDeportista.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaDeportistaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaDeportista> rt = cq.from(CategoriaDeportista.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
