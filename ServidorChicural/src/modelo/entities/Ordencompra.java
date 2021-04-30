/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "ordencompra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordencompra.findAll", query = "SELECT o FROM Ordencompra o")
    , @NamedQuery(name = "Ordencompra.findByIdordenCompra", query = "SELECT o FROM Ordencompra o WHERE o.idordenCompra = :idordenCompra")
    , @NamedQuery(name = "Ordencompra.findByFolio", query = "SELECT o FROM Ordencompra o WHERE o.folio = :folio")
    , @NamedQuery(name = "Ordencompra.findByFecha", query = "SELECT o FROM Ordencompra o WHERE o.fecha = :fecha")
    , @NamedQuery(name = "Ordencompra.findByHectareas", query = "SELECT o FROM Ordencompra o WHERE o.hectareas = :hectareas")
    , @NamedQuery(name = "Ordencompra.findByBlock", query = "SELECT o FROM Ordencompra o WHERE o.block = :block")
    , @NamedQuery(name = "Ordencompra.findByMontoTotal", query = "SELECT o FROM Ordencompra o WHERE o.montoTotal = :montoTotal")})
public class Ordencompra implements Serializable {

    @Column(name = "estado")
    private String estado;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOrdenCompra")
    private Collection<Productos> productosCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idordenCompra")
    private Integer idordenCompra;
    @Basic(optional = false)
    @Column(name = "folio")
    private int folio;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "hectareas")
    private int hectareas;
    @Basic(optional = false)
    @Column(name = "block")
    private int block;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montoTotal")
    private Double montoTotal;
    @JoinColumn(name = "cultivo", referencedColumnName = "idcultivo")
    @ManyToOne(optional = false)
    private Cultivo cultivo;
    @JoinColumn(name = "proveedor", referencedColumnName = "idProveedor")
    @ManyToOne(optional = false)
    private Proveedor proveedor;

    public Ordencompra() {
    }

    public Ordencompra(Integer idordenCompra) {
        this.idordenCompra = idordenCompra;
    }

    public Ordencompra(Integer idordenCompra, int folio, Date fecha, int hectareas, int block) {
        this.idordenCompra = idordenCompra;
        this.folio = folio;
        this.fecha = fecha;
        this.hectareas = hectareas;
        this.block = block;
    }

    public Integer getIdordenCompra() {
        return idordenCompra;
    }

    public void setIdordenCompra(Integer idordenCompra) {
        this.idordenCompra = idordenCompra;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getHectareas() {
        return hectareas;
    }

    public void setHectareas(int hectareas) {
        this.hectareas = hectareas;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Cultivo getCultivo() {
        return cultivo;
    }

    public void setCultivo(Cultivo cultivo) {
        this.cultivo = cultivo;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idordenCompra != null ? idordenCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordencompra)) {
            return false;
        }
        Ordencompra other = (Ordencompra) object;
        if ((this.idordenCompra == null && other.idordenCompra != null) || (this.idordenCompra != null && !this.idordenCompra.equals(other.idordenCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entities.Ordencompra[ idordenCompra=" + idordenCompra + " ]";
    }

    @XmlTransient
    public Collection<Productos> getProductosCollection() {
        return productosCollection;
    }

    public void setProductosCollection(Collection<Productos> productosCollection) {
        this.productosCollection = productosCollection;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
