/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones;

import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;

/**
 *
 * @author UMSA-JES
 */
public class JD_abmDetalle extends javax.swing.JDialog {

    /**
     * Creates new form JD_abmDetalle
     */
    int cod_complemento;
    int cod_trans_detalle;
    int cod_rol;
    public JD_abmDetalle(java.awt.Frame parent, boolean modal, String cod_trans_detalle, String cod_complemento,int cod_rol) {
        super(parent, modal);
        initComponents();
        this.cod_trans_detalle = Integer.parseInt(cod_trans_detalle);
        this.cod_complemento = Integer.parseInt(cod_complemento);
        this.cod_rol=cod_rol;
        this.detalleDefault();
        this.setLocationRelativeTo(null);
    }
    public void detalleDefault(){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            if(cod_rol==2)
                this.JTA_detalle.setText(puerto.getDetalleALM("SET-getDetalle", cod_complemento));
            else
                this.JTA_detalle.setText(puerto.getDetalle("SET-getDetalle", cod_complemento));
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,"<html> Error de conexion con el servidor <br> "+e,"Info Default",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    public void delDetalle(){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            int dialogResult = javax.swing.JOptionPane.showConfirmDialog (this, "¿Esta seguro(a) de eliminar el detalle?","Eliminar Detalle",javax.swing.JOptionPane.YES_NO_OPTION);
            if(dialogResult == javax.swing.JOptionPane.YES_OPTION){
//                if(cod_rol==2)
//                    puerto.delDetalleALM("SET-delDetalle", cod_complemento, cod_trans_detalle);
//                else
                    puerto.delDetalle("SET-delDetalle", cod_complemento, cod_trans_detalle);
                javax.swing.JOptionPane.showMessageDialog(this,"Se elimino el detalle correctamente","Eliminar Detalle",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            }
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,"<html> Error de conexion con el servidor <br> "+e,"Borrar Detalle",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    public void updateDetalle(){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS(); 
//            if(cod_rol==2)
//                puerto.updateDetalleALM("SET-updateDetalle", cod_complemento, this.JTA_detalle.getText().trim());
//            else
                puerto.updateDetalle("SET-updateDetalle", cod_complemento, this.JTA_detalle.getText().trim());
            javax.swing.JOptionPane.showMessageDialog(this,"Se actualizo el detalle correctamente","Adicionar Detalle",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,"<html> Error de conexion con el servidor <br> "+e,"Actualizar Detalle",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
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
        JB_actualiza = new javax.swing.JButton();
        JB_elimina = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTA_detalle = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(java.awt.SystemColor.controlHighlight);

        JB_actualiza.setForeground(new java.awt.Color(0, 102, 51));
        JB_actualiza.setText("ACTUALIZAR");
        JB_actualiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_actualizaActionPerformed(evt);
            }
        });

        JB_elimina.setForeground(new java.awt.Color(255, 0, 0));
        JB_elimina.setText("ELIMINAR");
        JB_elimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_eliminaActionPerformed(evt);
            }
        });

        JTA_detalle.setColumns(20);
        JTA_detalle.setRows(5);
        jScrollPane1.setViewportView(JTA_detalle);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(20, 76, 232));
        jLabel1.setText("OPCIONES DETALLE");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 0));
        jButton1.setText("SALIR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(JB_elimina, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JB_actualiza)
                .addGap(113, 113, 113))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JB_elimina)
                    .addComponent(JB_actualiza))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void JB_eliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_eliminaActionPerformed
        // TODO add your handling code here:
        this.delDetalle();
    }//GEN-LAST:event_JB_eliminaActionPerformed

    private void JB_actualizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_actualizaActionPerformed
        // TODO add your handling code here:
        this.updateDetalle();
    }//GEN-LAST:event_JB_actualizaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JD_abmDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_abmDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_abmDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_abmDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_abmDetalle dialog = new JD_abmDetalle(new javax.swing.JFrame(), true, "", "",0);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JB_actualiza;
    private javax.swing.JButton JB_elimina;
    private javax.swing.JTextArea JTA_detalle;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}