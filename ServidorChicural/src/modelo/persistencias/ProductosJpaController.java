/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.persistencias;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.entities.Ordencompra;
import modelo.entities.Producto;
import modelo.entities.Productos;
import modelo.persistencias.exceptions.NonexistentEntityException;

/**
 *
 * @author Lenovo
 */
public class ProductosJpaController implements Serializable {

    public ProductosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ChicuralPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productos productos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordencompra idOrdenCompra = productos.getIdOrdenCompra();
            if (idOrdenCompra != null) {
                idOrdenCompra = em.getReference(idOrdenCompra.getClass(), idOrdenCompra.getIdordenCompra());
                productos.setIdOrdenCompra(idOrdenCompra);
            }
            Producto idProducto = productos.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                productos.setIdProducto(idProducto);
            }
            em.persist(productos);
            if (idOrdenCompra != null) {
                idOrdenCompra.getProductosCollection().add(productos);
                idOrdenCompra = em.merge(idOrdenCompra);
            }
            if (idProducto != null) {
                idProducto.getProductosCollection().add(productos);
                idProducto = em.merge(idProducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productos productos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos persistentProductos = em.find(Productos.class, productos.getIdProductos());
            Ordencompra idOrdenCompraOld = persistentProductos.getIdOrdenCompra();
            Ordencompra idOrdenCompraNew = productos.getIdOrdenCompra();
            Producto idProductoOld = persistentProductos.getIdProducto();
            Producto idProductoNew = productos.getIdProducto();
            if (idOrdenCompraNew != null) {
                idOrdenCompraNew = em.getReference(idOrdenCompraNew.getClass(), idOrdenCompraNew.getIdordenCompra());
                productos.setIdOrdenCompra(idOrdenCompraNew);
            }
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                productos.setIdProducto(idProductoNew);
            }
            productos = em.merge(productos);
            if (idOrdenCompraOld != null && !idOrdenCompraOld.equals(idOrdenCompraNew)) {
                idOrdenCompraOld.getProductosCollection().remove(productos);
                idOrdenCompraOld = em.merge(idOrdenCompraOld);
            }
            if (idOrdenCompraNew != null && !idOrdenCompraNew.equals(idOrdenCompraOld)) {
                idOrdenCompraNew.getProductosCollection().add(productos);
                idOrdenCompraNew = em.merge(idOrdenCompraNew);
            }
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getProductosCollection().remove(productos);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getProductosCollection().add(productos);
                idProductoNew = em.merge(idProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productos.getIdProductos();
                if (findProductos(id) == null) {
                    throw new NonexistentEntityException("The productos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos productos;
            try {
                productos = em.getReference(Productos.class, id);
                productos.getIdProductos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productos with id " + id + " no longer exists.", enfe);
            }
            Ordencompra idOrdenCompra = productos.getIdOrdenCompra();
            if (idOrdenCompra != null) {
                idOrdenCompra.getProductosCollection().remove(productos);
                idOrdenCompra = em.merge(idOrdenCompra);
            }
            Producto idProducto = productos.getIdProducto();
            if (idProducto != null) {
                idProducto.getProductosCollection().remove(productos);
                idProducto = em.merge(idProducto);
            }
            em.remove(productos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productos> findProductosEntities() {
        return findProductosEntities(true, -1, -1);
    }

    public List<Productos> findProductosEntities(int maxResults, int firstResult) {
        return findProductosEntities(false, maxResults, firstResult);
    }

    private List<Productos> findProductosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productos.class));
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

    public Productos findProductos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productos.class, id);
        }catch(Exception e){
            return null;
        }finally {
            em.close();
        }
    }
    
    public List<Productos> findProductosByOrden(Ordencompra orden){
        EntityManager em = getEntityManager();
        Query query2 = em.createQuery("Select e FROM Productos e WHERE e.ordenCompra = " +orden.getIdordenCompra());
        List<Productos> detalles = query2.getResultList();
        return detalles;
    }


    public int getProductosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productos> rt = cq.from(Productos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
