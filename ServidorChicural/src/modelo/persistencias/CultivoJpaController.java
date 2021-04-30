/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.persistencias;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.entities.Ordencompra;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.entities.Cultivo;
import modelo.persistencias.exceptions.IllegalOrphanException;
import modelo.persistencias.exceptions.NonexistentEntityException;

/**
 *
 * @author Lenovo
 */
public class CultivoJpaController implements Serializable {

    public CultivoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ChicuralPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cultivo cultivo) {
        if (cultivo.getOrdencompraCollection() == null) {
            cultivo.setOrdencompraCollection(new ArrayList<Ordencompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Ordencompra> attachedOrdencompraCollection = new ArrayList<Ordencompra>();
            for (Ordencompra ordencompraCollectionOrdencompraToAttach : cultivo.getOrdencompraCollection()) {
                ordencompraCollectionOrdencompraToAttach = em.getReference(ordencompraCollectionOrdencompraToAttach.getClass(), ordencompraCollectionOrdencompraToAttach.getIdordenCompra());
                attachedOrdencompraCollection.add(ordencompraCollectionOrdencompraToAttach);
            }
            cultivo.setOrdencompraCollection(attachedOrdencompraCollection);
            em.persist(cultivo);
            for (Ordencompra ordencompraCollectionOrdencompra : cultivo.getOrdencompraCollection()) {
                Cultivo oldCultivoOfOrdencompraCollectionOrdencompra = ordencompraCollectionOrdencompra.getCultivo();
                ordencompraCollectionOrdencompra.setCultivo(cultivo);
                ordencompraCollectionOrdencompra = em.merge(ordencompraCollectionOrdencompra);
                if (oldCultivoOfOrdencompraCollectionOrdencompra != null) {
                    oldCultivoOfOrdencompraCollectionOrdencompra.getOrdencompraCollection().remove(ordencompraCollectionOrdencompra);
                    oldCultivoOfOrdencompraCollectionOrdencompra = em.merge(oldCultivoOfOrdencompraCollectionOrdencompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cultivo cultivo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cultivo persistentCultivo = em.find(Cultivo.class, cultivo.getIdcultivo());
            Collection<Ordencompra> ordencompraCollectionOld = persistentCultivo.getOrdencompraCollection();
            Collection<Ordencompra> ordencompraCollectionNew = cultivo.getOrdencompraCollection();
            List<String> illegalOrphanMessages = null;
            for (Ordencompra ordencompraCollectionOldOrdencompra : ordencompraCollectionOld) {
                if (!ordencompraCollectionNew.contains(ordencompraCollectionOldOrdencompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ordencompra " + ordencompraCollectionOldOrdencompra + " since its cultivo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Ordencompra> attachedOrdencompraCollectionNew = new ArrayList<Ordencompra>();
            for (Ordencompra ordencompraCollectionNewOrdencompraToAttach : ordencompraCollectionNew) {
                ordencompraCollectionNewOrdencompraToAttach = em.getReference(ordencompraCollectionNewOrdencompraToAttach.getClass(), ordencompraCollectionNewOrdencompraToAttach.getIdordenCompra());
                attachedOrdencompraCollectionNew.add(ordencompraCollectionNewOrdencompraToAttach);
            }
            ordencompraCollectionNew = attachedOrdencompraCollectionNew;
            cultivo.setOrdencompraCollection(ordencompraCollectionNew);
            cultivo = em.merge(cultivo);
            for (Ordencompra ordencompraCollectionNewOrdencompra : ordencompraCollectionNew) {
                if (!ordencompraCollectionOld.contains(ordencompraCollectionNewOrdencompra)) {
                    Cultivo oldCultivoOfOrdencompraCollectionNewOrdencompra = ordencompraCollectionNewOrdencompra.getCultivo();
                    ordencompraCollectionNewOrdencompra.setCultivo(cultivo);
                    ordencompraCollectionNewOrdencompra = em.merge(ordencompraCollectionNewOrdencompra);
                    if (oldCultivoOfOrdencompraCollectionNewOrdencompra != null && !oldCultivoOfOrdencompraCollectionNewOrdencompra.equals(cultivo)) {
                        oldCultivoOfOrdencompraCollectionNewOrdencompra.getOrdencompraCollection().remove(ordencompraCollectionNewOrdencompra);
                        oldCultivoOfOrdencompraCollectionNewOrdencompra = em.merge(oldCultivoOfOrdencompraCollectionNewOrdencompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cultivo.getIdcultivo();
                if (findCultivo(id) == null) {
                    throw new NonexistentEntityException("The cultivo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cultivo cultivo;
            try {
                cultivo = em.getReference(Cultivo.class, id);
                cultivo.getIdcultivo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cultivo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Ordencompra> ordencompraCollectionOrphanCheck = cultivo.getOrdencompraCollection();
            for (Ordencompra ordencompraCollectionOrphanCheckOrdencompra : ordencompraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cultivo (" + cultivo + ") cannot be destroyed since the Ordencompra " + ordencompraCollectionOrphanCheckOrdencompra + " in its ordencompraCollection field has a non-nullable cultivo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cultivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cultivo> findCultivoEntities() {
        return findCultivoEntities(true, -1, -1);
    }

    public List<Cultivo> findCultivoEntities(int maxResults, int firstResult) {
        return findCultivoEntities(false, maxResults, firstResult);
    }

    private List<Cultivo> findCultivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cultivo.class));
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

    public Cultivo findCultivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cultivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCultivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cultivo> rt = cq.from(Cultivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
