/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas.interfaces;

import modelo.entities.Productos;
import modelo.entities.Proveedor;

/**
 *
 * @author Lenovo
 */
public interface AgregandoProductos {
    void agregarDetalle(Productos detalle);
    
    Proveedor getProveedor();
}
