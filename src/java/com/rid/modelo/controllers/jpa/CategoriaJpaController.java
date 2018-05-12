/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.jpa;

import com.rid.modelo.controllers.jpa.exceptions.NonexistentEntityException;
import com.rid.modelo.controllers.jpa.exceptions.RollbackFailureException;
import com.rid.modelo.entities.Categoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rid.modelo.entities.CategoriaEdad;
import com.rid.modelo.entities.CategoriaPeso;
import com.rid.modelo.entities.CategoriaDeportista;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) throws RollbackFailureException, Exception {
        if (categoria.getCategoriaDeportistaList() == null) {
            categoria.setCategoriaDeportistaList(new ArrayList<CategoriaDeportista>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoriaEdad idCategoriaEdad = categoria.getIdCategoriaEdad();
            if (idCategoriaEdad != null) {
                idCategoriaEdad = em.getReference(idCategoriaEdad.getClass(), idCategoriaEdad.getIdCategoriaEdad());
                categoria.setIdCategoriaEdad(idCategoriaEdad);
            }
            CategoriaPeso idCategoriaPeso = categoria.getIdCategoriaPeso();
            if (idCategoriaPeso != null) {
                idCategoriaPeso = em.getReference(idCategoriaPeso.getClass(), idCategoriaPeso.getIdCategoriaPeso());
                categoria.setIdCategoriaPeso(idCategoriaPeso);
            }
            List<CategoriaDeportista> attachedCategoriaDeportistaList = new ArrayList<CategoriaDeportista>();
            for (CategoriaDeportista categoriaDeportistaListCategoriaDeportistaToAttach : categoria.getCategoriaDeportistaList()) {
                categoriaDeportistaListCategoriaDeportistaToAttach = em.getReference(categoriaDeportistaListCategoriaDeportistaToAttach.getClass(), categoriaDeportistaListCategoriaDeportistaToAttach.getIdCategoriaDeportista());
                attachedCategoriaDeportistaList.add(categoriaDeportistaListCategoriaDeportistaToAttach);
            }
            categoria.setCategoriaDeportistaList(attachedCategoriaDeportistaList);
            em.persist(categoria);
            if (idCategoriaEdad != null) {
                idCategoriaEdad.getCategoriaList().add(categoria);
                idCategoriaEdad = em.merge(idCategoriaEdad);
            }
            if (idCategoriaPeso != null) {
                idCategoriaPeso.getCategoriaList().add(categoria);
                idCategoriaPeso = em.merge(idCategoriaPeso);
            }
            for (CategoriaDeportista categoriaDeportistaListCategoriaDeportista : categoria.getCategoriaDeportistaList()) {
                Categoria oldIdCategoriaOfCategoriaDeportistaListCategoriaDeportista = categoriaDeportistaListCategoriaDeportista.getIdCategoria();
                categoriaDeportistaListCategoriaDeportista.setIdCategoria(categoria);
                categoriaDeportistaListCategoriaDeportista = em.merge(categoriaDeportistaListCategoriaDeportista);
                if (oldIdCategoriaOfCategoriaDeportistaListCategoriaDeportista != null) {
                    oldIdCategoriaOfCategoriaDeportistaListCategoriaDeportista.getCategoriaDeportistaList().remove(categoriaDeportistaListCategoriaDeportista);
                    oldIdCategoriaOfCategoriaDeportistaListCategoriaDeportista = em.merge(oldIdCategoriaOfCategoriaDeportistaListCategoriaDeportista);
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

    public void edit(Categoria categoria) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getIdCategoria());
            CategoriaEdad idCategoriaEdadOld = persistentCategoria.getIdCategoriaEdad();
            CategoriaEdad idCategoriaEdadNew = categoria.getIdCategoriaEdad();
            CategoriaPeso idCategoriaPesoOld = persistentCategoria.getIdCategoriaPeso();
            CategoriaPeso idCategoriaPesoNew = categoria.getIdCategoriaPeso();
            List<CategoriaDeportista> categoriaDeportistaListOld = persistentCategoria.getCategoriaDeportistaList();
            List<CategoriaDeportista> categoriaDeportistaListNew = categoria.getCategoriaDeportistaList();
            if (idCategoriaEdadNew != null) {
                idCategoriaEdadNew = em.getReference(idCategoriaEdadNew.getClass(), idCategoriaEdadNew.getIdCategoriaEdad());
                categoria.setIdCategoriaEdad(idCategoriaEdadNew);
            }
            if (idCategoriaPesoNew != null) {
                idCategoriaPesoNew = em.getReference(idCategoriaPesoNew.getClass(), idCategoriaPesoNew.getIdCategoriaPeso());
                categoria.setIdCategoriaPeso(idCategoriaPesoNew);
            }
            List<CategoriaDeportista> attachedCategoriaDeportistaListNew = new ArrayList<CategoriaDeportista>();
            for (CategoriaDeportista categoriaDeportistaListNewCategoriaDeportistaToAttach : categoriaDeportistaListNew) {
                categoriaDeportistaListNewCategoriaDeportistaToAttach = em.getReference(categoriaDeportistaListNewCategoriaDeportistaToAttach.getClass(), categoriaDeportistaListNewCategoriaDeportistaToAttach.getIdCategoriaDeportista());
                attachedCategoriaDeportistaListNew.add(categoriaDeportistaListNewCategoriaDeportistaToAttach);
            }
            categoriaDeportistaListNew = attachedCategoriaDeportistaListNew;
            categoria.setCategoriaDeportistaList(categoriaDeportistaListNew);
            categoria = em.merge(categoria);
            if (idCategoriaEdadOld != null && !idCategoriaEdadOld.equals(idCategoriaEdadNew)) {
                idCategoriaEdadOld.getCategoriaList().remove(categoria);
                idCategoriaEdadOld = em.merge(idCategoriaEdadOld);
            }
            if (idCategoriaEdadNew != null && !idCategoriaEdadNew.equals(idCategoriaEdadOld)) {
                idCategoriaEdadNew.getCategoriaList().add(categoria);
                idCategoriaEdadNew = em.merge(idCategoriaEdadNew);
            }
            if (idCategoriaPesoOld != null && !idCategoriaPesoOld.equals(idCategoriaPesoNew)) {
                idCategoriaPesoOld.getCategoriaList().remove(categoria);
                idCategoriaPesoOld = em.merge(idCategoriaPesoOld);
            }
            if (idCategoriaPesoNew != null && !idCategoriaPesoNew.equals(idCategoriaPesoOld)) {
                idCategoriaPesoNew.getCategoriaList().add(categoria);
                idCategoriaPesoNew = em.merge(idCategoriaPesoNew);
            }
            for (CategoriaDeportista categoriaDeportistaListOldCategoriaDeportista : categoriaDeportistaListOld) {
                if (!categoriaDeportistaListNew.contains(categoriaDeportistaListOldCategoriaDeportista)) {
                    categoriaDeportistaListOldCategoriaDeportista.setIdCategoria(null);
                    categoriaDeportistaListOldCategoriaDeportista = em.merge(categoriaDeportistaListOldCategoriaDeportista);
                }
            }
            for (CategoriaDeportista categoriaDeportistaListNewCategoriaDeportista : categoriaDeportistaListNew) {
                if (!categoriaDeportistaListOld.contains(categoriaDeportistaListNewCategoriaDeportista)) {
                    Categoria oldIdCategoriaOfCategoriaDeportistaListNewCategoriaDeportista = categoriaDeportistaListNewCategoriaDeportista.getIdCategoria();
                    categoriaDeportistaListNewCategoriaDeportista.setIdCategoria(categoria);
                    categoriaDeportistaListNewCategoriaDeportista = em.merge(categoriaDeportistaListNewCategoriaDeportista);
                    if (oldIdCategoriaOfCategoriaDeportistaListNewCategoriaDeportista != null && !oldIdCategoriaOfCategoriaDeportistaListNewCategoriaDeportista.equals(categoria)) {
                        oldIdCategoriaOfCategoriaDeportistaListNewCategoriaDeportista.getCategoriaDeportistaList().remove(categoriaDeportistaListNewCategoriaDeportista);
                        oldIdCategoriaOfCategoriaDeportistaListNewCategoriaDeportista = em.merge(oldIdCategoriaOfCategoriaDeportistaListNewCategoriaDeportista);
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
                Integer id = categoria.getIdCategoria();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
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
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getIdCategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            CategoriaEdad idCategoriaEdad = categoria.getIdCategoriaEdad();
            if (idCategoriaEdad != null) {
                idCategoriaEdad.getCategoriaList().remove(categoria);
                idCategoriaEdad = em.merge(idCategoriaEdad);
            }
            CategoriaPeso idCategoriaPeso = categoria.getIdCategoriaPeso();
            if (idCategoriaPeso != null) {
                idCategoriaPeso.getCategoriaList().remove(categoria);
                idCategoriaPeso = em.merge(idCategoriaPeso);
            }
            List<CategoriaDeportista> categoriaDeportistaList = categoria.getCategoriaDeportistaList();
            for (CategoriaDeportista categoriaDeportistaListCategoriaDeportista : categoriaDeportistaList) {
                categoriaDeportistaListCategoriaDeportista.setIdCategoria(null);
                categoriaDeportistaListCategoriaDeportista = em.merge(categoriaDeportistaListCategoriaDeportista);
            }
            em.remove(categoria);
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

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
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

    public Categoria findCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
