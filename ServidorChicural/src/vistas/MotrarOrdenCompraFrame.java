/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.entities.Ordencompra;
import modelo.persistencias.OrdencompraJpaController;

/**
 *
 * @author Lenovo
 */
public class MotrarOrdenCompraFrame extends javax.swing.JFrame {
    private DefaultTableModel modelo;
    private OrdencompraJpaController daoOrden;
    private List<Ordencompra> ordenes;
     /**
     * Creates new form MotrarOrdenCompraFrame
     */
    public MotrarOrdenCompraFrame() {
        this.daoOrden = new OrdencompraJpaController();
        initComponents();
        this.setupComponents();
    }
    
    private void setupComponents(){
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.jtOrdencompra.setText("0");
        this.generarTabla();
        this.ordenes = this.daoOrden.findOrdencompraEntities();
        this.llenarTabla();
    }
    
    private void generarTabla(){
        modelo = new DefaultTableModel();
        modelo.addColumn("Folio");
        modelo.addColumn("Proveedor");
        modelo.addColumn("Fecha");
        modelo.addColumn("Estado");
        this.jtOrden.setModel(modelo);
    }

    private void llenarTabla(){
        this.generarTabla();
        String[] campos = new String[4];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for(Ordencompra p : ordenes){
            
            campos[0] = p.getIdordenCompra()+"";
            campos[1] = p.getProveedor().getNombre();
            campos[2] = sdf.format(p.getFecha());
            campos[3] = p.getEstado();
            this.modelo.addRow(campos);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtOrdencompra = new javax.swing.JTextField();
        jbBuscar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jbVer = new javax.swing.JButton();
        jbEditar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtOrden = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(550, 320));
        setPreferredSize(new java.awt.Dimension(580, 500));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(5, 105, 27));
        jPanel1.setPreferredSize(new java.awt.Dimension(580, 150));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mostrar ordenes de compra");

        jbBuscar.setText("Buscar");
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jtOrdencompra, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbBuscar)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtOrdencompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscar))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(254, 254, 254));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(5, 105, 27));
        jPanel4.setPreferredSize(new java.awt.Dimension(170, 40));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbVer.setText("Ver");
        jbVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbVerActionPerformed(evt);
            }
        });
        jPanel4.add(jbVer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 90, -1));

        jbEditar.setText("Editar");
        jbEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditarActionPerformed(evt);
            }
        });
        jPanel4.add(jbEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 80, -1));

        jPanel3.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jtOrden.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtOrden);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 520, 280));

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbVerActionPerformed
        Ordencompra orden = this.ordenes.get(this.jtOrden.getSelectedRow());
        DetalleOrdenCompraFrame frame = new DetalleOrdenCompraFrame(this, orden, false);
        frame.setVisible(true);
    }//GEN-LAST:event_jbVerActionPerformed

    private void jbEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditarActionPerformed
        Ordencompra orden = this.ordenes.get(this.jtOrden.getSelectedRow());
        if(orden.getEstado().equalsIgnoreCase("Finalizado")){
            JOptionPane.showMessageDialog(this, "Las ordenes de compra con estado 'Finalizado' no pueden ser editadas.");
        }else{
            DetalleOrdenCompraFrame frame = new DetalleOrdenCompraFrame(this, orden, true);
            frame.setVisible(true);
        }
        
    }//GEN-LAST:event_jbEditarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        new MenuOrdenCompra().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_formWindowClosed

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed
        List<Ordencompra> ord = new ArrayList();
        Ordencompra orden = this.daoOrden.findOrdencompra(Integer.parseInt(this.jtOrdencompra.getText()));
        if(orden != null){
            ord.add(orden);

        }
        this.ordenes = ord;
        this.llenarTabla();

    }//GEN-LAST:event_jbBuscarActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbEditar;
    private javax.swing.JButton jbVer;
    private javax.swing.JTable jtOrden;
    private javax.swing.JTextField jtOrdencompra;
    // End of variables declaration//GEN-END:variables
}