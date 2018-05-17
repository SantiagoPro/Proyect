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
import java.util.ArrayList;
import java.util.List;
import com.rid.modelo.entities.Entrenador;
import com.rid.modelo.entities.Horario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class HorarioJpaController implements Serializable {

    public HorarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Horario horario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (horario.getDeportistaList() == null) {
            horario.setDeportistaList(new ArrayList<Deportista>());
        }
        if (horario.getEntrenadorList() == null) {
            horario.setEntrenadorList(new ArrayList<Entrenador>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Deportista> attachedDeportistaList = new ArrayList<Deportista>();
            for (Deportista deportistaListDeportistaToAttach : horario.getDeportistaList()) {
                deportistaListDeportistaToAttach = em.getReference(deportistaListDeportistaToAttach.getClass(), deportistaListDeportistaToAttach.getIdDeportista());
                attachedDeportistaList.add(deportistaListDeportistaToAttach);
            }
            horario.setDeportistaList(attachedDeportistaList);
            List<Entrenador> attachedEntrenadorList = new ArrayList<Entrenador>();
            for (Entrenador entrenadorListEntrenadorToAttach : horario.getEntrenadorList()) {
                entrenadorListEntrenadorToAttach = em.getReference(entrenadorListEntrenadorToAttach.getClass(), entrenadorListEntrenadorToAttach.getIdEntrenador());
                attachedEntrenadorList.add(entrenadorListEntrenadorToAttach);
            }
            horario.setEntrenadorList(attachedEntrenadorList);
            em.persist(horario);
            for (Deportista deportistaListDeportista : horario.getDeportistaList()) {
                deportistaListDeportista.getHorarioList().add(horario);
                deportistaListDeportista = em.merge(deportistaListDeportista);
            }
            for (Entrenador entrenadorListEntrenador : horario.getEntrenadorList()) {
                entrenadorListEntrenador.getHorarioList().add(horario);
                entrenadorListEntrenador = em.merge(entrenadorListEntrenador);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHorario(horario.getIdHorario()) != null) {
                throw new PreexistingEntityException("Horario " + horario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Horario horario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Horario persistentHorario = em.find(Horario.class, horario.getIdHorario());
            List<Deportista> deportistaListOld = persistentHorario.getDeportistaList();
            List<Deportista> deportistaListNew = horario.getDeportistaList();
            List<Entrenador> entrenadorListOld = persistentHorario.getEntrenadorList();
            List<Entrenador> entrenadorListNew = horario.getEntrenadorList();
            List<Deportista> attachedDeportistaListNew = new ArrayList<Deportista>();
            for (Deportista deportistaListNewDeportistaToAttach : deportistaListNew) {
                deportistaListNewDeportistaToAttach = em.getReference(deportistaListNewDeportistaToAttach.getClass(), deportistaListNewDeportistaToAttach.getIdDeportista());
                attachedDeportistaListNew.add(deportistaListNewDeportistaToAttach);
            }
            deportistaListNew = attachedDeportistaListNew;
            horario.setDeportistaList(deportistaListNew);
            List<Entrenador> attachedEntrenadorListNew = new ArrayList<Entrenador>();
            for (Entrenador entrenadorListNewEntrenadorToAttach : entrenadorListNew) {
                entrenadorListNewEntrenadorToAttach = em.getReference(entrenadorListNewEntrenadorToAttach.getClass(), entrenadorListNewEntrenadorToAttach.getIdEntrenador());
                attachedEntrenadorListNew.add(entrenadorListNewEntrenadorToAttach);
            }
            entrenadorListNew = attachedEntrenadorListNew;
            horario.setEntrenadorList(entrenadorListNew);
            horario = em.merge(horario);
            for (Deportista deportistaListOldDeportista : deportistaListOld) {
                if (!deportistaListNew.contains(deportistaListOldDeportista)) {
                    deportistaListOldDeportista.getHorarioList().remove(horario);
                    deportistaListOldDeportista = em.merge(deportistaListOldDeportista);
                }
            }
            for (Deportista deportistaListNewDeportista : deportistaListNew) {
                if (!deportistaListOld.contains(deportistaListNewDeportista)) {
                    deportistaListNewDeportista.getHorarioList().add(horario);
                    deportistaListNewDeportista = em.merge(deportistaListNewDeportista);
                }
            }
            for (Entrenador entrenadorListOldEntrenador : entrenadorListOld) {
                if (!entrenadorListNew.contains(entrenadorListOldEntrenador)) {
                    entrenadorListOldEntrenador.getHorarioList().remove(horario);
                    entrenadorListOldEntrenador = em.merge(entrenadorListOldEntrenador);
                }
            }
            for (Entrenador entrenadorListNewEntrenador : entrenadorListNew) {
                if (!entrenadorListOld.contains(entrenadorListNewEntrenador)) {
                    entrenadorListNewEntrenador.getHorarioList().add(horario);
                    entrenadorListNewEntrenador = em.merge(entrenadorListNewEntrenador);
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
                Integer id = horario.getIdHorario();
                if (findHorario(id) == null) {
                    throw new NonexistentEntityException("The horario with id " + id + " no longer exists.");
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
            Horario horario;
            try {
                horario = em.getReference(Horario.class, id);
                horario.getIdHorario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horario with id " + id + " no longer exists.", enfe);
            }
            List<Deportista> deportistaList = horario.getDeportistaList();
            for (Deportista deportistaListDeportista : deportistaList) {
                deportistaListDeportista.getHorarioList().remove(horario);
                deportistaListDeportista = em.merge(deportistaListDeportista);
            }
            List<Entrenador> entrenadorList = horario.getEntrenadorList();
            for (Entrenador entrenadorListEntrenador : entrenadorList) {
                entrenadorListEntrenador.getHorarioList().remove(horario);
                entrenadorListEntrenador = em.merge(entrenadorListEntrenador);
            }
            em.remove(horario);
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

    public List<Horario> findHorarioEntities() {
        return findHorarioEntities(true, -1, -1);
    }

    public List<Horario> findHorarioEntities(int maxResults, int firstResult) {
        return findHorarioEntities(false, maxResults, firstResult);
    }

    private List<Horario> findHorarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Horario.class));
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

    public Horario findHorario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Horario.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Horario> rt = cq.from(Horario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
