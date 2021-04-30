/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author abrah
 */
@Entity
@Table(name = "cultivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cultivo.findAll", query = "SELECT c FROM Cultivo c")
    , @NamedQuery(name = "Cultivo.findByIdcultivo", query = "SELECT c FROM Cultivo c WHERE c.idcultivo = :idcultivo")
    , @NamedQuery(name = "Cultivo.findByNombre", query = "SELECT c FROM Cultivo c WHERE c.nombre = :nombre")})
public class Cultivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcultivo")
    private Integer idcultivo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cultivo")
    private Collection<Ordencompra> ordencompraCollection;

    public Cultivo() {
    }

    public Cultivo(Integer idcultivo) {
        this.idcultivo = idcultivo;
    }

    public Cultivo(Integer idcultivo, String nombre) {
        this.idcultivo = idcultivo;
        this.nombre = nombre;
    }

    public Integer getIdcultivo() {
        return idcultivo;
    }

    public void setIdcultivo(Integer idcultivo) {
        this.idcultivo = idcultivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Ordencompra> getOrdencompraCollection() {
        return ordencompraCollection;
    }

    public void setOrdencompraCollection(Collection<Ordencompra> ordencompraCollection) {
        this.ordencompraCollection = ordencompraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcultivo != null ? idcultivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cultivo)) {
            return false;
        }
        Cultivo other = (Cultivo) object;
        if ((this.idcultivo == null && other.idcultivo != null) || (this.idcultivo != null && !this.idcultivo.equals(other.idcultivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entities.Cultivo[ idcultivo=" + idcultivo + " ]";
    }
    
}
