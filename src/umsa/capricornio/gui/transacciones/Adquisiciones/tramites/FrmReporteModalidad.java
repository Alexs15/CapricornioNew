/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrmReporteModalidad.java
 *
 * Created on 29-11-2011, 03:04:17 PM
 */
package umsa.capricornio.gui.transacciones.Adquisiciones.tramites;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.rpc.ServiceException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import umsa.capricornio.domain.Transaccion;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.reporte.RepModalidad;

/**
 *
 * @author julian
 */
public class FrmReporteModalidad extends javax.swing.JInternalFrame {

    FrmMenu menu;
    private Runtime r;
    /** Creates new form FrmReporteModalidad */
    public FrmReporteModalidad(FrmMenu menu) {
        this.menu=menu;
        initComponents();
    }
    
    void MostrarReporte(){
        List list=new ArrayList();
        SimpleDateFormat form =new SimpleDateFormat("dd/MM/yyyy");
        String fec_ini="'"+form.format(DatFec_ini.getValue())+"'";
        String fec_fin="'"+form.format(DatFec_fin.getValue())+" 23:59:59'";
        
        String cuantia = CmbModalidad.getSelectedItem().toString();
        String []dato= cuantia.split(" - ");
        String cod_cuantia= dato[0];
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getReporteAdjudicaciones(fec_ini,fec_fin,2011,Integer.parseInt(cod_cuantia));
            
            if (datos!=null){                 
                Map map = new HashMap();
                for (int f=0;f<datos.length;f++){
                    Transaccion trans = new Transaccion();                    
                    trans.setNro_propuesta(datos[f].get("NRO_PROPUESTA").toString());                    
                    trans.setUnidad_sol(datos[f].get("DE").toString());
                    trans.setFondo(datos[f].get("FONDO").toString());
                    trans.setCantidad_pedido(Integer.parseInt(datos[f].get("CANTIDAD_PEDIDO").toString()));
                    trans.setDetalle(datos[f].get("DETALLE").toString());
                    trans.setMonto_ppto(Double.parseDouble(datos[f].get("MONTO_PPTO").toString()));
                    trans.setHoja_ruta(datos[f].get("HOJA_RUTA").toString());
                    trans.setInf_comision(datos[f].get("INF_COMISION").toString());
                    trans.setResolucion_adm(datos[f].get("RESOLUCION_ADM").toString());
                    trans.setPrecio_unit(Double.parseDouble(datos[f].get("PRECIO_UNIT").toString()));
                    trans.setCasa_comercial(datos[f].get("CASA_COMERCIAL").toString());                                        
                    trans.setCuantia(datos[f].get("CUANTIA").toString());                                        
                    trans.setDel(Double.parseDouble(datos[f].get("DEL").toString()));
                    trans.setHasta(Double.parseDouble(datos[f].get("HASTA").toString()));
                    trans.setTipo_contrato(datos[f].get("TIPO_CONTRATO").toString());                                        
                    list.add(trans);
                } 
                RepModalidad rep = new RepModalidad();
                rep.Reporte(list,form.format(DatFec_ini.getValue()),form.format(DatFec_fin.getValue()));
            }                         
            /*for (int i = 0; i < list.size(); i++) {
                Transaccion aux = (Transaccion) list.get(i);t           
                System.out.println(aux.getNro_gestion()+" "+aux.getFecha_creacion()+" "+ aux.getFecha_envio()+" "+aux.getUnidad_sol()+" "+aux.getUnidad_des()+" "+aux.getUsuario_sol()+" "+aux.getUnidad_medida()+" "+aux.getCantidad_pedido()+" "+aux.getTipo_item()+" "+aux.getArticulo()+" "+aux.getDetalle_solicitud());
            }  */      
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println("error 1:"+e);} 
        catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,"LA ORDEN NO FUE APROBADA O \n NO ELIJIO UNA FILA PARA PODER IMPRIMIR EL REPORTE "+e,"SYSTEM CAPRICORN",
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        DatFec_ini = new net.sf.nachocalendar.components.DateField();
        DatFec_fin = new net.sf.nachocalendar.components.DateField();
        jLabel3 = new javax.swing.JLabel();
        CmbModalidad = new javax.swing.JComboBox();
        BtnReporte = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();

        setTitle("REPORTE PRESUPUESTOS");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("DEL :");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 20, 50, 20);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("AL :");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(200, 20, 50, 20);

        DatFec_ini.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        getContentPane().add(DatFec_ini);
        DatFec_ini.setBounds(80, 20, 100, 18);

        DatFec_fin.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        getContentPane().add(DatFec_fin);
        DatFec_fin.setBounds(260, 20, 100, 18);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("MODALIDAD :");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(30, 60, 90, 20);

        CmbModalidad.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        getContentPane().add(CmbModalidad);
        CmbModalidad.setBounds(130, 60, 190, 20);

        BtnReporte.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        BtnReporte.setText("Ver Reporte");
        BtnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnReporteActionPerformed(evt);
            }
        });
        getContentPane().add(BtnReporte);
        BtnReporte.setBounds(50, 110, 120, 23);

        BtnCancelar.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        BtnCancelar.setText("Cancelar");
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnCancelar);
        BtnCancelar.setBounds(210, 110, 120, 23);

        setBounds(0, 0, 422, 178);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnReporteActionPerformed
        //MostrarReporte();
        int ts=-1;
        String e="PPTO";
        Date fi,ff;
        ts=CmbModalidad.getSelectedIndex();
        fi=(Date) DatFec_ini.getValue();
        ff=(Date) DatFec_fin.getValue();
        System.out.println("ts "+ts+" fi "+fi+" ff "+ff+" e "+e);
        mostrarreporteya(e,ts,fi,ff);
    }//GEN-LAST:event_BtnReporteActionPerformed

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        CerrarFrame();
    }//GEN-LAST:event_BtnCancelarActionPerformed

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
            urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra.jasper");
            urlMaestro1 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra2.jasper");
            urlMaestro2 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra3.jasper");
            parameters.put("ESTADO",e);
            parameters.put("TIPO_SOL",ts);
            parameters.put("FECHA_INICIO",fi);
            parameters.put("FECHA_FINAL",ff);
            parameters.put("DIR", urlMaestro1.toString());
            parameters.put("DIR1", urlMaestro2.toString());
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
    
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        try{
            /*AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getModalidad();            
            if (datos!=null){                 
                Map map = new HashMap();
                for (int f=0;f<datos.length;f++){
                    CmbModalidad.addItem(datos[f].get("COD_CUANTIA").toString()+" - "+ datos[f].get("CUANTIA").toString());                    
                }                 
            }*/
            CmbModalidad.addItem("DESCARGO");
            CmbModalidad.addItem("NORMAL");
        }
        /*catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println("error 1:"+e);} 
        catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,"LA ORDEN NO FUE APROBADA O \n NO ELIJIO UNA FILA PARA PODER IMPRIMIR EL REPORTE "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }*/
        catch(Exception e)
        {
            System.out.println("error nouououou");
        }
    }//GEN-LAST:event_formInternalFrameOpened

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnReporte;
    private javax.swing.JComboBox CmbModalidad;
    private net.sf.nachocalendar.components.DateField DatFec_fin;
    private net.sf.nachocalendar.components.DateField DatFec_ini;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
