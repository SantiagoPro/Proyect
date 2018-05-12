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
import com.rid.modelo.entities.CategoriaEdad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class CategoriaEdadJpaController implements Serializable {

    public CategoriaEdadJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaEdad categoriaEdad) throws RollbackFailureException, Exception {
        if (categoriaEdad.getCategoriaList() == null) {
            categoriaEdad.setCategoriaList(new ArrayList<Categoria>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Categoria> attachedCategoriaList = new ArrayList<Categoria>();
            for (Categoria categoriaListCategoriaToAttach : categoriaEdad.getCategoriaList()) {
                categoriaListCategoriaToAttach = em.getReference(categoriaListCategoriaToAttach.getClass(), categoriaListCategoriaToAttach.getIdCategoria());
                attachedCategoriaList.add(categoriaListCategoriaToAttach);
            }
            categoriaEdad.setCategoriaList(attachedCategoriaList);
            em.persist(categoriaEdad);
            for (Categoria categoriaListCategoria : categoriaEdad.getCategoriaList()) {
                CategoriaEdad oldIdCategoriaEdadOfCategoriaListCategoria = categoriaListCategoria.getIdCategoriaEdad();
                categoriaListCategoria.setIdCategoriaEdad(categoriaEdad);
                categoriaListCategoria = em.merge(categoriaListCategoria);
                if (oldIdCategoriaEdadOfCategoriaListCategoria != null) {
                    oldIdCategoriaEdadOfCategoriaListCategoria.getCategoriaList().remove(categoriaListCategoria);
                    oldIdCategoriaEdadOfCategoriaListCategoria = em.merge(oldIdCategoriaEdadOfCategoriaListCategoria);
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

    public void edit(CategoriaEdad categoriaEdad) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoriaEdad persistentCategoriaEdad = em.find(CategoriaEdad.class, categoriaEdad.getIdCategoriaEdad());
            List<Categoria> categoriaListOld = persistentCategoriaEdad.getCategoriaList();
            List<Categoria> categoriaListNew = categoriaEdad.getCategoriaList();
            List<String> illegalOrphanMessages = null;
            for (Categoria categoriaListOldCategoria : categoriaListOld) {
                if (!categoriaListNew.contains(categoriaListOldCategoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Categoria " + categoriaListOldCategoria + " since its idCategoriaEdad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Categoria> attachedCategoriaListNew = new ArrayList<Categoria>();
            for (Categoria categoriaListNewCategoriaToAttach : categoriaListNew) {
                categoriaListNewCategoriaToAttach = em.getReference(categoriaListNewCategoriaToAttach.getClass(), categoriaListNewCategoriaToAttach.getIdCategoria());
                attachedCategoriaListNew.add(categoriaListNewCategoriaToAttach);
            }
            categoriaListNew = attachedCategoriaListNew;
            categoriaEdad.setCategoriaList(categoriaListNew);
            categoriaEdad = em.merge(categoriaEdad);
            for (Categoria categoriaListNewCategoria : categoriaListNew) {
                if (!categoriaListOld.contains(categoriaListNewCategoria)) {
                    CategoriaEdad oldIdCategoriaEdadOfCategoriaListNewCategoria = categoriaListNewCategoria.getIdCategoriaEdad();
                    categoriaListNewCategoria.setIdCategoriaEdad(categoriaEdad);
                    categoriaListNewCategoria = em.merge(categoriaListNewCategoria);
                    if (oldIdCategoriaEdadOfCategoriaListNewCategoria != null && !oldIdCategoriaEdadOfCategoriaListNewCategoria.equals(categoriaEdad)) {
                        oldIdCategoriaEdadOfCategoriaListNewCategoria.getCategoriaList().remove(categoriaListNewCategoria);
                        oldIdCategoriaEdadOfCategoriaListNewCategoria = em.merge(oldIdCategoriaEdadOfCategoriaListNewCategoria);
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
                Integer id = categoriaEdad.getIdCategoriaEdad();
                if (findCategoriaEdad(id) == null) {
                    throw new NonexistentEntityException("The categoriaEdad with id " + id + " no longer exists.");
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
            CategoriaEdad categoriaEdad;
            try {
                categoriaEdad = em.getReference(CategoriaEdad.class, id);
                categoriaEdad.getIdCategoriaEdad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaEdad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Categoria> categoriaListOrphanCheck = categoriaEdad.getCategoriaList();
            for (Categoria categoriaListOrphanCheckCategoria : categoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CategoriaEdad (" + categoriaEdad + ") cannot be destroyed since the Categoria " + categoriaListOrphanCheckCategoria + " in its categoriaList field has a non-nullable idCategoriaEdad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(categoriaEdad);
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

    public List<CategoriaEdad> findCategoriaEdadEntities() {
        return findCategoriaEdadEntities(true, -1, -1);
    }

    public List<CategoriaEdad> findCategoriaEdadEntities(int maxResults, int firstResult) {
        return findCategoriaEdadEntities(false, maxResults, firstResult);
    }

    private List<CategoriaEdad> findCategoriaEdadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaEdad.class));
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

    public CategoriaEdad findCategoriaEdad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaEdad.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaEdadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaEdad> rt = cq.from(CategoriaEdad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
