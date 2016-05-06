/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrmReportesAlmacen.java
 *
 * Created on 24-11-2011, 06:28:20 PM
 */
package umsa.capricornio.gui.Almacen;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.xml.rpc.ServiceException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import umsa.capricornio.domain.Transaccion;
import umsa.capricornio.gui.Almacen.reportes.RepDocsEnviados;
import umsa.capricornio.gui.Almacen.reportes.RepIngresoMatPeriodico;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.JD_Reporte1;
import umsa.capricornio.utilitarios.herramientas.i_formatterDate;

/**
 *
 * @author julian
 */
public class FrmReportesAlmacen extends javax.swing.JInternalFrame {

    FrmMenu menu;
    private Runtime r;
    /** Creates new form FrmReportesAlmacen */
    public FrmReportesAlmacen(FrmMenu menu) {
        this.menu=menu;
        initComponents();
    }

    void MostrarReporteIngMaterial(){
        List list=new ArrayList();        
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getReporteIngMaterialesAlmacen(DatFec_ini.getValue().toString(),DatFec_fin.getValue().toString(),2011);            
            if (datos!=null){                 
                Map map = new HashMap();
                for (int f=0;f<datos.length;f++){
                    Transaccion trans = new Transaccion();
                    trans.setArticulo(datos[f].get("ARTICULO").toString());
                    trans.setFecha_envio(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_ENVIO").toString()));
                    trans.setUnidad_sol(datos[f].get("DE").toString());
                    trans.setUnidad_medida(datos[f].get("UNIDAD_MEDIDA").toString());
                    trans.setCantidad_pedido(Integer.parseInt(datos[f].get("CANTIDAD_PEDIDO").toString()));
                    trans.setNro_ingreso(Integer.parseInt(datos[f].get("NRO").toString()));
                    trans.setCbte(datos[f].get("CBTE").toString());
                    trans.setMemo(datos[f].get("MEMO").toString());
                    trans.setNro_orden_compra(Integer.parseInt(datos[f].get("NRO_ORDEN").toString()));
                    trans.setPrecio_unit(Double.parseDouble(datos[f].get("PRECIO_UNIT").toString()));
                    trans.setEstado(datos[f].get("ESTADO").toString());
                    list.add(trans);
                } 
                RepIngresoMatPeriodico rep = new RepIngresoMatPeriodico();
                rep.Reporte(list);
            }                         
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);} 
        catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,"DEBE ELEJIR UNA FILA PARA PODER IMPRIMIR EL REPORTE","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }  
    }
        
    void MostrarReporteEnvioDocs(){
        List list=new ArrayList();        
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getReporteDocsEnviados(DatFec_ini.getValue().toString(),DatFec_fin.getValue().toString(),2011);            
            if (datos!=null){                 
                Map map = new HashMap();
                for (int f=0;f<datos.length;f++){
                    Transaccion trans = new Transaccion();
                    //t1.estado,t1.fecha_envio,t8.articulo,t2.cantidad_pedido,t2.precio_unit ,t4.nro,t4.memo,T5.DE,t5.cbte,t5.hoja_ruta, t6.casa_comercial
                    trans.setEstado(datos[f].get("ESTADO").toString());
                    trans.setFecha_envio(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_ENVIO").toString()));
                    trans.setArticulo(datos[f].get("ARTICULO").toString());
                    trans.setCantidad_pedido(Integer.parseInt(datos[f].get("CANTIDAD_PEDIDO").toString()));
                    trans.setPrecio_unit(Double.parseDouble(datos[f].get("PRECIO_UNIT").toString()));
                    trans.setNro_ingreso(Integer.parseInt(datos[f].get("NRO").toString()));
                    trans.setMemo(datos[f].get("MEMO").toString());
                    trans.setUnidad_sol(datos[f].get("DE").toString());
                    trans.setCbte(datos[f].get("CBTE").toString());
                    trans.setHoja_ruta(datos[f].get("HOJA_RUTA").toString());
                    trans.setCasa_comercial(datos[f].get("CASA_COMERCIAL").toString());
                    list.add(trans);
                } 
                RepDocsEnviados rep = new RepDocsEnviados();
                rep.Reporte(list);
            }                         
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);} 
        catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,"DEBE ELEJIR UNA FILA PARA PODER IMPRIMIR EL REPORTE","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }  
    }
        
    void CerrarFrame(){
        menu.CerrarFrameInterno(this);
        System.gc();
        r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtnGrpReporte = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        DatFec_ini = new net.sf.nachocalendar.components.DateField();
        jLabel2 = new javax.swing.JLabel();
        DatFec_fin = new net.sf.nachocalendar.components.DateField();
        jPanel1 = new javax.swing.JPanel();
        RadIngreso = new javax.swing.JRadioButton();
        RadEnvio = new javax.swing.JRadioButton();
        RadIngreso2 = new javax.swing.JRadioButton();
        RadEnvio2 = new javax.swing.JRadioButton();
        RadEnvio3 = new javax.swing.JRadioButton();
        RadEnvio4 = new javax.swing.JRadioButton();
        BtnSalir = new javax.swing.JButton();
        BtnReporte = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        RadIngreso1 = new javax.swing.JRadioButton();
        RadEnvio1 = new javax.swing.JRadioButton();

        setTitle("Reportes de Ingreso de Materiales y Envio de Documentos");
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Del :");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 20, 70, 20);

        DatFec_ini.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        DatFec_ini.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        getContentPane().add(DatFec_ini);
        DatFec_ini.setBounds(110, 20, 100, 18);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Al :");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(200, 20, 70, 20);

        DatFec_fin.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        DatFec_fin.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        getContentPane().add(DatFec_fin);
        DatFec_fin.setBounds(280, 20, 100, 18);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        buttonGroup1.add(RadIngreso);
        RadIngreso.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        RadIngreso.setText("Juridica");
        jPanel1.add(RadIngreso);
        RadIngreso.setBounds(110, 10, 110, 23);

        buttonGroup1.add(RadEnvio);
        RadEnvio.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        RadEnvio.setText("Adquicisiones");
        jPanel1.add(RadEnvio);
        RadEnvio.setBounds(110, 30, 105, 23);

        buttonGroup1.add(RadIngreso2);
        RadIngreso2.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        RadIngreso2.setText("Presupuesto");
        RadIngreso2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadIngreso2ActionPerformed(evt);
            }
        });
        jPanel1.add(RadIngreso2);
        RadIngreso2.setBounds(10, 10, 100, 23);

        buttonGroup1.add(RadEnvio2);
        RadEnvio2.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        RadEnvio2.setText("Almacenes");
        jPanel1.add(RadEnvio2);
        RadEnvio2.setBounds(10, 50, 87, 23);

        buttonGroup1.add(RadEnvio3);
        RadEnvio3.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        RadEnvio3.setText("Concluido");
        jPanel1.add(RadEnvio3);
        RadEnvio3.setBounds(110, 50, 81, 23);

        buttonGroup1.add(RadEnvio4);
        RadEnvio4.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        RadEnvio4.setText("Borrador");
        jPanel1.add(RadEnvio4);
        RadEnvio4.setBounds(10, 30, 75, 23);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(220, 50, 230, 90);

        BtnSalir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnSalir.setForeground(new java.awt.Color(255, 0, 0));
        BtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_previous.png"))); // NOI18N
        BtnSalir.setText("Salir");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(BtnSalir);
        BtnSalir.setBounds(250, 150, 150, 25);

        BtnReporte.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnReporte.setForeground(new java.awt.Color(0, 0, 255));
        BtnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/clipboard_text.png"))); // NOI18N
        BtnReporte.setText("Ver Reporte");
        BtnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnReporteActionPerformed(evt);
            }
        });
        getContentPane().add(BtnReporte);
        BtnReporte.setBounds(70, 150, 150, 25);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        BtnGrpReporte.add(RadIngreso1);
        RadIngreso1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        RadIngreso1.setText("Solicitud Normal");
        jPanel2.add(RadIngreso1);
        RadIngreso1.setBounds(30, 20, 150, 23);

        BtnGrpReporte.add(RadEnvio1);
        RadEnvio1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        RadEnvio1.setText("Solicitud Por Descargo");
        jPanel2.add(RadEnvio1);
        RadEnvio1.setBounds(30, 50, 149, 23);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 50, 210, 90);

        setBounds(0, 0, 481, 214);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        CerrarFrame();
    }//GEN-LAST:event_BtnSalirActionPerformed

    void mostrarreporteya(String e,int ts,Date fi,Date ff)
    {
        if(fi.toString().equals(ff.toString()))
            {
                //fi.setDate(fi.getDate()-1);
                ff.setDate(ff.getDate()+1);
                System.out.println(fi);
                System.out.println(ff);
                //JOptionPane.showMessageDialog(this, "Las fechas deben ser diferentes aun si es de un dia");
            }
            else
            {
                System.out.println("no muestra"+fi);
                //mostrarreporteya(e,ts,fi,ff);
            }
        URL urlMaestro,urlMaestro1,urlMaestro2;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");            
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@200.7.160.182:1521:ADQUI", "ADQUISICIONES", "4dqu1_c3n72al");
            JD_Reporte1 t1 = new JD_Reporte1();
            Map parameters = new HashMap();
            urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra0.jasper");
            urlMaestro1 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra20.jasper");
            urlMaestro2 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra30.jasper");
            parameters.put("ESTADO",e);
            parameters.put("TIPO_SOL",ts);
            parameters.put("FECHA_INICIO",fi);
            parameters.put("FECHA_FINAL",ff);
            parameters.put("DIR", urlMaestro1.toString());
            parameters.put("DIR1", urlMaestro2.toString());
            parameters.put("cod_user",18);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(urlMaestro); 
            System.out.println("realizo el jasper reporte");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);
            System.out.println("realizo el jasper print");
            JasperViewer.viewReport(jasperPrint, false);  
            System.out.println("realizo el jasper view");
            
        } catch (Exception ec) {
            System.out.println("Error Gravichimo: "+ec);
        }
    }
    
    private void BtnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnReporteActionPerformed
        int ts=-1;
        String e="ttt";
        Date fi,ff;
        if (RadEnvio1.isSelected())
            ts=0;
            //System.out.println("tipo sol: "+0);
            //MostrarReporteEnvioDocs();
        if (RadIngreso1.isSelected())
            ts=1;
            //System.out.println("tipo sol: "+1);
            //MostrarReporteIngMaterial();
        if (RadIngreso.isSelected())
            e="JUR";
            //System.out.println("estado: juridica");
        if (RadIngreso2.isSelected())
            e="PPTO";
            //System.out.println("estado: presupuesto");
        if (RadEnvio.isSelected())
            e="ALM1";
            //System.out.println("estado: almacenes");
        if (RadEnvio2.isSelected())
            e="ADQ";
        if (RadEnvio3.isSelected())
            e="C";
        if (RadEnvio4.isSelected())
            e="B";
            //System.out.println("estado: adquisiciones");
        //System.out.println("fecha inicio: "+DatFec_ini.getValue().toString());
        //System.out.println("fecha final: "+DatFec_fin.getValue().toString());
        fi=(Date) DatFec_ini.getValue();
        ff=(Date) DatFec_fin.getValue();
        if(ts==-1)
        {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de solicitud");
        }
        else{
        if(e.equals("ttt"))
        {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Estado");
        }else{
            mostrarreporteya(e,ts,fi,ff);
            //mostrarreporteya(e,ts,fi,ff);    
        }
        }
        
        //JD_Reporte1 r = new JD_Reporte1(this.menu,false,e,ts,fi,ff);
        //r.setVisible(true);
    }//GEN-LAST:event_BtnReporteActionPerformed

    private void RadIngreso2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadIngreso2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RadIngreso2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BtnGrpReporte;
    private javax.swing.JButton BtnReporte;
    private javax.swing.JButton BtnSalir;
    private net.sf.nachocalendar.components.DateField DatFec_fin;
    private net.sf.nachocalendar.components.DateField DatFec_ini;
    private javax.swing.JRadioButton RadEnvio;
    private javax.swing.JRadioButton RadEnvio1;
    private javax.swing.JRadioButton RadEnvio2;
    private javax.swing.JRadioButton RadEnvio3;
    private javax.swing.JRadioButton RadEnvio4;
    private javax.swing.JRadioButton RadIngreso;
    private javax.swing.JRadioButton RadIngreso1;
    private javax.swing.JRadioButton RadIngreso2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
