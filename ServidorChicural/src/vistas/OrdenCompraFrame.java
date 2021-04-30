/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import modelo.entities.Cultivo;
import modelo.entities.Ordencompra;
import modelo.entities.Productos;
import modelo.entities.Proveedor;
import modelo.persistencias.CultivoJpaController;
import modelo.persistencias.OrdencompraJpaController;
import modelo.persistencias.ProductosJpaController;
import vistas.interfaces.AgregandoProductos;
import vistas.interfaces.Buscable;


/**
 *
 * @author abrah
 */
public class OrdenCompraFrame extends javax.swing.JFrame implements Buscable, AgregandoProductos{
    
    private DefaultTableModel modelo;
    private CultivoJpaController daoCultivo;
    private BuscarProveedor jpProveedor;
    private BuscarProducto jpProducto;
    private List<Productos> detallesProducto;
    private Proveedor proveedor;
    private List<Cultivo> cultivos;
    
    /**
     * Creates new form OrdenCompraFrame
     */
    public OrdenCompraFrame() {
        this.daoCultivo = new CultivoJpaController();
        this. cultivos = new ArrayList();
        this.detallesProducto = new ArrayList();
        initComponents();
        this.setupComponents();
        
    }
    
    private void setupComponents(){
        
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        ((JTextField)this.jdcFecha.getComponent(1)).setEditable(false);
        this.jdcFecha.setDateFormatString("dd/MM/YYYY");
        this.jdcFecha.setDate(new Date());
        
        
        this.cultivos = this.daoCultivo.findCultivoEntities();
        this.jcbCultivo.removeAllItems();
        for(Cultivo c : cultivos){
            this.jcbCultivo.addItem(c.getNombre());
        }
        
        this.generarTabla();
        
        this.jtfProveedor.setEditable(false);
        
        this.jsBlock.setValue(1);
        
        
    }
    
    private void agregarEventos(){
        
    }
    
    private void generarTabla(){
        modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Total");
        this.jtProductos.setModel(modelo);
    }
    
    private void llenarTabla(){
        this.generarTabla();
        String[] campos = new String[3];
        for(Productos p : detallesProducto){
            campos[0] = p.getIdProducto().getNombre();
            campos[1] = Integer.toString(p.getCantidad());
            campos[2] = Double.toString(p.getTotal());
            this.modelo.addRow(campos);
        }
    }
    
    @Override
    public void setProveedor(Proveedor proveedor){
        this.proveedor = proveedor;
    }
    
    public Proveedor getProveedor(){
        return this.proveedor;
    }
    
