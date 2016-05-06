/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.gui.transacciones.Adquisiciones.tramites;

import java.util.Map;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;

/**
 *
 * @author Henrry
 */
public class JD_PLAZOS extends javax.swing.JDialog {

    /**
     * Creates new form JD_CONTRATADO
     */
    private int cod_transaccion;
    
    public JD_PLAZOS(java.awt.Frame parent, boolean modal, int cod_transaccion) {
        super(parent, modal);
        initComponents();
        this.cod_transaccion = cod_transaccion;
        this.setLocationRelativeTo(null);
        CargaContratado(this.cod_transaccion);
    }
    private void CargaContratado(int cod_transaccion){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            Map[] datos = puerto.getContratoPlazos(cod_transaccion);
            if (datos != null) {
                
//                System.err.println("Con Datos");
                this.JTF_PLAZO_PRESTACION.setText(datos[0].get("PLAZO_PRESTACION").toString());
                this.JTF_PLAZO_OBSERVACIONES.setText(datos[0].get("PLAZO_OBSERVACIONES").toString());
                
                this.JC_TIPO_PLAZO_PRESTACION.setSelectedIndex(Integer.valueOf(datos[0].get("SW_TIPO_PLAZO_PRESTACION").toString()));
                //this.JTF_APROBADO.setText(datos[0].get("APROBADO").toString());
                //this.JTF_CARGO.setText(datos[0].get("CARGO").toString());
//                this.JTF_DIRECCION_CONTRATADO.setText(datos[0].get("DIR_PROPONENTE_ADJUDICADO").toString());
//                this.JTF_DENOMINACION_CONTRATADO.setText(datos[0].get("DENO_PROPONENTE_ADJUDICADO").toString());
                
            }
            else
                System.err.println("Vacio!!!");
        } catch (Exception e) {
            System.err.println("Error :O");          
        }
    }
    private void GuardaContratado(int cod_transaccion){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            Map[] datos = puerto.updateContratoPlazos(cod_transaccion
                    ,this.JTF_PLAZO_PRESTACION.getText()
                    ,this.JTF_PLAZO_OBSERVACIONES.getText()
                    ,String.valueOf(this.JC_TIPO_PLAZO_PRESTACION.getSelectedIndex())
                    
            );
            javax.swing.JOptionPane.showMessageDialog(this, "Se actualizo el tramite de forma exitosa!!! ", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
//            Map[] datos =null;
//            if (datos != null) {
//                System.err.println("Con Datos");
//            }
//            else
//                System.err.println("Vacio!!!");
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error :O <br> " + e, "SYSTEM CAPRICORN",
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

        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        JTF_PLAZO_PRESTACION = new javax.swing.JTextField();
        JTF_PLAZO_OBSERVACIONES = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        JD_GUARDAR = new javax.swing.JButton();
        JD_SALIR = new javax.swing.JButton();
        JC_TIPO_PLAZO_PRESTACION = new javax.swing.JComboBox();
        JC_TIPO_PLAZO_PRESTACION1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PLAZOS");

        jLabel1.setText("PLAZO PRESTACION (DIAS):");

        jLabel5.setText("PLAZO  OBERVACIONES:");

        JTF_PLAZO_PRESTACION.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_PLAZO_PRESTACIONActionPerformed(evt);
            }
        });

        JTF_PLAZO_OBSERVACIONES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_PLAZO_OBSERVACIONESActionPerformed(evt);
            }
        });

        JD_GUARDAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/save_16.png"))); // NOI18N
        JD_GUARDAR.setText("Guardar");
        JD_GUARDAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JD_GUARDARActionPerformed(evt);
            }
        });

        JD_SALIR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_previous.png"))); // NOI18N
        JD_SALIR.setText("SALIR");
        JD_SALIR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JD_SALIRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(JD_GUARDAR)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(JD_SALIR)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JD_GUARDAR)
                    .addComponent(JD_SALIR))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        JC_TIPO_PLAZO_PRESTACION.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Escoja una opción de Plazo", "A partir de la emisión del orden de proceder", "A partir del desembolso del anticipo", "A partir del dia siguiente habil de la suscripcion del contrato", "En un Rango de Fecha" }));
        JC_TIPO_PLAZO_PRESTACION.setMinimumSize(new java.awt.Dimension(320, 26));
        JC_TIPO_PLAZO_PRESTACION.setPreferredSize(new java.awt.Dimension(320, 26));

        JC_TIPO_PLAZO_PRESTACION1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Escoja una opción de Plazo", "A partir de la suscripcion del presente documento", "En un Rango de fecha" }));
        JC_TIPO_PLAZO_PRESTACION1.setMinimumSize(new java.awt.Dimension(320, 26));
        JC_TIPO_PLAZO_PRESTACION1.setPreferredSize(new java.awt.Dimension(320, 26));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JC_TIPO_PLAZO_PRESTACION1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(JC_TIPO_PLAZO_PRESTACION, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(JTF_PLAZO_PRESTACION)
                                .addComponent(JTF_PLAZO_OBSERVACIONES, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(JTF_PLAZO_PRESTACION, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JC_TIPO_PLAZO_PRESTACION, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(JTF_PLAZO_OBSERVACIONES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JC_TIPO_PLAZO_PRESTACION1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JTF_PLAZO_PRESTACIONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_PLAZO_PRESTACIONActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTF_PLAZO_PRESTACIONActionPerformed

    private void JTF_PLAZO_OBSERVACIONESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_PLAZO_OBSERVACIONESActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTF_PLAZO_OBSERVACIONESActionPerformed

    private void JD_GUARDARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JD_GUARDARActionPerformed
        // TODO add your handling code here:
        this.GuardaContratado(this.cod_transaccion);
    }//GEN-LAST:event_JD_GUARDARActionPerformed

    private void JD_SALIRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JD_SALIRActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_JD_SALIRActionPerformed

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
            java.util.logging.Logger.getLogger(JD_CONTRATADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_CONTRATADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_CONTRATADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_CONTRATADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_CONTRATADO dialog = new JD_CONTRATADO(new javax.swing.JFrame(), true,0);
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
    private javax.swing.JComboBox JC_TIPO_PLAZO_PRESTACION;
    private javax.swing.JComboBox JC_TIPO_PLAZO_PRESTACION1;
    private javax.swing.JButton JD_GUARDAR;
    private javax.swing.JButton JD_SALIR;
    private javax.swing.JTextField JTF_PLAZO_OBSERVACIONES;
    private javax.swing.JTextField JTF_PLAZO_PRESTACION;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}