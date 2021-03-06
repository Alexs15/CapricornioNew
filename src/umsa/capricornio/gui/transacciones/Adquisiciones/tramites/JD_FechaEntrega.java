/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.Adquisiciones.tramites;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;

/**
 *
 * @author UMSA-JES
 */
public class JD_FechaEntrega extends javax.swing.JDialog {

    /**
     * Creates new form JD_FechaEntrega
     */
    private int cod_trans_nro;
    private boolean sw_datos = true;
    
    public JD_FechaEntrega(java.awt.Frame parent, boolean modal,int cod_trans_nro) {
        super(parent, modal);
        this.cod_trans_nro = cod_trans_nro;
        initComponents();
        LlenaFechas();
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
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Fecha_Plazo1 = new net.sf.nachocalendar.components.DateField();
        Fecha_Plazo2 = new net.sf.nachocalendar.components.DateField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("FECHA DE ENTREGA");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel9.setText("FECHA DE INICIO:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setText("FECHA DE CONCLUSION:");

        Fecha_Plazo1.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        Fecha_Plazo1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Fecha_Plazo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Fecha_Plazo1KeyPressed(evt);
            }
        });

        Fecha_Plazo2.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        Fecha_Plazo2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Fecha_Plazo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Fecha_Plazo2KeyPressed(evt);
            }
        });

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Fecha_Plazo1, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(Fecha_Plazo2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 38, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Fecha_Plazo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Fecha_Plazo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LlenaFechas(){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("El cod_trans_nro es: "+cod_trans_nro);
            Map[] datos = puerto.getRestriccionFechas(cod_trans_nro, 13);
            if (datos != null) {
                this.Fecha_Plazo1.setValue(new Date(datos[0].get("FECHA_INICIO").toString()));
                this.Fecha_Plazo2.setValue(new Date(datos[0].get("FECHA_CONCLUSION").toString()));
//                BloqueaInputs();
            }
            else
                sw_datos = false;
            
        } catch (Exception e) {
        }
    }
    private void BloqueaInputs(){
        this.Fecha_Plazo1.setEnabled(false);
        this.Fecha_Plazo2.setEnabled(false);
    }
    private void Fecha_Plazo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Fecha_Plazo1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Fecha_Plazo1KeyPressed

    private void Fecha_Plazo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Fecha_Plazo2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Fecha_Plazo2KeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void guardaFechaEntrega(){
        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            String fecha_EI = "'" + form.format(this.Fecha_Plazo1.getValue()) + "'";
            String fecha_EF = "'" + form.format(this.Fecha_Plazo2.getValue()) + "'";
            puerto.addFechaRestriccion2("SET-addFechaRestriccion",13, cod_trans_nro, fecha_EI, fecha_EF,"","");
//            if(sw_datos)
//                puerto.addFechaRestriccion2("SET-addFechaRestriccion",13, cod_trans_nro, fecha_EI, fecha_EF,"","");
//            else
//                puerto.addFechaRestriccion2("SET-addFechaRestriccion",13, cod_trans_nro, fecha_EI, fecha_EF,"","");
            
            System.out.println("Se adiciono las Fechas correctamente");
        } catch (Exception e) {
            
        }
    }
    private void updateFechaEntrega(){
        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            String fecha_EI = "'" + form.format(this.Fecha_Plazo1.getValue()) + "'";
            String fecha_EF = "'" + form.format(this.Fecha_Plazo2.getValue()) + "'";
            puerto.updateFechaEntrega("SET-updateFechaEntrega",13, cod_trans_nro, fecha_EI, fecha_EF);
//            if(sw_datos)
//                puerto.addFechaRestriccion2("SET-addFechaRestriccion",13, cod_trans_nro, fecha_EI, fecha_EF,"","");
//            else
//                puerto.addFechaRestriccion2("SET-addFechaRestriccion",13, cod_trans_nro, fecha_EI, fecha_EF,"","");
            
            System.out.println("Se adiciono las Fechas correctamente");
        } catch (Exception e) {
            
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(sw_datos){
            guardaFechaEntrega();
        }else{
            updateFechaEntrega();
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(JD_FechaEntrega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_FechaEntrega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_FechaEntrega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_FechaEntrega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_FechaEntrega dialog = new JD_FechaEntrega(new javax.swing.JFrame(), true,0);
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
    private net.sf.nachocalendar.components.DateField Fecha_Plazo1;
    private net.sf.nachocalendar.components.DateField Fecha_Plazo2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