    @Override
    public void agregarDetalle(Productos detalle){
        this.detallesProducto.add(detalle);
        this.generarTabla();
        this.llenarTabla();
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Cabecera = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Priincipal = new javax.swing.JPanel();
        jdcFecha = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jbBuscar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jtfProveedor = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jbEliminar = new javax.swing.JButton();
        jbAgregar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtProductos = new javax.swing.JTable();
        jbGuardar = new javax.swing.JButton();
        jsBlock = new javax.swing.JSpinner();
        jcbCultivo = new javax.swing.JComboBox<>();
        jbCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        Cabecera.setBackground(new java.awt.Color(5, 105, 27));
        Cabecera.setPreferredSize(new java.awt.Dimension(491, 60));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Chicural");

        javax.swing.GroupLayout CabeceraLayout = new javax.swing.GroupLayout(Cabecera);
        Cabecera.setLayout(CabeceraLayout);
        CabeceraLayout.setHorizontalGroup(
            CabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CabeceraLayout.createSequentialGroup()
                .addContainerGap(197, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(185, 185, 185))
        );
        CabeceraLayout.setVerticalGroup(
            CabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CabeceraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        getContentPane().add(Cabecera, java.awt.BorderLayout.PAGE_START);

        Priincipal.setBackground(new java.awt.Color(254, 254, 254));
        Priincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Priincipal.add(jdcFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 130, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setText("Orden de compra");
        Priincipal.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, -1));

        jPanel1.setBackground(new java.awt.Color(102, 4, 4));
        jPanel1.setPreferredSize(new java.awt.Dimension(40, 375));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBorder(null);
        jButton1.setContentAreaFilled(false);
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 11, 39, -1));

        Priincipal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Productos");
        Priincipal.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Fecha:");
        Priincipal.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        jbBuscar.setText("Buscar");
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });
        Priincipal.add(jbBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Cultivo:");
        Priincipal.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Proveedor:");
        Priincipal.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, -1, -1));

        jtfProveedor.setPreferredSize(new java.awt.Dimension(200, 25));
        Priincipal.add(jtfProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 200, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Block:");
        Priincipal.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jbEliminar.setText("Eliminar");
        jbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(jbEliminar);

        jbAgregar.setText("Agregar");
        jbAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAgregarActionPerformed(evt);
            }
        });
        jPanel2.add(jbAgregar);

        jPanel3.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(5, 105, 27));
        jPanel4.setPreferredSize(new java.awt.Dimension(360, 10));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jtProductos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jtProductos);

        jPanel3.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        Priincipal.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 370, 130));

        jbGuardar.setText("Guardar");
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
            }
        });
        Priincipal.add(jbGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 330, 80, 30));
        Priincipal.add(jsBlock, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 90, -1));

        jcbCultivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbCultivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCultivoActionPerformed(evt);
            }
        });
        Priincipal.add(jcbCultivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, 160, -1));

        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });
        Priincipal.add(jbCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, -1, 30));

        getContentPane().add(Priincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed
        this.jpProveedor = new BuscarProveedor(this);
        this.jpProveedor.setVisible(true);
    }//GEN-LAST:event_jbBuscarActionPerformed

    private void jbAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAgregarActionPerformed
        if(!this.jtfProveedor.getText().equals("")){
            this.jpProducto = new BuscarProducto(this);
            this.jpProducto.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Primero se debe elegir un proveedor para utilizar esta función.");
        }
        
    }//GEN-LAST:event_jbAgregarActionPerformed

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que deseas guardar esta orden de compra?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if(opcion == JOptionPane.YES_OPTION){
            Ordencompra ordenCompra = new Ordencompra();
            ordenCompra.setFecha(new Date());
            ordenCompra.setBlock((int)this.jsBlock.getValue());
            ordenCompra.setCultivo(this.cultivos.get(this.jcbCultivo.getSelectedIndex()));
            ordenCompra.setFolio(1);
            ordenCompra.setProveedor(this.proveedor);
            ordenCompra.setEstado("En proceso");
            double total = 0;
            for(Productos p : this.detallesProducto){
                total += p.getTotal();
            }
            ordenCompra.setMontoTotal(total);
            OrdencompraJpaController daoOrden = new OrdencompraJpaController();
           
            daoOrden.create(ordenCompra);
            
            
            ProductosJpaController daoProductos = new ProductosJpaController();
            for(Productos p : this.detallesProducto){
                p.setIdOrdenCompra(ordenCompra);
                p.setOrdenCompra(ordenCompra.getIdordenCompra());
                daoProductos.create(p);
            }
        }
    }//GEN-LAST:event_jbGuardarActionPerformed

    private void jcbCultivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCultivoActionPerformed

    }//GEN-LAST:event_jcbCultivoActionPerformed

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed
        if(!this.jtfProveedor.getText().equals("")){
            int index = this.jtProductos.getSelectedRow();
            this.modelo.removeRow(index);
            this.detallesProducto.remove(index);
        }else{
            JOptionPane.showMessageDialog(this, "Primero se debe elegir un proveedor para utilizar esta función.");
        }
    }//GEN-LAST:event_jbEliminarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        new MenuOrdenCompra().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_formWindowClosed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        int opcion = JOptionPane.showConfirmDialog(this, "¿Estas seguro que deseas cancelar la orden de compra?", "Confirmación",  JOptionPane.YES_NO_OPTION);
        if(opcion == JOptionPane.YES_OPTION){
            this.dispose();
        }

    }//GEN-LAST:event_jbCancelarActionPerformed

    public void addAgregarProductoListener(ActionListener listener){
        this.jbAgregar.addActionListener(listener);
    }
    
    public void addEliminarProductoListener(ActionListener listener){
        this.jbEliminar.addActionListener(listener);
    }
    
    
    public void addGuardarOrdenListener(ActionListener listener){
        this.jbGuardar.addActionListener(listener);
    }
    
    @Override
    public void actualizarProveedor(){
        this.jtfProveedor.setText(this.proveedor.getNombre());
        this.detallesProducto.clear();
        this.llenarTabla();
    }
    
    public Date getFecha(){
        return this.jdcFecha.getDate();
    }
    
    public String getCultivo(){
        return this.jcbCultivo.getSelectedItem().toString();
    }
    
    public String getBlock(){
        return this.jsBlock.getValue().toString();
    }
    
    
    public void mostrarMensajeError(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cabecera;
    private javax.swing.JPanel Priincipal;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbAgregar;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JComboBox<String> jcbCultivo;
    private com.toedter.calendar.JDateChooser jdcFecha;
    private javax.swing.JSpinner jsBlock;
    private javax.swing.JTable jtProductos;
    private javax.swing.JTextField jtfProveedor;
    // End of variables declaration//GEN-END:variables
}
