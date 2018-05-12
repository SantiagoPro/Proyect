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
import com.rid.modelo.entities.CategoriaPeso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class CategoriaPesoJpaController implements Serializable {

    public CategoriaPesoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaPeso categoriaPeso) throws RollbackFailureException, Exception {
        if (categoriaPeso.getCategoriaList() == null) {
            categoriaPeso.setCategoriaList(new ArrayList<Categoria>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Categoria> attachedCategoriaList = new ArrayList<Categoria>();
            for (Categoria categoriaListCategoriaToAttach : categoriaPeso.getCategoriaList()) {
                categoriaListCategoriaToAttach = em.getReference(categoriaListCategoriaToAttach.getClass(), categoriaListCategoriaToAttach.getIdCategoria());
                attachedCategoriaList.add(categoriaListCategoriaToAttach);
            }
            categoriaPeso.setCategoriaList(attachedCategoriaList);
            em.persist(categoriaPeso);
            for (Categoria categoriaListCategoria : categoriaPeso.getCategoriaList()) {
                CategoriaPeso oldIdCategoriaPesoOfCategoriaListCategoria = categoriaListCategoria.getIdCategoriaPeso();
                categoriaListCategoria.setIdCategoriaPeso(categoriaPeso);
                categoriaListCategoria = em.merge(categoriaListCategoria);
                if (oldIdCategoriaPesoOfCategoriaListCategoria != null) {
                    oldIdCategoriaPesoOfCategoriaListCategoria.getCategoriaList().remove(categoriaListCategoria);
                    oldIdCategoriaPesoOfCategoriaListCategoria = em.merge(oldIdCategoriaPesoOfCategoriaListCategoria);
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

    public void edit(CategoriaPeso categoriaPeso) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoriaPeso persistentCategoriaPeso = em.find(CategoriaPeso.class, categoriaPeso.getIdCategoriaPeso());
            List<Categoria> categoriaListOld = persistentCategoriaPeso.getCategoriaList();
            List<Categoria> categoriaListNew = categoriaPeso.getCategoriaList();
            List<String> illegalOrphanMessages = null;
            for (Categoria categoriaListOldCategoria : categoriaListOld) {
                if (!categoriaListNew.contains(categoriaListOldCategoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Categoria " + categoriaListOldCategoria + " since its idCategoriaPeso field is not nullable.");
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
            categoriaPeso.setCategoriaList(categoriaListNew);
            categoriaPeso = em.merge(categoriaPeso);
            for (Categoria categoriaListNewCategoria : categoriaListNew) {
                if (!categoriaListOld.contains(categoriaListNewCategoria)) {
                    CategoriaPeso oldIdCategoriaPesoOfCategoriaListNewCategoria = categoriaListNewCategoria.getIdCategoriaPeso();
                    categoriaListNewCategoria.setIdCategoriaPeso(categoriaPeso);
                    categoriaListNewCategoria = em.merge(categoriaListNewCategoria);
                    if (oldIdCategoriaPesoOfCategoriaListNewCategoria != null && !oldIdCategoriaPesoOfCategoriaListNewCategoria.equals(categoriaPeso)) {
                        oldIdCategoriaPesoOfCategoriaListNewCategoria.getCategoriaList().remove(categoriaListNewCategoria);
                        oldIdCategoriaPesoOfCategoriaListNewCategoria = em.merge(oldIdCategoriaPesoOfCategoriaListNewCategoria);
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
                Integer id = categoriaPeso.getIdCategoriaPeso();
                if (findCategoriaPeso(id) == null) {
                    throw new NonexistentEntityException("The categoriaPeso with id " + id + " no longer exists.");
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
            CategoriaPeso categoriaPeso;
            try {
                categoriaPeso = em.getReference(CategoriaPeso.class, id);
                categoriaPeso.getIdCategoriaPeso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaPeso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Categoria> categoriaListOrphanCheck = categoriaPeso.getCategoriaList();
            for (Categoria categoriaListOrphanCheckCategoria : categoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CategoriaPeso (" + categoriaPeso + ") cannot be destroyed since the Categoria " + categoriaListOrphanCheckCategoria + " in its categoriaList field has a non-nullable idCategoriaPeso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(categoriaPeso);
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

    public List<CategoriaPeso> findCategoriaPesoEntities() {
        return findCategoriaPesoEntities(true, -1, -1);
    }

    public List<CategoriaPeso> findCategoriaPesoEntities(int maxResults, int firstResult) {
        return findCategoriaPesoEntities(false, maxResults, firstResult);
    }

    private List<CategoriaPeso> findCategoriaPesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaPeso.class));
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

    public CategoriaPeso findCategoriaPeso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaPeso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaPesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaPeso> rt = cq.from(CategoriaPeso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
