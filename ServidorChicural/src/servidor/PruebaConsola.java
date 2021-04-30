/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import modelo.entities.Cultivo;
import modelo.entities.Ordencompra;
import modelo.entities.Producto;
import modelo.entities.Productos;
import modelo.entities.Proveedor;
import modelo.persistencias.OrdencompraJpaController;
import modelo.persistencias.ProductosJpaController;
import modelo.persistencias.exceptions.NonexistentEntityException;

/**
 *
 * @author abrah
 */
public class PruebaConsola {
    private Scanner entrada;
    
    public PruebaConsola(){
        this.entrada = new Scanner(System.in);
    }
    public void start(){
        while(true){
            System.out.println("1.-Realizar orden de compra");
            System.out.println("2.-Editar orden de compra");
            System.out.println("3.-Mostrar ordenes de compra");
            
            int opcion = this.entrada.nextInt();
            switch(opcion){
                case 1:
                    this.realizarOrden();
                    break;
                case 2:
                    this.editarOrden();
                    break;  
                case 3:
                    this.mostrarOrdenes();
                    break;
                case 9:
                    System.out.println("fin");
                    break;
            }
        }
    }
    
    private void realizarOrden(){
        System.out.println("------Realizar orden de compra------");
        System.out.println("Ingrese la id del proveedor:");
        int idProveedor = this.entrada.nextInt();
        System.out.println("Ingrese el block al que va dirigido");
        int numBlock = this.entrada.nextInt();
        System.out.println("Ingrese el cultivo al que va dirigido");
        int numCultivo = this.entrada.nextInt();
        List<Productos> detalles = new ArrayList();
        String opcion = "si";
        do{
            System.out.println("Ingrese id del producto que desea agregar:");
            int idProducto  = this.entrada.nextInt();
            System.out.println("Cantidad deseada:");
            int cantidad = this.entrada.nextInt();
            Producto producto = new Producto(idProducto);
            Productos productos = new Productos();
            productos.setCantidad(cantidad);
            productos.setIdProducto(producto);
            detalles.add(productos);
            System.out.println("Quiere agregar otro producto?");
            opcion = this.entrada.next();
        }while(!opcion.equalsIgnoreCase("no") );
        Ordencompra ordenCompra = new Ordencompra();
        ordenCompra.setFecha(new Date());
        ordenCompra.setBlock(numBlock);
        ordenCompra.setCultivo(new Cultivo(numCultivo));
        ordenCompra.setFolio(1);
        ordenCompra.setProveedor(new Proveedor(idProveedor));
        OrdencompraJpaController daoOrden = new OrdencompraJpaController();
        daoOrden.create(ordenCompra); 
        List<Ordencompra> ordenes = daoOrden.findOrdencompraEntities();
        Ordencompra ultima = ordenes.get(ordenes.size()-1);
        
        ProductosJpaController daoProductos = new ProductosJpaController();
        for(Productos p : detalles){
            p.setIdOrdenCompra(ultima);
            daoProductos.create(p);
        }
        
    }
    
    private void editarOrden(){
        try {
            System.out.println("------Editar orden de compra------");
            System.out.println("Ingrese id de la orden a modificar: ");
            int idOrden = this.entrada.nextInt();
            System.out.println("Ingrese la nueva id del proveedor:");
            int idProveedor = this.entrada.nextInt();
            System.out.println("Ingrese el nuevo block al que va dirigido");
            int numBlock = this.entrada.nextInt();
            System.out.println("Ingrese el nuevo cultivo al que va dirigido");
            int numCultivo = this.entrada.nextInt();
            System.out.println("Ingrese id del detalla que quiere modificar");
            int numDetalle = this.entrada.nextInt();
            System.out.println("Ingrese nueva cantidad:");
            int cant = this.entrada.nextInt();
            
            
            OrdencompraJpaController daoOrden = new OrdencompraJpaController();
            Ordencompra orden = daoOrden.findOrdencompra(idOrden);
            orden.setProveedor(new Proveedor(idProveedor));
            orden.setBlock(numBlock);
            orden.setCultivo(new Cultivo(numCultivo));
            daoOrden.edit(orden);
            
            
            
            ProductosJpaController daoProductos = new ProductosJpaController();
            Productos productos = daoProductos.findProductos(numDetalle);
            productos.setCantidad(cant);
            daoProductos.edit(productos);
            
            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PruebaConsola.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PruebaConsola.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void mostrarOrdenes(){
        System.out.println("------Mostrar ordenes de compra------");
        OrdencompraJpaController daoOrden = new OrdencompraJpaController();
        List<Ordencompra> lista = daoOrden.findOrdencompraEntities();
        for(int f = 0; f < lista.size(); f++){
            System.out.println("Id: " + lista.get(f).getIdordenCompra() +
                               "/ Proveedor: " + lista.get(f).getProveedor().getNombre() +
                               "/ Cultivo: "  + lista.get(f).getCultivo().getNombre() +
                               "/ Fecha: " + lista.get(f).getFecha());
        }
        
    }
}
