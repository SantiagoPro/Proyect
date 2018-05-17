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
import com.rid.modelo.entities.CategoriaDeportista;
import com.rid.modelo.entities.Entrenador;
import com.rid.modelo.entities.Entrenamiento;
import java.util.ArrayList;
import java.util.List;
import com.rid.modelo.entities.Rms;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class EntrenamientoJpaController implements Serializable {

    public EntrenamientoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entrenamiento entrenamiento) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (entrenamiento.getEntrenamientoList() == null) {
            entrenamiento.setEntrenamientoList(new ArrayList<Entrenamiento>());
        }
        if (entrenamiento.getRmsList() == null) {
            entrenamiento.setRmsList(new ArrayList<Rms>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoriaDeportista idCategoriaDeportista = entrenamiento.getIdCategoriaDeportista();
            if (idCategoriaDeportista != null) {
                idCategoriaDeportista = em.getReference(idCategoriaDeportista.getClass(), idCategoriaDeportista.getIdCategoriaDeportista());
                entrenamiento.setIdCategoriaDeportista(idCategoriaDeportista);
            }
            Entrenador idEntrenador = entrenamiento.getIdEntrenador();
            if (idEntrenador != null) {
                idEntrenador = em.getReference(idEntrenador.getClass(), idEntrenador.getIdEntrenador());
                entrenamiento.setIdEntrenador(idEntrenador);
            }
            Entrenamiento entrenamientoPadre = entrenamiento.getEntrenamientoPadre();
            if (entrenamientoPadre != null) {
                entrenamientoPadre = em.getReference(entrenamientoPadre.getClass(), entrenamientoPadre.getIdEntrenamiento());
                entrenamiento.setEntrenamientoPadre(entrenamientoPadre);
            }
            List<Entrenamiento> attachedEntrenamientoList = new ArrayList<Entrenamiento>();
            for (Entrenamiento entrenamientoListEntrenamientoToAttach : entrenamiento.getEntrenamientoList()) {
                entrenamientoListEntrenamientoToAttach = em.getReference(entrenamientoListEntrenamientoToAttach.getClass(), entrenamientoListEntrenamientoToAttach.getIdEntrenamiento());
                attachedEntrenamientoList.add(entrenamientoListEntrenamientoToAttach);
            }
            entrenamiento.setEntrenamientoList(attachedEntrenamientoList);
            List<Rms> attachedRmsList = new ArrayList<Rms>();
            for (Rms rmsListRmsToAttach : entrenamiento.getRmsList()) {
                rmsListRmsToAttach = em.getReference(rmsListRmsToAttach.getClass(), rmsListRmsToAttach.getIdRms());
                attachedRmsList.add(rmsListRmsToAttach);
            }
            entrenamiento.setRmsList(attachedRmsList);
            em.persist(entrenamiento);
            if (idCategoriaDeportista != null) {
                idCategoriaDeportista.getEntrenamientoList().add(entrenamiento);
                idCategoriaDeportista = em.merge(idCategoriaDeportista);
            }
            if (idEntrenador != null) {
                idEntrenador.getEntrenamientoList().add(entrenamiento);
                idEntrenador = em.merge(idEntrenador);
            }
            if (entrenamientoPadre != null) {
                entrenamientoPadre.getEntrenamientoList().add(entrenamiento);
                entrenamientoPadre = em.merge(entrenamientoPadre);
            }
            for (Entrenamiento entrenamientoListEntrenamiento : entrenamiento.getEntrenamientoList()) {
                Entrenamiento oldEntrenamientoPadreOfEntrenamientoListEntrenamiento = entrenamientoListEntrenamiento.getEntrenamientoPadre();
                entrenamientoListEntrenamiento.setEntrenamientoPadre(entrenamiento);
                entrenamientoListEntrenamiento = em.merge(entrenamientoListEntrenamiento);
                if (oldEntrenamientoPadreOfEntrenamientoListEntrenamiento != null) {
                    oldEntrenamientoPadreOfEntrenamientoListEntrenamiento.getEntrenamientoList().remove(entrenamientoListEntrenamiento);
                    oldEntrenamientoPadreOfEntrenamientoListEntrenamiento = em.merge(oldEntrenamientoPadreOfEntrenamientoListEntrenamiento);
                }
            }
            for (Rms rmsListRms : entrenamiento.getRmsList()) {
                Entrenamiento oldIdEntrenamientoOfRmsListRms = rmsListRms.getIdEntrenamiento();
                rmsListRms.setIdEntrenamiento(entrenamiento);
                rmsListRms = em.merge(rmsListRms);
                if (oldIdEntrenamientoOfRmsListRms != null) {
                    oldIdEntrenamientoOfRmsListRms.getRmsList().remove(rmsListRms);
                    oldIdEntrenamientoOfRmsListRms = em.merge(oldIdEntrenamientoOfRmsListRms);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEntrenamiento(entrenamiento.getIdEntrenamiento()) != null) {
                throw new PreexistingEntityException("Entrenamiento " + entrenamiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entrenamiento entrenamiento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Entrenamiento persistentEntrenamiento = em.find(Entrenamiento.class, entrenamiento.getIdEntrenamiento());
            CategoriaDeportista idCategoriaDeportistaOld = persistentEntrenamiento.getIdCategoriaDeportista();
            CategoriaDeportista idCategoriaDeportistaNew = entrenamiento.getIdCategoriaDeportista();
            Entrenador idEntrenadorOld = persistentEntrenamiento.getIdEntrenador();
            Entrenador idEntrenadorNew = entrenamiento.getIdEntrenador();
            Entrenamiento entrenamientoPadreOld = persistentEntrenamiento.getEntrenamientoPadre();
            Entrenamiento entrenamientoPadreNew = entrenamiento.getEntrenamientoPadre();
            List<Entrenamiento> entrenamientoListOld = persistentEntrenamiento.getEntrenamientoList();
            List<Entrenamiento> entrenamientoListNew = entrenamiento.getEntrenamientoList();
            List<Rms> rmsListOld = persistentEntrenamiento.getRmsList();
            List<Rms> rmsListNew = entrenamiento.getRmsList();
            if (idCategoriaDeportistaNew != null) {
                idCategoriaDeportistaNew = em.getReference(idCategoriaDeportistaNew.getClass(), idCategoriaDeportistaNew.getIdCategoriaDeportista());
                entrenamiento.setIdCategoriaDeportista(idCategoriaDeportistaNew);
            }
            if (idEntrenadorNew != null) {
                idEntrenadorNew = em.getReference(idEntrenadorNew.getClass(), idEntrenadorNew.getIdEntrenador());
                entrenamiento.setIdEntrenador(idEntrenadorNew);
            }
            if (entrenamientoPadreNew != null) {
                entrenamientoPadreNew = em.getReference(entrenamientoPadreNew.getClass(), entrenamientoPadreNew.getIdEntrenamiento());
                entrenamiento.setEntrenamientoPadre(entrenamientoPadreNew);
            }
            List<Entrenamiento> attachedEntrenamientoListNew = new ArrayList<Entrenamiento>();
            for (Entrenamiento entrenamientoListNewEntrenamientoToAttach : entrenamientoListNew) {
                entrenamientoListNewEntrenamientoToAttach = em.getReference(entrenamientoListNewEntrenamientoToAttach.getClass(), entrenamientoListNewEntrenamientoToAttach.getIdEntrenamiento());
                attachedEntrenamientoListNew.add(entrenamientoListNewEntrenamientoToAttach);
            }
            entrenamientoListNew = attachedEntrenamientoListNew;
            entrenamiento.setEntrenamientoList(entrenamientoListNew);
            List<Rms> attachedRmsListNew = new ArrayList<Rms>();
            for (Rms rmsListNewRmsToAttach : rmsListNew) {
                rmsListNewRmsToAttach = em.getReference(rmsListNewRmsToAttach.getClass(), rmsListNewRmsToAttach.getIdRms());
                attachedRmsListNew.add(rmsListNewRmsToAttach);
            }
            rmsListNew = attachedRmsListNew;
            entrenamiento.setRmsList(rmsListNew);
            entrenamiento = em.merge(entrenamiento);
            if (idCategoriaDeportistaOld != null && !idCategoriaDeportistaOld.equals(idCategoriaDeportistaNew)) {
                idCategoriaDeportistaOld.getEntrenamientoList().remove(entrenamiento);
                idCategoriaDeportistaOld = em.merge(idCategoriaDeportistaOld);
            }
            if (idCategoriaDeportistaNew != null && !idCategoriaDeportistaNew.equals(idCategoriaDeportistaOld)) {
                idCategoriaDeportistaNew.getEntrenamientoList().add(entrenamiento);
                idCategoriaDeportistaNew = em.merge(idCategoriaDeportistaNew);
            }
            if (idEntrenadorOld != null && !idEntrenadorOld.equals(idEntrenadorNew)) {
                idEntrenadorOld.getEntrenamientoList().remove(entrenamiento);
                idEntrenadorOld = em.merge(idEntrenadorOld);
            }
            if (idEntrenadorNew != null && !idEntrenadorNew.equals(idEntrenadorOld)) {
                idEntrenadorNew.getEntrenamientoList().add(entrenamiento);
                idEntrenadorNew = em.merge(idEntrenadorNew);
            }
            if (entrenamientoPadreOld != null && !entrenamientoPadreOld.equals(entrenamientoPadreNew)) {
                entrenamientoPadreOld.getEntrenamientoList().remove(entrenamiento);
                entrenamientoPadreOld = em.merge(entrenamientoPadreOld);
            }
            if (entrenamientoPadreNew != null && !entrenamientoPadreNew.equals(entrenamientoPadreOld)) {
                entrenamientoPadreNew.getEntrenamientoList().add(entrenamiento);
                entrenamientoPadreNew = em.merge(entrenamientoPadreNew);
            }
            for (Entrenamiento entrenamientoListOldEntrenamiento : entrenamientoListOld) {
                if (!entrenamientoListNew.contains(entrenamientoListOldEntrenamiento)) {
                    entrenamientoListOldEntrenamiento.setEntrenamientoPadre(null);
                    entrenamientoListOldEntrenamiento = em.merge(entrenamientoListOldEntrenamiento);
                }
            }
            for (Entrenamiento entrenamientoListNewEntrenamiento : entrenamientoListNew) {
                if (!entrenamientoListOld.contains(entrenamientoListNewEntrenamiento)) {
                    Entrenamiento oldEntrenamientoPadreOfEntrenamientoListNewEntrenamiento = entrenamientoListNewEntrenamiento.getEntrenamientoPadre();
                    entrenamientoListNewEntrenamiento.setEntrenamientoPadre(entrenamiento);
                    entrenamientoListNewEntrenamiento = em.merge(entrenamientoListNewEntrenamiento);
                    if (oldEntrenamientoPadreOfEntrenamientoListNewEntrenamiento != null && !oldEntrenamientoPadreOfEntrenamientoListNewEntrenamiento.equals(entrenamiento)) {
                        oldEntrenamientoPadreOfEntrenamientoListNewEntrenamiento.getEntrenamientoList().remove(entrenamientoListNewEntrenamiento);
                        oldEntrenamientoPadreOfEntrenamientoListNewEntrenamiento = em.merge(oldEntrenamientoPadreOfEntrenamientoListNewEntrenamiento);
                    }
                }
            }
            for (Rms rmsListOldRms : rmsListOld) {
                if (!rmsListNew.contains(rmsListOldRms)) {
                    rmsListOldRms.setIdEntrenamiento(null);
                    rmsListOldRms = em.merge(rmsListOldRms);
                }
            }
            for (Rms rmsListNewRms : rmsListNew) {
                if (!rmsListOld.contains(rmsListNewRms)) {
                    Entrenamiento oldIdEntrenamientoOfRmsListNewRms = rmsListNewRms.getIdEntrenamiento();
                    rmsListNewRms.setIdEntrenamiento(entrenamiento);
                    rmsListNewRms = em.merge(rmsListNewRms);
                    if (oldIdEntrenamientoOfRmsListNewRms != null && !oldIdEntrenamientoOfRmsListNewRms.equals(entrenamiento)) {
                        oldIdEntrenamientoOfRmsListNewRms.getRmsList().remove(rmsListNewRms);
                        oldIdEntrenamientoOfRmsListNewRms = em.merge(oldIdEntrenamientoOfRmsListNewRms);
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
                Integer id = entrenamiento.getIdEntrenamiento();
                if (findEntrenamiento(id) == null) {
                    throw new NonexistentEntityException("The entrenamiento with id " + id + " no longer exists.");
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
            Entrenamiento entrenamiento;
            try {
                entrenamiento = em.getReference(Entrenamiento.class, id);
                entrenamiento.getIdEntrenamiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entrenamiento with id " + id + " no longer exists.", enfe);
            }
            CategoriaDeportista idCategoriaDeportista = entrenamiento.getIdCategoriaDeportista();
            if (idCategoriaDeportista != null) {
                idCategoriaDeportista.getEntrenamientoList().remove(entrenamiento);
                idCategoriaDeportista = em.merge(idCategoriaDeportista);
            }
            Entrenador idEntrenador = entrenamiento.getIdEntrenador();
            if (idEntrenador != null) {
                idEntrenador.getEntrenamientoList().remove(entrenamiento);
                idEntrenador = em.merge(idEntrenador);
            }
            Entrenamiento entrenamientoPadre = entrenamiento.getEntrenamientoPadre();
            if (entrenamientoPadre != null) {
                entrenamientoPadre.getEntrenamientoList().remove(entrenamiento);
                entrenamientoPadre = em.merge(entrenamientoPadre);
            }
            List<Entrenamiento> entrenamientoList = entrenamiento.getEntrenamientoList();
            for (Entrenamiento entrenamientoListEntrenamiento : entrenamientoList) {
                entrenamientoListEntrenamiento.setEntrenamientoPadre(null);
                entrenamientoListEntrenamiento = em.merge(entrenamientoListEntrenamiento);
            }
            List<Rms> rmsList = entrenamiento.getRmsList();
            for (Rms rmsListRms : rmsList) {
                rmsListRms.setIdEntrenamiento(null);
                rmsListRms = em.merge(rmsListRms);
            }
            em.remove(entrenamiento);
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

    public List<Entrenamiento> findEntrenamientoEntities() {
        return findEntrenamientoEntities(true, -1, -1);
    }

    public List<Entrenamiento> findEntrenamientoEntities(int maxResults, int firstResult) {
        return findEntrenamientoEntities(false, maxResults, firstResult);
    }

    private List<Entrenamiento> findEntrenamientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entrenamiento.class));
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

    public Entrenamiento findEntrenamiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entrenamiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntrenamientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entrenamiento> rt = cq.from(Entrenamiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
