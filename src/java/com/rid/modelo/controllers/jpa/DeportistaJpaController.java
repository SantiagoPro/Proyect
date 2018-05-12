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
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rid.modelo.entities.Usuario;
import com.rid.modelo.entities.Horario;
import java.util.ArrayList;
import java.util.List;
import com.rid.modelo.entities.CategoriaDeportista;
import com.rid.modelo.entities.Deportista;
import com.rid.modelo.entities.Imc;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class DeportistaJpaController implements Serializable {

    public DeportistaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Deportista deportista) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        if (deportista.getHorarioList() == null) {
            deportista.setHorarioList(new ArrayList<Horario>());
        }
        if (deportista.getCategoriaDeportistaList() == null) {
            deportista.setCategoriaDeportistaList(new ArrayList<CategoriaDeportista>());
        }
        if (deportista.getImcList() == null) {
            deportista.setImcList(new ArrayList<Imc>());
        }
        List<String> illegalOrphanMessages = null;
        Usuario usuarioOrphanCheck = deportista.getUsuario();
        if (usuarioOrphanCheck != null) {
            Deportista oldDeportistaOfUsuario = usuarioOrphanCheck.getDeportista();
            if (oldDeportistaOfUsuario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuarioOrphanCheck + " already has an item of type Deportista whose usuario column cannot be null. Please make another selection for the usuario field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuario = deportista.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdUsuarios());
                deportista.setUsuario(usuario);
            }
            List<Horario> attachedHorarioList = new ArrayList<Horario>();
            for (Horario horarioListHorarioToAttach : deportista.getHorarioList()) {
                horarioListHorarioToAttach = em.getReference(horarioListHorarioToAttach.getClass(), horarioListHorarioToAttach.getIdHorario());
                attachedHorarioList.add(horarioListHorarioToAttach);
            }
            deportista.setHorarioList(attachedHorarioList);
            List<CategoriaDeportista> attachedCategoriaDeportistaList = new ArrayList<CategoriaDeportista>();
            for (CategoriaDeportista categoriaDeportistaListCategoriaDeportistaToAttach : deportista.getCategoriaDeportistaList()) {
                categoriaDeportistaListCategoriaDeportistaToAttach = em.getReference(categoriaDeportistaListCategoriaDeportistaToAttach.getClass(), categoriaDeportistaListCategoriaDeportistaToAttach.getIdCategoriaDeportista());
                attachedCategoriaDeportistaList.add(categoriaDeportistaListCategoriaDeportistaToAttach);
            }
            deportista.setCategoriaDeportistaList(attachedCategoriaDeportistaList);
            List<Imc> attachedImcList = new ArrayList<Imc>();
            for (Imc imcListImcToAttach : deportista.getImcList()) {
                imcListImcToAttach = em.getReference(imcListImcToAttach.getClass(), imcListImcToAttach.getIdImc());
                attachedImcList.add(imcListImcToAttach);
            }
            deportista.setImcList(attachedImcList);
            em.persist(deportista);
            if (usuario != null) {
                usuario.setDeportista(deportista);
                usuario = em.merge(usuario);
            }
            for (Horario horarioListHorario : deportista.getHorarioList()) {
                horarioListHorario.getDeportistaList().add(deportista);
                horarioListHorario = em.merge(horarioListHorario);
            }
            for (CategoriaDeportista categoriaDeportistaListCategoriaDeportista : deportista.getCategoriaDeportistaList()) {
                Deportista oldIdDeportistaOfCategoriaDeportistaListCategoriaDeportista = categoriaDeportistaListCategoriaDeportista.getIdDeportista();
                categoriaDeportistaListCategoriaDeportista.setIdDeportista(deportista);
                categoriaDeportistaListCategoriaDeportista = em.merge(categoriaDeportistaListCategoriaDeportista);
                if (oldIdDeportistaOfCategoriaDeportistaListCategoriaDeportista != null) {
                    oldIdDeportistaOfCategoriaDeportistaListCategoriaDeportista.getCategoriaDeportistaList().remove(categoriaDeportistaListCategoriaDeportista);
                    oldIdDeportistaOfCategoriaDeportistaListCategoriaDeportista = em.merge(oldIdDeportistaOfCategoriaDeportistaListCategoriaDeportista);
                }
            }
            for (Imc imcListImc : deportista.getImcList()) {
                Deportista oldIdDeportistaOfImcListImc = imcListImc.getIdDeportista();
                imcListImc.setIdDeportista(deportista);
                imcListImc = em.merge(imcListImc);
                if (oldIdDeportistaOfImcListImc != null) {
                    oldIdDeportistaOfImcListImc.getImcList().remove(imcListImc);
                    oldIdDeportistaOfImcListImc = em.merge(oldIdDeportistaOfImcListImc);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findDeportista(deportista.getIdDeportista()) != null) {
                throw new PreexistingEntityException("Deportista " + deportista + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Deportista deportista) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Deportista persistentDeportista = em.find(Deportista.class, deportista.getIdDeportista());
            Usuario usuarioOld = persistentDeportista.getUsuario();
            Usuario usuarioNew = deportista.getUsuario();
            List<Horario> horarioListOld = persistentDeportista.getHorarioList();
            List<Horario> horarioListNew = deportista.getHorarioList();
            List<CategoriaDeportista> categoriaDeportistaListOld = persistentDeportista.getCategoriaDeportistaList();
            List<CategoriaDeportista> categoriaDeportistaListNew = deportista.getCategoriaDeportistaList();
            List<Imc> imcListOld = persistentDeportista.getImcList();
            List<Imc> imcListNew = deportista.getImcList();
            List<String> illegalOrphanMessages = null;
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                Deportista oldDeportistaOfUsuario = usuarioNew.getDeportista();
                if (oldDeportistaOfUsuario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuarioNew + " already has an item of type Deportista whose usuario column cannot be null. Please make another selection for the usuario field.");
                }
            }
            for (CategoriaDeportista categoriaDeportistaListOldCategoriaDeportista : categoriaDeportistaListOld) {
                if (!categoriaDeportistaListNew.contains(categoriaDeportistaListOldCategoriaDeportista)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CategoriaDeportista " + categoriaDeportistaListOldCategoriaDeportista + " since its idDeportista field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdUsuarios());
                deportista.setUsuario(usuarioNew);
            }
            List<Horario> attachedHorarioListNew = new ArrayList<Horario>();
            for (Horario horarioListNewHorarioToAttach : horarioListNew) {
                horarioListNewHorarioToAttach = em.getReference(horarioListNewHorarioToAttach.getClass(), horarioListNewHorarioToAttach.getIdHorario());
                attachedHorarioListNew.add(horarioListNewHorarioToAttach);
            }
            horarioListNew = attachedHorarioListNew;
            deportista.setHorarioList(horarioListNew);
            List<CategoriaDeportista> attachedCategoriaDeportistaListNew = new ArrayList<CategoriaDeportista>();
            for (CategoriaDeportista categoriaDeportistaListNewCategoriaDeportistaToAttach : categoriaDeportistaListNew) {
                categoriaDeportistaListNewCategoriaDeportistaToAttach = em.getReference(categoriaDeportistaListNewCategoriaDeportistaToAttach.getClass(), categoriaDeportistaListNewCategoriaDeportistaToAttach.getIdCategoriaDeportista());
                attachedCategoriaDeportistaListNew.add(categoriaDeportistaListNewCategoriaDeportistaToAttach);
            }
            categoriaDeportistaListNew = attachedCategoriaDeportistaListNew;
            deportista.setCategoriaDeportistaList(categoriaDeportistaListNew);
            List<Imc> attachedImcListNew = new ArrayList<Imc>();
            for (Imc imcListNewImcToAttach : imcListNew) {
                imcListNewImcToAttach = em.getReference(imcListNewImcToAttach.getClass(), imcListNewImcToAttach.getIdImc());
                attachedImcListNew.add(imcListNewImcToAttach);
            }
            imcListNew = attachedImcListNew;
            deportista.setImcList(imcListNew);
            deportista = em.merge(deportista);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.setDeportista(null);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.setDeportista(deportista);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Horario horarioListOldHorario : horarioListOld) {
                if (!horarioListNew.contains(horarioListOldHorario)) {
                    horarioListOldHorario.getDeportistaList().remove(deportista);
                    horarioListOldHorario = em.merge(horarioListOldHorario);
                }
            }
            for (Horario horarioListNewHorario : horarioListNew) {
                if (!horarioListOld.contains(horarioListNewHorario)) {
                    horarioListNewHorario.getDeportistaList().add(deportista);
                    horarioListNewHorario = em.merge(horarioListNewHorario);
                }
            }
            for (CategoriaDeportista categoriaDeportistaListNewCategoriaDeportista : categoriaDeportistaListNew) {
                if (!categoriaDeportistaListOld.contains(categoriaDeportistaListNewCategoriaDeportista)) {
                    Deportista oldIdDeportistaOfCategoriaDeportistaListNewCategoriaDeportista = categoriaDeportistaListNewCategoriaDeportista.getIdDeportista();
                    categoriaDeportistaListNewCategoriaDeportista.setIdDeportista(deportista);
                    categoriaDeportistaListNewCategoriaDeportista = em.merge(categoriaDeportistaListNewCategoriaDeportista);
                    if (oldIdDeportistaOfCategoriaDeportistaListNewCategoriaDeportista != null && !oldIdDeportistaOfCategoriaDeportistaListNewCategoriaDeportista.equals(deportista)) {
                        oldIdDeportistaOfCategoriaDeportistaListNewCategoriaDeportista.getCategoriaDeportistaList().remove(categoriaDeportistaListNewCategoriaDeportista);
                        oldIdDeportistaOfCategoriaDeportistaListNewCategoriaDeportista = em.merge(oldIdDeportistaOfCategoriaDeportistaListNewCategoriaDeportista);
                    }
                }
            }
            for (Imc imcListOldImc : imcListOld) {
                if (!imcListNew.contains(imcListOldImc)) {
                    imcListOldImc.setIdDeportista(null);
                    imcListOldImc = em.merge(imcListOldImc);
                }
            }
            for (Imc imcListNewImc : imcListNew) {
                if (!imcListOld.contains(imcListNewImc)) {
                    Deportista oldIdDeportistaOfImcListNewImc = imcListNewImc.getIdDeportista();
                    imcListNewImc.setIdDeportista(deportista);
                    imcListNewImc = em.merge(imcListNewImc);
                    if (oldIdDeportistaOfImcListNewImc != null && !oldIdDeportistaOfImcListNewImc.equals(deportista)) {
                        oldIdDeportistaOfImcListNewImc.getImcList().remove(imcListNewImc);
                        oldIdDeportistaOfImcListNewImc = em.merge(oldIdDeportistaOfImcListNewImc);
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
                Long id = deportista.getIdDeportista();
                if (findDeportista(id) == null) {
                    throw new NonexistentEntityException("The deportista with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Deportista deportista;
            try {
                deportista = em.getReference(Deportista.class, id);
                deportista.getIdDeportista();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The deportista with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CategoriaDeportista> categoriaDeportistaListOrphanCheck = deportista.getCategoriaDeportistaList();
            for (CategoriaDeportista categoriaDeportistaListOrphanCheckCategoriaDeportista : categoriaDeportistaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Deportista (" + deportista + ") cannot be destroyed since the CategoriaDeportista " + categoriaDeportistaListOrphanCheckCategoriaDeportista + " in its categoriaDeportistaList field has a non-nullable idDeportista field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario = deportista.getUsuario();
            if (usuario != null) {
                usuario.setDeportista(null);
                usuario = em.merge(usuario);
            }
            List<Horario> horarioList = deportista.getHorarioList();
            for (Horario horarioListHorario : horarioList) {
                horarioListHorario.getDeportistaList().remove(deportista);
                horarioListHorario = em.merge(horarioListHorario);
            }
            List<Imc> imcList = deportista.getImcList();
            for (Imc imcListImc : imcList) {
                imcListImc.setIdDeportista(null);
                imcListImc = em.merge(imcListImc);
            }
            em.remove(deportista);
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

    public List<Deportista> findDeportistaEntities() {
        return findDeportistaEntities(true, -1, -1);
    }

    public List<Deportista> findDeportistaEntities(int maxResults, int firstResult) {
        return findDeportistaEntities(false, maxResults, firstResult);
    }

    private List<Deportista> findDeportistaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Deportista.class));
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

    public Deportista findDeportista(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Deportista.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeportistaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Deportista> rt = cq.from(Deportista.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
