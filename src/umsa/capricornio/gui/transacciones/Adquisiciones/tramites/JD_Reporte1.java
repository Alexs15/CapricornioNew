/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.Adquisiciones.tramites;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.utilitarios.herramientas.i_formatterDate;
/**
 *
 * @author UMSA-JES
 */
public class JD_Reporte1 extends javax.swing.JDialog {

    URL urlMaestro,urlMaestro1,urlMaestro2;
    String estado;
    int tipo_sol;
    Date fi,ff;
    /**
     * Creates new form JD_Reporte
     */
    public JD_Reporte1(){
        
    }
    public JD_Reporte1(java.awt.Frame parent, boolean modal, String e, int ts, Date fec_ini, Date fec_fin) {
        super(parent, modal);
        estado=e;
        tipo_sol=ts;
        fi=fec_ini;
        ff=fec_fin;
        initComponents();
    }
    public JD_Reporte1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
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
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jButton1.setText("Generar Reporte");
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
                .addGap(175, 175, 175)
                .addComponent(jButton1)
                .addContainerGap(201, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jButton1)
                .addContainerGap(107, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        /*JFrame f = new JFrame("hola este modal");
        f.setAlwaysOnTop(true);
        f.setSize(300,300);
        f.setVisible(true);*/
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");            
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@200.7.160.182:1521:ADQUI", "ADQUISICIONES", "4dqu1_c3n72al");
            JD_Reporte1 t1 = new JD_Reporte1();
            Map parameters = new HashMap();
            urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra.jasper");
            urlMaestro1 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra2.jasper");
            urlMaestro2 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra3.jasper");
            parameters.put("ESTADO",estado);
            parameters.put("TIPO_SOL",tipo_sol);
            parameters.put("FECHA_INICIO",fi);
            parameters.put("FECHA_FINAL",ff);
            System.out.println("tipo de solicitud: "+tipo_sol);
            /************/
            /*File file=null,temp=null,temp1=null;
            try{
                file = new File("C:\\Users\\javieralex\\Documents\\tempcapri");
                file.mkdirs();
                file.setWritable(true);
                String archivo = file.getCanonicalPath() + "\\ReporteCompra2.jasper";
                temp = new File(archivo);
                InputStream is = this.getClass().getResourceAsStream("/umsa/capricornio/gui/reports/ReporteCompra2.jasper");
                FileOutputStream archivoDestino = new FileOutputStream(temp);
                FileWriter fw = new FileWriter(temp);
                byte[] buffer = new byte[512*1024];
                int nbLectura;
                while ((nbLectura = is.read(buffer)) != -1)
                    archivoDestino.write(buffer, 0, nbLectura);
            //cierras el archivo,el inputS y el FileW
                fw.close();
                archivoDestino.close();
                is.close();
            }
            catch(Exception e){
                System.out.println("Problema abriendo el pdf de erfc");
                
            }
            try{
                //file.deleteOnExit();
                String archivo1 = file.getCanonicalPath() + "\\ReporteCompra3.jasper";
                temp1 = new File(archivo1);
                InputStream is1 = this.getClass().getResourceAsStream("/umsa/capricornio/gui/reports/ReporteCompra3.jasper");
                FileOutputStream archivoDestino1 = new FileOutputStream(temp1);
                FileWriter fw1 = new FileWriter(temp1);
                byte[] buffer1 = new byte[512*1024];
                int nbLectura1;
                while ((nbLectura1 = is1.read(buffer1)) != -1)
                    archivoDestino1.write(buffer1, 0, nbLectura1);
            //cierras el archivo,el inputS y el FileW
                fw1.close();
                archivoDestino1.close();
                is1.close();
            }
            catch(Exception e){
                System.out.println("Prsadasdoblema abriendo el pdf de erfc");
            }
            /*************/
            
            parameters.put("DIR", urlMaestro1.toString());
            parameters.put("DIR1", urlMaestro2.toString());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(urlMaestro); 
            System.out.println("realizo el jasper reporte");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);
            System.out.println("realizo el jasper print");
            JasperViewer.viewReport(jasperPrint, false);  
            System.out.println("realizo el jasper view");
            
        } catch (Exception e) {
            /*JLabel a=new JLabel("Error Gravichimo: "+e);
            f.add(a,BorderLayout.CENTER);*/
            System.out.println("Error Gravichimo: "+e);
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
            java.util.logging.Logger.getLogger(JD_Reporte1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_Reporte1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_Reporte1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_Reporte1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_Reporte1 dialog = new JD_Reporte1(new javax.swing.JFrame(), false);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}