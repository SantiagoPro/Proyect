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
import com.rid.modelo.entities.PorcentajeCarga;
import java.util.ArrayList;
import java.util.List;
import com.rid.modelo.entities.Rms;
import com.rid.modelo.entities.TipoEntrenamiento;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class TipoEntrenamientoJpaController implements Serializable {

    public TipoEntrenamientoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEntrenamiento tipoEntrenamiento) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tipoEntrenamiento.getPorcentajeCargaList() == null) {
            tipoEntrenamiento.setPorcentajeCargaList(new ArrayList<PorcentajeCarga>());
        }
        if (tipoEntrenamiento.getRmsList() == null) {
            tipoEntrenamiento.setRmsList(new ArrayList<Rms>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<PorcentajeCarga> attachedPorcentajeCargaList = new ArrayList<PorcentajeCarga>();
            for (PorcentajeCarga porcentajeCargaListPorcentajeCargaToAttach : tipoEntrenamiento.getPorcentajeCargaList()) {
                porcentajeCargaListPorcentajeCargaToAttach = em.getReference(porcentajeCargaListPorcentajeCargaToAttach.getClass(), porcentajeCargaListPorcentajeCargaToAttach.getIdPorcentaje());
                attachedPorcentajeCargaList.add(porcentajeCargaListPorcentajeCargaToAttach);
            }
            tipoEntrenamiento.setPorcentajeCargaList(attachedPorcentajeCargaList);
            List<Rms> attachedRmsList = new ArrayList<Rms>();
            for (Rms rmsListRmsToAttach : tipoEntrenamiento.getRmsList()) {
                rmsListRmsToAttach = em.getReference(rmsListRmsToAttach.getClass(), rmsListRmsToAttach.getIdRms());
                attachedRmsList.add(rmsListRmsToAttach);
            }
            tipoEntrenamiento.setRmsList(attachedRmsList);
            em.persist(tipoEntrenamiento);
            for (PorcentajeCarga porcentajeCargaListPorcentajeCarga : tipoEntrenamiento.getPorcentajeCargaList()) {
                TipoEntrenamiento oldIdTipoEntrenamientoOfPorcentajeCargaListPorcentajeCarga = porcentajeCargaListPorcentajeCarga.getIdTipoEntrenamiento();
                porcentajeCargaListPorcentajeCarga.setIdTipoEntrenamiento(tipoEntrenamiento);
                porcentajeCargaListPorcentajeCarga = em.merge(porcentajeCargaListPorcentajeCarga);
                if (oldIdTipoEntrenamientoOfPorcentajeCargaListPorcentajeCarga != null) {
                    oldIdTipoEntrenamientoOfPorcentajeCargaListPorcentajeCarga.getPorcentajeCargaList().remove(porcentajeCargaListPorcentajeCarga);
                    oldIdTipoEntrenamientoOfPorcentajeCargaListPorcentajeCarga = em.merge(oldIdTipoEntrenamientoOfPorcentajeCargaListPorcentajeCarga);
                }
            }
            for (Rms rmsListRms : tipoEntrenamiento.getRmsList()) {
                TipoEntrenamiento oldIdTipoEntrenamientoOfRmsListRms = rmsListRms.getIdTipoEntrenamiento();
                rmsListRms.setIdTipoEntrenamiento(tipoEntrenamiento);
                rmsListRms = em.merge(rmsListRms);
                if (oldIdTipoEntrenamientoOfRmsListRms != null) {
                    oldIdTipoEntrenamientoOfRmsListRms.getRmsList().remove(rmsListRms);
                    oldIdTipoEntrenamientoOfRmsListRms = em.merge(oldIdTipoEntrenamientoOfRmsListRms);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTipoEntrenamiento(tipoEntrenamiento.getIdTipoEntrenamiento()) != null) {
                throw new PreexistingEntityException("TipoEntrenamiento " + tipoEntrenamiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEntrenamiento tipoEntrenamiento) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoEntrenamiento persistentTipoEntrenamiento = em.find(TipoEntrenamiento.class, tipoEntrenamiento.getIdTipoEntrenamiento());
            List<PorcentajeCarga> porcentajeCargaListOld = persistentTipoEntrenamiento.getPorcentajeCargaList();
            List<PorcentajeCarga> porcentajeCargaListNew = tipoEntrenamiento.getPorcentajeCargaList();
            List<Rms> rmsListOld = persistentTipoEntrenamiento.getRmsList();
            List<Rms> rmsListNew = tipoEntrenamiento.getRmsList();
            List<String> illegalOrphanMessages = null;
            for (PorcentajeCarga porcentajeCargaListOldPorcentajeCarga : porcentajeCargaListOld) {
                if (!porcentajeCargaListNew.contains(porcentajeCargaListOldPorcentajeCarga)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PorcentajeCarga " + porcentajeCargaListOldPorcentajeCarga + " since its idTipoEntrenamiento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PorcentajeCarga> attachedPorcentajeCargaListNew = new ArrayList<PorcentajeCarga>();
            for (PorcentajeCarga porcentajeCargaListNewPorcentajeCargaToAttach : porcentajeCargaListNew) {
                porcentajeCargaListNewPorcentajeCargaToAttach = em.getReference(porcentajeCargaListNewPorcentajeCargaToAttach.getClass(), porcentajeCargaListNewPorcentajeCargaToAttach.getIdPorcentaje());
                attachedPorcentajeCargaListNew.add(porcentajeCargaListNewPorcentajeCargaToAttach);
            }
            porcentajeCargaListNew = attachedPorcentajeCargaListNew;
            tipoEntrenamiento.setPorcentajeCargaList(porcentajeCargaListNew);
            List<Rms> attachedRmsListNew = new ArrayList<Rms>();
            for (Rms rmsListNewRmsToAttach : rmsListNew) {
                rmsListNewRmsToAttach = em.getReference(rmsListNewRmsToAttach.getClass(), rmsListNewRmsToAttach.getIdRms());
                attachedRmsListNew.add(rmsListNewRmsToAttach);
            }
            rmsListNew = attachedRmsListNew;
            tipoEntrenamiento.setRmsList(rmsListNew);
            tipoEntrenamiento = em.merge(tipoEntrenamiento);
            for (PorcentajeCarga porcentajeCargaListNewPorcentajeCarga : porcentajeCargaListNew) {
                if (!porcentajeCargaListOld.contains(porcentajeCargaListNewPorcentajeCarga)) {
                    TipoEntrenamiento oldIdTipoEntrenamientoOfPorcentajeCargaListNewPorcentajeCarga = porcentajeCargaListNewPorcentajeCarga.getIdTipoEntrenamiento();
                    porcentajeCargaListNewPorcentajeCarga.setIdTipoEntrenamiento(tipoEntrenamiento);
                    porcentajeCargaListNewPorcentajeCarga = em.merge(porcentajeCargaListNewPorcentajeCarga);
                    if (oldIdTipoEntrenamientoOfPorcentajeCargaListNewPorcentajeCarga != null && !oldIdTipoEntrenamientoOfPorcentajeCargaListNewPorcentajeCarga.equals(tipoEntrenamiento)) {
                        oldIdTipoEntrenamientoOfPorcentajeCargaListNewPorcentajeCarga.getPorcentajeCargaList().remove(porcentajeCargaListNewPorcentajeCarga);
                        oldIdTipoEntrenamientoOfPorcentajeCargaListNewPorcentajeCarga = em.merge(oldIdTipoEntrenamientoOfPorcentajeCargaListNewPorcentajeCarga);
                    }
                }
            }
            for (Rms rmsListOldRms : rmsListOld) {
                if (!rmsListNew.contains(rmsListOldRms)) {
                    rmsListOldRms.setIdTipoEntrenamiento(null);
                    rmsListOldRms = em.merge(rmsListOldRms);
                }
            }
            for (Rms rmsListNewRms : rmsListNew) {
                if (!rmsListOld.contains(rmsListNewRms)) {
                    TipoEntrenamiento oldIdTipoEntrenamientoOfRmsListNewRms = rmsListNewRms.getIdTipoEntrenamiento();
                    rmsListNewRms.setIdTipoEntrenamiento(tipoEntrenamiento);
                    rmsListNewRms = em.merge(rmsListNewRms);
                    if (oldIdTipoEntrenamientoOfRmsListNewRms != null && !oldIdTipoEntrenamientoOfRmsListNewRms.equals(tipoEntrenamiento)) {
                        oldIdTipoEntrenamientoOfRmsListNewRms.getRmsList().remove(rmsListNewRms);
                        oldIdTipoEntrenamientoOfRmsListNewRms = em.merge(oldIdTipoEntrenamientoOfRmsListNewRms);
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
                Integer id = tipoEntrenamiento.getIdTipoEntrenamiento();
                if (findTipoEntrenamiento(id) == null) {
                    throw new NonexistentEntityException("The tipoEntrenamiento with id " + id + " no longer exists.");
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
            TipoEntrenamiento tipoEntrenamiento;
            try {
                tipoEntrenamiento = em.getReference(TipoEntrenamiento.class, id);
                tipoEntrenamiento.getIdTipoEntrenamiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEntrenamiento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PorcentajeCarga> porcentajeCargaListOrphanCheck = tipoEntrenamiento.getPorcentajeCargaList();
            for (PorcentajeCarga porcentajeCargaListOrphanCheckPorcentajeCarga : porcentajeCargaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoEntrenamiento (" + tipoEntrenamiento + ") cannot be destroyed since the PorcentajeCarga " + porcentajeCargaListOrphanCheckPorcentajeCarga + " in its porcentajeCargaList field has a non-nullable idTipoEntrenamiento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Rms> rmsList = tipoEntrenamiento.getRmsList();
            for (Rms rmsListRms : rmsList) {
                rmsListRms.setIdTipoEntrenamiento(null);
                rmsListRms = em.merge(rmsListRms);
            }
            em.remove(tipoEntrenamiento);
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

    public List<TipoEntrenamiento> findTipoEntrenamientoEntities() {
        return findTipoEntrenamientoEntities(true, -1, -1);
    }

    public List<TipoEntrenamiento> findTipoEntrenamientoEntities(int maxResults, int firstResult) {
        return findTipoEntrenamientoEntities(false, maxResults, firstResult);
    }

    private List<TipoEntrenamiento> findTipoEntrenamientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEntrenamiento.class));
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

    public TipoEntrenamiento findTipoEntrenamiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEntrenamiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEntrenamientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEntrenamiento> rt = cq.from(TipoEntrenamiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
