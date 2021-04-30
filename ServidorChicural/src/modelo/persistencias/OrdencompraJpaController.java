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
import modelo.entities.Cultivo;
import modelo.entities.Proveedor;
import modelo.entities.Productos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.entities.Ordencompra;
import modelo.persistencias.exceptions.IllegalOrphanException;
import modelo.persistencias.exceptions.NonexistentEntityException;

/**
 *
 * @author Lenovo
 */
public class OrdencompraJpaController implements Serializable {

    public OrdencompraJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ChicuralPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ordencompra ordencompra) {
        if (ordencompra.getProductosCollection() == null) {
            ordencompra.setProductosCollection(new ArrayList<Productos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cultivo cultivo = ordencompra.getCultivo();
            if (cultivo != null) {
                cultivo = em.getReference(cultivo.getClass(), cultivo.getIdcultivo());
                ordencompra.setCultivo(cultivo);
            }
            Proveedor proveedor = ordencompra.getProveedor();
            if (proveedor != null) {
                proveedor = em.getReference(proveedor.getClass(), proveedor.getIdProveedor());
                ordencompra.setProveedor(proveedor);
            }
            Collection<Productos> attachedProductosCollection = new ArrayList<Productos>();
            for (Productos productosCollectionProductosToAttach : ordencompra.getProductosCollection()) {
                productosCollectionProductosToAttach = em.getReference(productosCollectionProductosToAttach.getClass(), productosCollectionProductosToAttach.getIdProductos());
                attachedProductosCollection.add(productosCollectionProductosToAttach);
            }
            ordencompra.setProductosCollection(attachedProductosCollection);
            em.persist(ordencompra);
            if (cultivo != null) {
                cultivo.getOrdencompraCollection().add(ordencompra);
                cultivo = em.merge(cultivo);
            }
            if (proveedor != null) {
                proveedor.getOrdencompraCollection().add(ordencompra);
                proveedor = em.merge(proveedor);
            }
            for (Productos productosCollectionProductos : ordencompra.getProductosCollection()) {
                Ordencompra oldIdOrdenCompraOfProductosCollectionProductos = productosCollectionProductos.getIdOrdenCompra();
                productosCollectionProductos.setIdOrdenCompra(ordencompra);
                productosCollectionProductos = em.merge(productosCollectionProductos);
                if (oldIdOrdenCompraOfProductosCollectionProductos != null) {
                    oldIdOrdenCompraOfProductosCollectionProductos.getProductosCollection().remove(productosCollectionProductos);
                    oldIdOrdenCompraOfProductosCollectionProductos = em.merge(oldIdOrdenCompraOfProductosCollectionProductos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ordencompra ordencompra) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordencompra persistentOrdencompra = em.find(Ordencompra.class, ordencompra.getIdordenCompra());
            Cultivo cultivoOld = persistentOrdencompra.getCultivo();
            Cultivo cultivoNew = ordencompra.getCultivo();
            Proveedor proveedorOld = persistentOrdencompra.getProveedor();
            Proveedor proveedorNew = ordencompra.getProveedor();
            Collection<Productos> productosCollectionOld = persistentOrdencompra.getProductosCollection();
            Collection<Productos> productosCollectionNew = ordencompra.getProductosCollection();
            List<String> illegalOrphanMessages = null;
           
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cultivoNew != null) {
                cultivoNew = em.getReference(cultivoNew.getClass(), cultivoNew.getIdcultivo());
                ordencompra.setCultivo(cultivoNew);
            }
            if (proveedorNew != null) {
                proveedorNew = em.getReference(proveedorNew.getClass(), proveedorNew.getIdProveedor());
                ordencompra.setProveedor(proveedorNew);
            }
            
            ordencompra = em.merge(ordencompra);
            if (cultivoOld != null && !cultivoOld.equals(cultivoNew)) {
                cultivoOld.getOrdencompraCollection().remove(ordencompra);
                cultivoOld = em.merge(cultivoOld);
            }
            if (cultivoNew != null && !cultivoNew.equals(cultivoOld)) {
                cultivoNew.getOrdencompraCollection().add(ordencompra);
                cultivoNew = em.merge(cultivoNew);
            }
            if (proveedorOld != null && !proveedorOld.equals(proveedorNew)) {
                proveedorOld.getOrdencompraCollection().remove(ordencompra);
                proveedorOld = em.merge(proveedorOld);
            }
            if (proveedorNew != null && !proveedorNew.equals(proveedorOld)) {
                proveedorNew.getOrdencompraCollection().add(ordencompra);
                proveedorNew = em.merge(proveedorNew);
            }
            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordencompra.getIdordenCompra();
                if (findOrdencompra(id) == null) {
                    throw new NonexistentEntityException("The ordencompra with id " + id + " no longer exists.");
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
            Ordencompra ordencompra;
            try {
                ordencompra = em.getReference(Ordencompra.class, id);
                ordencompra.getIdordenCompra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordencompra with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Productos> productosCollectionOrphanCheck = ordencompra.getProductosCollection();
            for (Productos productosCollectionOrphanCheckProductos : productosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ordencompra (" + ordencompra + ") cannot be destroyed since the Productos " + productosCollectionOrphanCheckProductos + " in its productosCollection field has a non-nullable idOrdenCompra field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cultivo cultivo = ordencompra.getCultivo();
            if (cultivo != null) {
                cultivo.getOrdencompraCollection().remove(ordencompra);
                cultivo = em.merge(cultivo);
            }
            Proveedor proveedor = ordencompra.getProveedor();
            if (proveedor != null) {
                proveedor.getOrdencompraCollection().remove(ordencompra);
                proveedor = em.merge(proveedor);
            }
            em.remove(ordencompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ordencompra> findOrdencompraEntities() {
        return findOrdencompraEntities(true, -1, -1);
    }

    public List<Ordencompra> findOrdencompraEntities(int maxResults, int firstResult) {
        return findOrdencompraEntities(false, maxResults, firstResult);
    }

    private List<Ordencompra> findOrdencompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ordencompra.class));
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

    public Ordencompra findOrdencompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ordencompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdencompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ordencompra> rt = cq.from(Ordencompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
