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
import com.rid.modelo.entities.Deportista;
import com.rid.modelo.entities.Administrador;
import com.rid.modelo.entities.Entrenador;
import com.rid.modelo.entities.TipoDocumento;
import com.rid.modelo.entities.Rol;
import com.rid.modelo.entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author karen
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Deportista deportista = usuario.getDeportista();
            if (deportista != null) {
                deportista = em.getReference(deportista.getClass(), deportista.getIdDeportista());
                usuario.setDeportista(deportista);
            }
            Administrador administrador = usuario.getAdministrador();
            if (administrador != null) {
                administrador = em.getReference(administrador.getClass(), administrador.getIdAdministrador());
                usuario.setAdministrador(administrador);
            }
            Entrenador entrenador = usuario.getEntrenador();
            if (entrenador != null) {
                entrenador = em.getReference(entrenador.getClass(), entrenador.getIdEntrenador());
                usuario.setEntrenador(entrenador);
            }
            TipoDocumento idTipoDocumento = usuario.getIdTipoDocumento();
            if (idTipoDocumento != null) {
                idTipoDocumento = em.getReference(idTipoDocumento.getClass(), idTipoDocumento.getIdTipoDocumento());
                usuario.setIdTipoDocumento(idTipoDocumento);
            }
            Rol idRol = usuario.getIdRol();
            if (idRol != null) {
                idRol = em.getReference(idRol.getClass(), idRol.getIdRol());
                usuario.setIdRol(idRol);
            }
            em.persist(usuario);
            if (deportista != null) {
                Usuario oldUsuarioOfDeportista = deportista.getUsuario();
                if (oldUsuarioOfDeportista != null) {
                    oldUsuarioOfDeportista.setDeportista(null);
                    oldUsuarioOfDeportista = em.merge(oldUsuarioOfDeportista);
                }
                deportista.setUsuario(usuario);
                deportista = em.merge(deportista);
            }
            if (administrador != null) {
                Usuario oldUsuarioOfAdministrador = administrador.getUsuario();
                if (oldUsuarioOfAdministrador != null) {
                    oldUsuarioOfAdministrador.setAdministrador(null);
                    oldUsuarioOfAdministrador = em.merge(oldUsuarioOfAdministrador);
                }
                administrador.setUsuario(usuario);
                administrador = em.merge(administrador);
            }
            if (entrenador != null) {
                Usuario oldUsuarioOfEntrenador = entrenador.getUsuario();
                if (oldUsuarioOfEntrenador != null) {
                    oldUsuarioOfEntrenador.setEntrenador(null);
                    oldUsuarioOfEntrenador = em.merge(oldUsuarioOfEntrenador);
                }
                entrenador.setUsuario(usuario);
                entrenador = em.merge(entrenador);
            }
            if (idTipoDocumento != null) {
                idTipoDocumento.getUsuarioList().add(usuario);
                idTipoDocumento = em.merge(idTipoDocumento);
            }
            if (idRol != null) {
                idRol.getUsuarioList().add(usuario);
                idRol = em.merge(idRol);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuario(usuario.getIdUsuarios()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuarios());
            Deportista deportistaOld = persistentUsuario.getDeportista();
            Deportista deportistaNew = usuario.getDeportista();
            Administrador administradorOld = persistentUsuario.getAdministrador();
            Administrador administradorNew = usuario.getAdministrador();
            Entrenador entrenadorOld = persistentUsuario.getEntrenador();
            Entrenador entrenadorNew = usuario.getEntrenador();
            TipoDocumento idTipoDocumentoOld = persistentUsuario.getIdTipoDocumento();
            TipoDocumento idTipoDocumentoNew = usuario.getIdTipoDocumento();
            Rol idRolOld = persistentUsuario.getIdRol();
            Rol idRolNew = usuario.getIdRol();
            List<String> illegalOrphanMessages = null;
            if (deportistaOld != null && !deportistaOld.equals(deportistaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Deportista " + deportistaOld + " since its usuario field is not nullable.");
            }
            if (administradorOld != null && !administradorOld.equals(administradorNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Administrador " + administradorOld + " since its usuario field is not nullable.");
            }
            if (entrenadorOld != null && !entrenadorOld.equals(entrenadorNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Entrenador " + entrenadorOld + " since its usuario field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (deportistaNew != null) {
                deportistaNew = em.getReference(deportistaNew.getClass(), deportistaNew.getIdDeportista());
                usuario.setDeportista(deportistaNew);
            }
            if (administradorNew != null) {
                administradorNew = em.getReference(administradorNew.getClass(), administradorNew.getIdAdministrador());
                usuario.setAdministrador(administradorNew);
            }
            if (entrenadorNew != null) {
                entrenadorNew = em.getReference(entrenadorNew.getClass(), entrenadorNew.getIdEntrenador());
                usuario.setEntrenador(entrenadorNew);
            }
            if (idTipoDocumentoNew != null) {
                idTipoDocumentoNew = em.getReference(idTipoDocumentoNew.getClass(), idTipoDocumentoNew.getIdTipoDocumento());
                usuario.setIdTipoDocumento(idTipoDocumentoNew);
            }
            if (idRolNew != null) {
                idRolNew = em.getReference(idRolNew.getClass(), idRolNew.getIdRol());
                usuario.setIdRol(idRolNew);
            }
            usuario = em.merge(usuario);
            if (deportistaNew != null && !deportistaNew.equals(deportistaOld)) {
                Usuario oldUsuarioOfDeportista = deportistaNew.getUsuario();
                if (oldUsuarioOfDeportista != null) {
                    oldUsuarioOfDeportista.setDeportista(null);
                    oldUsuarioOfDeportista = em.merge(oldUsuarioOfDeportista);
                }
                deportistaNew.setUsuario(usuario);
                deportistaNew = em.merge(deportistaNew);
            }
            if (administradorNew != null && !administradorNew.equals(administradorOld)) {
                Usuario oldUsuarioOfAdministrador = administradorNew.getUsuario();
                if (oldUsuarioOfAdministrador != null) {
                    oldUsuarioOfAdministrador.setAdministrador(null);
                    oldUsuarioOfAdministrador = em.merge(oldUsuarioOfAdministrador);
                }
                administradorNew.setUsuario(usuario);
                administradorNew = em.merge(administradorNew);
            }
            if (entrenadorNew != null && !entrenadorNew.equals(entrenadorOld)) {
                Usuario oldUsuarioOfEntrenador = entrenadorNew.getUsuario();
                if (oldUsuarioOfEntrenador != null) {
                    oldUsuarioOfEntrenador.setEntrenador(null);
                    oldUsuarioOfEntrenador = em.merge(oldUsuarioOfEntrenador);
                }
                entrenadorNew.setUsuario(usuario);
                entrenadorNew = em.merge(entrenadorNew);
            }
            if (idTipoDocumentoOld != null && !idTipoDocumentoOld.equals(idTipoDocumentoNew)) {
                idTipoDocumentoOld.getUsuarioList().remove(usuario);
                idTipoDocumentoOld = em.merge(idTipoDocumentoOld);
            }
            if (idTipoDocumentoNew != null && !idTipoDocumentoNew.equals(idTipoDocumentoOld)) {
                idTipoDocumentoNew.getUsuarioList().add(usuario);
                idTipoDocumentoNew = em.merge(idTipoDocumentoNew);
            }
            if (idRolOld != null && !idRolOld.equals(idRolNew)) {
                idRolOld.getUsuarioList().remove(usuario);
                idRolOld = em.merge(idRolOld);
            }
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                idRolNew.getUsuarioList().add(usuario);
                idRolNew = em.merge(idRolNew);
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
                Long id = usuario.getIdUsuarios();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuarios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Deportista deportistaOrphanCheck = usuario.getDeportista();
            if (deportistaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Deportista " + deportistaOrphanCheck + " in its deportista field has a non-nullable usuario field.");
            }
            Administrador administradorOrphanCheck = usuario.getAdministrador();
            if (administradorOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Administrador " + administradorOrphanCheck + " in its administrador field has a non-nullable usuario field.");
            }
            Entrenador entrenadorOrphanCheck = usuario.getEntrenador();
            if (entrenadorOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Entrenador " + entrenadorOrphanCheck + " in its entrenador field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoDocumento idTipoDocumento = usuario.getIdTipoDocumento();
            if (idTipoDocumento != null) {
                idTipoDocumento.getUsuarioList().remove(usuario);
                idTipoDocumento = em.merge(idTipoDocumento);
            }
            Rol idRol = usuario.getIdRol();
            if (idRol != null) {
                idRol.getUsuarioList().remove(usuario);
                idRol = em.merge(idRol);
            }
            em.remove(usuario);
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

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
