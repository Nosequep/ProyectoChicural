/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abrah
 */
@Entity
@Table(name = "productos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productos.findAll", query = "SELECT p FROM Productos p")
    , @NamedQuery(name = "Productos.findByIdProductos", query = "SELECT p FROM Productos p WHERE p.idProductos = :idProductos")
    , @NamedQuery(name = "Productos.findByCantidad", query = "SELECT p FROM Productos p WHERE p.cantidad = :cantidad")})
public class Productos implements Serializable {

    @Basic(optional = false)
    @Column(name = "ordenCompra")
    private int ordenCompra;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProductos")
    private Integer idProductos;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "idOrdenCompra", referencedColumnName = "idordenCompra")
    @ManyToOne(optional = false)
    private Ordencompra idOrdenCompra;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne(optional = false)
    private Producto idProducto;

    public Productos() {
    }

    public Productos(Integer idProductos) {
        this.idProductos = idProductos;
    }

    public Productos(Integer idProductos, int cantidad) {
        this.idProductos = idProductos;
        this.cantidad = cantidad;
    }

    public Integer getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(Integer idProductos) {
        this.idProductos = idProductos;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Ordencompra getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Ordencompra idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProductos != null ? idProductos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productos)) {
            return false;
        }
        Productos other = (Productos) object;
        if ((this.idProductos == null && other.idProductos != null) || (this.idProductos != null && !this.idProductos.equals(other.idProductos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entities.Productos[ idProductos=" + idProductos + " ]";
    }

    public int getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(int ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    
}
