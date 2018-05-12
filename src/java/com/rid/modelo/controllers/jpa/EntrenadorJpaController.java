/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.jpa;

import com.rid.modelo.controllers.jpa.exceptions.IllegalOrphanException;
import com.rid.modelo.controllers.jpa.exceptions.NonexistentEntityException;
import com.rid.modelo.controllers.jpa.exceptions.PreexistingEntityException;
import com.rid.modelo.controllers.jpa.exceptions.RollbackFailureException;
import com.rid.modelo.entities.Entrenador;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rid.modelo.entities.Usuario;
import com.rid.modelo.entities.Horario;
import java.util.ArrayList;
import java.util.List;
import com.rid.modelo.entities.Entrenamiento;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class EntrenadorJpaController implements Serializable {

    public EntrenadorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entrenador entrenador) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        if (entrenador.getHorarioList() == null) {
            entrenador.setHorarioList(new ArrayList<Horario>());
        }
        if (entrenador.getEntrenamientoList() == null) {
            entrenador.setEntrenamientoList(new ArrayList<Entrenamiento>());
        }
        List<String> illegalOrphanMessages = null;
        Usuario usuarioOrphanCheck = entrenador.getUsuario();
        if (usuarioOrphanCheck != null) {
            Entrenador oldEntrenadorOfUsuario = usuarioOrphanCheck.getEntrenador();
            if (oldEntrenadorOfUsuario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuarioOrphanCheck + " already has an item of type Entrenador whose usuario column cannot be null. Please make another selection for the usuario field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuario = entrenador.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdUsuarios());
                entrenador.setUsuario(usuario);
            }
            List<Horario> attachedHorarioList = new ArrayList<Horario>();
            for (Horario horarioListHorarioToAttach : entrenador.getHorarioList()) {
                horarioListHorarioToAttach = em.getReference(horarioListHorarioToAttach.getClass(), horarioListHorarioToAttach.getIdHorario());
                attachedHorarioList.add(horarioListHorarioToAttach);
            }
            entrenador.setHorarioList(attachedHorarioList);
            List<Entrenamiento> attachedEntrenamientoList = new ArrayList<Entrenamiento>();
            for (Entrenamiento entrenamientoListEntrenamientoToAttach : entrenador.getEntrenamientoList()) {
                entrenamientoListEntrenamientoToAttach = em.getReference(entrenamientoListEntrenamientoToAttach.getClass(), entrenamientoListEntrenamientoToAttach.getIdEntrenamiento());
                attachedEntrenamientoList.add(entrenamientoListEntrenamientoToAttach);
            }
            entrenador.setEntrenamientoList(attachedEntrenamientoList);
            em.persist(entrenador);
            if (usuario != null) {
                usuario.setEntrenador(entrenador);
                usuario = em.merge(usuario);
            }
            for (Horario horarioListHorario : entrenador.getHorarioList()) {
                horarioListHorario.getEntrenadorList().add(entrenador);
                horarioListHorario = em.merge(horarioListHorario);
            }
            for (Entrenamiento entrenamientoListEntrenamiento : entrenador.getEntrenamientoList()) {
                Entrenador oldIdEntrenadorOfEntrenamientoListEntrenamiento = entrenamientoListEntrenamiento.getIdEntrenador();
                entrenamientoListEntrenamiento.setIdEntrenador(entrenador);
                entrenamientoListEntrenamiento = em.merge(entrenamientoListEntrenamiento);
                if (oldIdEntrenadorOfEntrenamientoListEntrenamiento != null) {
                    oldIdEntrenadorOfEntrenamientoListEntrenamiento.getEntrenamientoList().remove(entrenamientoListEntrenamiento);
                    oldIdEntrenadorOfEntrenamientoListEntrenamiento = em.merge(oldIdEntrenadorOfEntrenamientoListEntrenamiento);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEntrenador(entrenador.getIdEntrenador()) != null) {
                throw new PreexistingEntityException("Entrenador " + entrenador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entrenador entrenador) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Entrenador persistentEntrenador = em.find(Entrenador.class, entrenador.getIdEntrenador());
            Usuario usuarioOld = persistentEntrenador.getUsuario();
            Usuario usuarioNew = entrenador.getUsuario();
            List<Horario> horarioListOld = persistentEntrenador.getHorarioList();
            List<Horario> horarioListNew = entrenador.getHorarioList();
            List<Entrenamiento> entrenamientoListOld = persistentEntrenador.getEntrenamientoList();
            List<Entrenamiento> entrenamientoListNew = entrenador.getEntrenamientoList();
            List<String> illegalOrphanMessages = null;
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                Entrenador oldEntrenadorOfUsuario = usuarioNew.getEntrenador();
                if (oldEntrenadorOfUsuario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuarioNew + " already has an item of type Entrenador whose usuario column cannot be null. Please make another selection for the usuario field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdUsuarios());
                entrenador.setUsuario(usuarioNew);
            }
            List<Horario> attachedHorarioListNew = new ArrayList<Horario>();
            for (Horario horarioListNewHorarioToAttach : horarioListNew) {
                horarioListNewHorarioToAttach = em.getReference(horarioListNewHorarioToAttach.getClass(), horarioListNewHorarioToAttach.getIdHorario());
                attachedHorarioListNew.add(horarioListNewHorarioToAttach);
            }
            horarioListNew = attachedHorarioListNew;
            entrenador.setHorarioList(horarioListNew);
            List<Entrenamiento> attachedEntrenamientoListNew = new ArrayList<Entrenamiento>();
            for (Entrenamiento entrenamientoListNewEntrenamientoToAttach : entrenamientoListNew) {
                entrenamientoListNewEntrenamientoToAttach = em.getReference(entrenamientoListNewEntrenamientoToAttach.getClass(), entrenamientoListNewEntrenamientoToAttach.getIdEntrenamiento());
                attachedEntrenamientoListNew.add(entrenamientoListNewEntrenamientoToAttach);
            }
            entrenamientoListNew = attachedEntrenamientoListNew;
            entrenador.setEntrenamientoList(entrenamientoListNew);
            entrenador = em.merge(entrenador);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.setEntrenador(null);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.setEntrenador(entrenador);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Horario horarioListOldHorario : horarioListOld) {
                if (!horarioListNew.contains(horarioListOldHorario)) {
                    horarioListOldHorario.getEntrenadorList().remove(entrenador);
                    horarioListOldHorario = em.merge(horarioListOldHorario);
                }
            }
            for (Horario horarioListNewHorario : horarioListNew) {
                if (!horarioListOld.contains(horarioListNewHorario)) {
                    horarioListNewHorario.getEntrenadorList().add(entrenador);
                    horarioListNewHorario = em.merge(horarioListNewHorario);
                }
            }
            for (Entrenamiento entrenamientoListOldEntrenamiento : entrenamientoListOld) {
                if (!entrenamientoListNew.contains(entrenamientoListOldEntrenamiento)) {
                    entrenamientoListOldEntrenamiento.setIdEntrenador(null);
                    entrenamientoListOldEntrenamiento = em.merge(entrenamientoListOldEntrenamiento);
                }
            }
            for (Entrenamiento entrenamientoListNewEntrenamiento : entrenamientoListNew) {
                if (!entrenamientoListOld.contains(entrenamientoListNewEntrenamiento)) {
                    Entrenador oldIdEntrenadorOfEntrenamientoListNewEntrenamiento = entrenamientoListNewEntrenamiento.getIdEntrenador();
                    entrenamientoListNewEntrenamiento.setIdEntrenador(entrenador);
                    entrenamientoListNewEntrenamiento = em.merge(entrenamientoListNewEntrenamiento);
                    if (oldIdEntrenadorOfEntrenamientoListNewEntrenamiento != null && !oldIdEntrenadorOfEntrenamientoListNewEntrenamiento.equals(entrenador)) {
                        oldIdEntrenadorOfEntrenamientoListNewEntrenamiento.getEntrenamientoList().remove(entrenamientoListNewEntrenamiento);
                        oldIdEntrenadorOfEntrenamientoListNewEntrenamiento = em.merge(oldIdEntrenadorOfEntrenamientoListNewEntrenamiento);
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
                Long id = entrenador.getIdEntrenador();
                if (findEntrenador(id) == null) {
                    throw new NonexistentEntityException("The entrenador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Entrenador entrenador;
            try {
                entrenador = em.getReference(Entrenador.class, id);
                entrenador.getIdEntrenador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entrenador with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario = entrenador.getUsuario();
            if (usuario != null) {
                usuario.setEntrenador(null);
                usuario = em.merge(usuario);
            }
            List<Horario> horarioList = entrenador.getHorarioList();
            for (Horario horarioListHorario : horarioList) {
                horarioListHorario.getEntrenadorList().remove(entrenador);
                horarioListHorario = em.merge(horarioListHorario);
            }
            List<Entrenamiento> entrenamientoList = entrenador.getEntrenamientoList();
            for (Entrenamiento entrenamientoListEntrenamiento : entrenamientoList) {
                entrenamientoListEntrenamiento.setIdEntrenador(null);
                entrenamientoListEntrenamiento = em.merge(entrenamientoListEntrenamiento);
            }
            em.remove(entrenador);
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

    public List<Entrenador> findEntrenadorEntities() {
        return findEntrenadorEntities(true, -1, -1);
    }

    public List<Entrenador> findEntrenadorEntities(int maxResults, int firstResult) {
        return findEntrenadorEntities(false, maxResults, firstResult);
    }

    private List<Entrenador> findEntrenadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entrenador.class));
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

    public Entrenador findEntrenador(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entrenador.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntrenadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entrenador> rt = cq.from(Entrenador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
