/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author abrah
 */
public class OrdenCompraController {
//    private OrdenCompraFrame fOrdenCompra;
//    private OrdenCompraModel mOrdenCompra;
    
//    public OrdenCompraController(OrdenCompraFrame fOrdenCompra, OrdenCompraModel mOrdenCompra){
//        this.fOrdenCompra = fOrdenCompra;
//        this.mOrdenCompra = mOrdenCompra;
//        
//        this.fOrdenCompra.addGuardarOrdenListener(new GuardarOrdenListener()); 
//        this.fOrdenCompra.addAgregarProductoListener(new AgregarProductoListener());
//        this.fOrdenCompra.addEliminarProductoListener(new EliminarProductoListener());
//    }
    
    private class GuardarOrdenListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
//            fOrdenCompra.mostrarMensajeError("Se guardo orden.");
        }
    }
    
    private class AgregarProductoListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
//            fOrdenCompra.mostrarMensajeError("Se agrega producto");
        }
    }
    
    private class EliminarProductoListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
//            fOrdenCompra.mostrarMensajeError("Se elimina producto");
        }
    }
}
