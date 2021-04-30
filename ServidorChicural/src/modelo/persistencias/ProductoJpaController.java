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
import modelo.entities.Proveedor;
import modelo.entities.Productos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.entities.Producto;
import modelo.persistencias.exceptions.IllegalOrphanException;
import modelo.persistencias.exceptions.NonexistentEntityException;

/**
 *
 * @author Lenovo
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ChicuralPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getProductosCollection() == null) {
            producto.setProductosCollection(new ArrayList<Productos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor idProveedor = producto.getIdProveedor();
            if (idProveedor != null) {
                idProveedor = em.getReference(idProveedor.getClass(), idProveedor.getIdProveedor());
                producto.setIdProveedor(idProveedor);
            }
            Collection<Productos> attachedProductosCollection = new ArrayList<Productos>();
            for (Productos productosCollectionProductosToAttach : producto.getProductosCollection()) {
                productosCollectionProductosToAttach = em.getReference(productosCollectionProductosToAttach.getClass(), productosCollectionProductosToAttach.getIdProductos());
                attachedProductosCollection.add(productosCollectionProductosToAttach);
            }
            producto.setProductosCollection(attachedProductosCollection);
            em.persist(producto);
            if (idProveedor != null) {
                idProveedor.getProductoCollection().add(producto);
                idProveedor = em.merge(idProveedor);
            }
            for (Productos productosCollectionProductos : producto.getProductosCollection()) {
                Producto oldIdProductoOfProductosCollectionProductos = productosCollectionProductos.getIdProducto();
                productosCollectionProductos.setIdProducto(producto);
                productosCollectionProductos = em.merge(productosCollectionProductos);
                if (oldIdProductoOfProductosCollectionProductos != null) {
                    oldIdProductoOfProductosCollectionProductos.getProductosCollection().remove(productosCollectionProductos);
                    oldIdProductoOfProductosCollectionProductos = em.merge(oldIdProductoOfProductosCollectionProductos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            Proveedor idProveedorOld = persistentProducto.getIdProveedor();
            Proveedor idProveedorNew = producto.getIdProveedor();
            Collection<Productos> productosCollectionOld = persistentProducto.getProductosCollection();
            Collection<Productos> productosCollectionNew = producto.getProductosCollection();
            List<String> illegalOrphanMessages = null;
            for (Productos productosCollectionOldProductos : productosCollectionOld) {
                if (!productosCollectionNew.contains(productosCollectionOldProductos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Productos " + productosCollectionOldProductos + " since its idProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idProveedorNew != null) {
                idProveedorNew = em.getReference(idProveedorNew.getClass(), idProveedorNew.getIdProveedor());
                producto.setIdProveedor(idProveedorNew);
            }
            Collection<Productos> attachedProductosCollectionNew = new ArrayList<Productos>();
            for (Productos productosCollectionNewProductosToAttach : productosCollectionNew) {
                productosCollectionNewProductosToAttach = em.getReference(productosCollectionNewProductosToAttach.getClass(), productosCollectionNewProductosToAttach.getIdProductos());
                attachedProductosCollectionNew.add(productosCollectionNewProductosToAttach);
            }
            productosCollectionNew = attachedProductosCollectionNew;
            producto.setProductosCollection(productosCollectionNew);
            producto = em.merge(producto);
            if (idProveedorOld != null && !idProveedorOld.equals(idProveedorNew)) {
                idProveedorOld.getProductoCollection().remove(producto);
                idProveedorOld = em.merge(idProveedorOld);
            }
            if (idProveedorNew != null && !idProveedorNew.equals(idProveedorOld)) {
                idProveedorNew.getProductoCollection().add(producto);
                idProveedorNew = em.merge(idProveedorNew);
            }
            for (Productos productosCollectionNewProductos : productosCollectionNew) {
                if (!productosCollectionOld.contains(productosCollectionNewProductos)) {
                    Producto oldIdProductoOfProductosCollectionNewProductos = productosCollectionNewProductos.getIdProducto();
                    productosCollectionNewProductos.setIdProducto(producto);
                    productosCollectionNewProductos = em.merge(productosCollectionNewProductos);
                    if (oldIdProductoOfProductosCollectionNewProductos != null && !oldIdProductoOfProductosCollectionNewProductos.equals(producto)) {
                        oldIdProductoOfProductosCollectionNewProductos.getProductosCollection().remove(productosCollectionNewProductos);
                        oldIdProductoOfProductosCollectionNewProductos = em.merge(oldIdProductoOfProductosCollectionNewProductos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Productos> productosCollectionOrphanCheck = producto.getProductosCollection();
            for (Productos productosCollectionOrphanCheckProductos : productosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Productos " + productosCollectionOrphanCheckProductos + " in its productosCollection field has a non-nullable idProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Proveedor idProveedor = producto.getIdProveedor();
            if (idProveedor != null) {
                idProveedor.getProductoCollection().remove(producto);
                idProveedor = em.merge(idProveedor);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Producto> findProductoByName(String nombre, Proveedor proveedor){
        EntityManager em = getEntityManager();
        Query query2 = em.createQuery("Select e FROM Producto e WHERE e.nombre like '%" + nombre + "%' and e.proveedor = " + proveedor.getIdProveedor());
        List<Producto> productos = query2.getResultList();
        return productos;
    }
    
    public List<Producto> findProductoByProveedor(Proveedor provedor){
        EntityManager em = getEntityManager();
        Query query2 = em.createQuery("Select e FROM Producto e WHERE e.proveedor = " +provedor.getIdProveedor());
        List<Producto> productos = query2.getResultList();
        return productos;
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
