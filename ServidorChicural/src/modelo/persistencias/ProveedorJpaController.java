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
import modelo.entities.Producto;
import modelo.entities.Proveedor;
import modelo.persistencias.exceptions.IllegalOrphanException;
import modelo.persistencias.exceptions.NonexistentEntityException;

/**
 *
 * @author Lenovo
 */
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ChicuralPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) {
        if (proveedor.getOrdencompraCollection() == null) {
            proveedor.setOrdencompraCollection(new ArrayList<Ordencompra>());
        }
        if (proveedor.getProductoCollection() == null) {
            proveedor.setProductoCollection(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Ordencompra> attachedOrdencompraCollection = new ArrayList<Ordencompra>();
            for (Ordencompra ordencompraCollectionOrdencompraToAttach : proveedor.getOrdencompraCollection()) {
                ordencompraCollectionOrdencompraToAttach = em.getReference(ordencompraCollectionOrdencompraToAttach.getClass(), ordencompraCollectionOrdencompraToAttach.getIdordenCompra());
                attachedOrdencompraCollection.add(ordencompraCollectionOrdencompraToAttach);
            }
            proveedor.setOrdencompraCollection(attachedOrdencompraCollection);
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionProductoToAttach : proveedor.getProductoCollection()) {
                productoCollectionProductoToAttach = em.getReference(productoCollectionProductoToAttach.getClass(), productoCollectionProductoToAttach.getIdProducto());
                attachedProductoCollection.add(productoCollectionProductoToAttach);
            }
            proveedor.setProductoCollection(attachedProductoCollection);
            em.persist(proveedor);
            for (Ordencompra ordencompraCollectionOrdencompra : proveedor.getOrdencompraCollection()) {
                Proveedor oldProveedorOfOrdencompraCollectionOrdencompra = ordencompraCollectionOrdencompra.getProveedor();
                ordencompraCollectionOrdencompra.setProveedor(proveedor);
                ordencompraCollectionOrdencompra = em.merge(ordencompraCollectionOrdencompra);
                if (oldProveedorOfOrdencompraCollectionOrdencompra != null) {
                    oldProveedorOfOrdencompraCollectionOrdencompra.getOrdencompraCollection().remove(ordencompraCollectionOrdencompra);
                    oldProveedorOfOrdencompraCollectionOrdencompra = em.merge(oldProveedorOfOrdencompraCollectionOrdencompra);
                }
            }
            for (Producto productoCollectionProducto : proveedor.getProductoCollection()) {
                Proveedor oldIdProveedorOfProductoCollectionProducto = productoCollectionProducto.getIdProveedor();
                productoCollectionProducto.setIdProveedor(proveedor);
                productoCollectionProducto = em.merge(productoCollectionProducto);
                if (oldIdProveedorOfProductoCollectionProducto != null) {
                    oldIdProveedorOfProductoCollectionProducto.getProductoCollection().remove(productoCollectionProducto);
                    oldIdProveedorOfProductoCollectionProducto = em.merge(oldIdProveedorOfProductoCollectionProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getIdProveedor());
            Collection<Ordencompra> ordencompraCollectionOld = persistentProveedor.getOrdencompraCollection();
            Collection<Ordencompra> ordencompraCollectionNew = proveedor.getOrdencompraCollection();
            Collection<Producto> productoCollectionOld = persistentProveedor.getProductoCollection();
            Collection<Producto> productoCollectionNew = proveedor.getProductoCollection();
            List<String> illegalOrphanMessages = null;
            for (Ordencompra ordencompraCollectionOldOrdencompra : ordencompraCollectionOld) {
                if (!ordencompraCollectionNew.contains(ordencompraCollectionOldOrdencompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ordencompra " + ordencompraCollectionOldOrdencompra + " since its proveedor field is not nullable.");
                }
            }
            for (Producto productoCollectionOldProducto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Producto " + productoCollectionOldProducto + " since its idProveedor field is not nullable.");
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
            proveedor.setOrdencompraCollection(ordencompraCollectionNew);
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewProductoToAttach : productoCollectionNew) {
                productoCollectionNewProductoToAttach = em.getReference(productoCollectionNewProductoToAttach.getClass(), productoCollectionNewProductoToAttach.getIdProducto());
                attachedProductoCollectionNew.add(productoCollectionNewProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            proveedor.setProductoCollection(productoCollectionNew);
            proveedor = em.merge(proveedor);
            for (Ordencompra ordencompraCollectionNewOrdencompra : ordencompraCollectionNew) {
                if (!ordencompraCollectionOld.contains(ordencompraCollectionNewOrdencompra)) {
                    Proveedor oldProveedorOfOrdencompraCollectionNewOrdencompra = ordencompraCollectionNewOrdencompra.getProveedor();
                    ordencompraCollectionNewOrdencompra.setProveedor(proveedor);
                    ordencompraCollectionNewOrdencompra = em.merge(ordencompraCollectionNewOrdencompra);
                    if (oldProveedorOfOrdencompraCollectionNewOrdencompra != null && !oldProveedorOfOrdencompraCollectionNewOrdencompra.equals(proveedor)) {
                        oldProveedorOfOrdencompraCollectionNewOrdencompra.getOrdencompraCollection().remove(ordencompraCollectionNewOrdencompra);
                        oldProveedorOfOrdencompraCollectionNewOrdencompra = em.merge(oldProveedorOfOrdencompraCollectionNewOrdencompra);
                    }
                }
            }
            for (Producto productoCollectionNewProducto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewProducto)) {
                    Proveedor oldIdProveedorOfProductoCollectionNewProducto = productoCollectionNewProducto.getIdProveedor();
                    productoCollectionNewProducto.setIdProveedor(proveedor);
                    productoCollectionNewProducto = em.merge(productoCollectionNewProducto);
                    if (oldIdProveedorOfProductoCollectionNewProducto != null && !oldIdProveedorOfProductoCollectionNewProducto.equals(proveedor)) {
                        oldIdProveedorOfProductoCollectionNewProducto.getProductoCollection().remove(productoCollectionNewProducto);
                        oldIdProveedorOfProductoCollectionNewProducto = em.merge(oldIdProveedorOfProductoCollectionNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedor.getIdProveedor();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
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
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getIdProveedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Ordencompra> ordencompraCollectionOrphanCheck = proveedor.getOrdencompraCollection();
            for (Ordencompra ordencompraCollectionOrphanCheckOrdencompra : ordencompraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the Ordencompra " + ordencompraCollectionOrphanCheckOrdencompra + " in its ordencompraCollection field has a non-nullable proveedor field.");
            }
            Collection<Producto> productoCollectionOrphanCheck = proveedor.getProductoCollection();
            for (Producto productoCollectionOrphanCheckProducto : productoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the Producto " + productoCollectionOrphanCheckProducto + " in its productoCollection field has a non-nullable idProveedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(proveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
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

    public Proveedor findProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Proveedor> findProveedorByName(String nombre){
        EntityManager em = getEntityManager();
        Query query2 = em.createQuery("Select e FROM Proveedor e WHERE e.nombre like '%" + nombre + "%'");
        List<Proveedor> proveedores = query2.getResultList();
        return proveedores;
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
