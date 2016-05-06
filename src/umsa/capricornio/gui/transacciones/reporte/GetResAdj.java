/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.reporte;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import umsa.capricornio.domain.Transaccion;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.utilitarios.herramientas.NumerosTextuales;

/**
 *
 * @author UMSA-JES
 */
public class GetResAdj {
    
    URL urlMaestro,urlImage,urlMaestro1,urlMaestro2,urlMaestro3,urlMaestro4,urlMaestro5;
    String dir_daf;
    public GetResAdj(){
        this.genera();
    }
    public void imprimePDF(URL url,Map parameters){
        JasperReport masterReport = null;
        try { masterReport = (JasperReport) JRLoader.loadObject(url);} 
        catch (JRException e) 
            { System.out.println("Error cargando el reporte maestro: " + e.getMessage());
              System.exit(3);
            }
        
        //parameters.put("imagen",urlImage.toString());
        //parameters.put("titulo",titulo);        

        JasperPrint masterPrint = null;
        try {
            System.out.println("Esta vivo weon¡!¡"+masterReport);
            masterPrint = JasperFillManager.fillReport(masterReport, parameters, new JREmptyDataSource());
        }
        catch (JRException e) {
        System.out.println("error nq "+e);
        }  
        System.out.println("el error debe estar por aqui---- "+masterPrint+" --sdfsd-- "+masterReport);
        JasperViewer.viewReport(masterPrint, false);
    }
    public void imprimePDF2(URL url,Map parameters){
        JasperReport masterReport = null;
        Connection conexion=null;
        try { 
            try {
                System.out.println("aqui ya vemos otro");
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException ex) {
                System.out.println("esto es un error");
                JOptionPane.showMessageDialog( null, "esto"+ex.getMessage());
                Logger.getLogger(GetResAdj.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                System.out.println("aqui ya vemos otro2");
                conexion = DriverManager.getConnection("jdbc:oracle:thin:@200.7.160.182:1521:ADQUI", "ADQUISICIONES", "4dqu1_c3n72al");
            } catch (SQLException ex) {
                System.out.println("esto es un error1");
                JOptionPane.showMessageDialog( null, "esto1"+ex.getMessage());
                Logger.getLogger(GetResAdj.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("es posible que el error este aqui");
            masterReport = (JasperReport) JRLoader.loadObject(url);
            System.out.println("es posible que el error este aqui2");
        } 
        catch (JRException e) 
        {
            System.out.println("Error cargando el reporte maestro: " + e.getMessage());
            JOptionPane.showMessageDialog( null, "esto2"+e.getMessage());
            System.exit(3);
        }
        
        //parameters.put("imagen",urlImage.toString());
        //parameters.put("titulo",titulo);        

        JasperPrint masterPrint = null;
        try {
            System.out.println("el error debe estar por aqui---- "+masterPrint+" --sdfsd-- "+masterReport);
            masterPrint = JasperFillManager.fillReport(masterReport, parameters, conexion);
            System.out.println("el error debe estar por aqui---- "+masterPrint+" --sdfsd-- "+masterReport);
        }
        catch (JRException e) {
            JOptionPane.showMessageDialog( null, e.getMessage());
            System.out.println("el error debe estar "+ e.getMessage());
        }
        //System.out.println("el error debe estar por aqui:---- "+masterPrint+" --sdfsd-- "+masterReport);
        JasperViewer.viewReport(masterPrint, false);
    }
    public void genera(){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            //dir_daf = puerto.getDatoGeneral("DIR_DAF");
            dir_daf = puerto.getDatoGeneral("DIR_DAF_2");
            
            System.out.println("DIR_DAF: "+dir_daf);
        } catch (Exception e) {
            System.out.println("Gravichimo error: "+e);
        }
    }
    public void ReporteTareas(int i,int j)
    {
        Map parameters=new HashMap();
        parameters.put("MENOR",i);
        parameters.put("MAYOR",j);
        RepTransaccion t1=new RepTransaccion();
        urlMaestro=t1.getClass().getResource("/umsa/capricornio/gui/reports/Tareas_Adicionales.jasper");
        this.imprimePDF2(urlMaestro, parameters);
    }
    public void getreporte500(int cod_t)
    {
        Map parameters=new HashMap();
        System.out.println("cod_t "+cod_t);
        parameters.put("cod_transaccion", cod_t);
        RepTransaccion t1=new RepTransaccion();
        urlMaestro=t1.getClass().getResource("/umsa/capricornio/gui/reports/FORMULARIO500.jasper");
        this.imprimePDF2(urlMaestro, parameters);
    }
    public void getreporte400(int cod_t)
    {
        Map parameters=new HashMap();
        parameters.put("cod_transaccion", cod_t);
        RepTransaccion t1=new RepTransaccion();
        urlMaestro=t1.getClass().getResource("/umsa/capricornio/gui/reports/FORMULARIO400.jasper");
        this.imprimePDF2(urlMaestro, parameters);
    }
    public void getreporte600(int cod_t)
    {
        Map parameters=new HashMap();
        parameters.put("cod_transaccion", cod_t);
        RepTransaccion t1=new RepTransaccion();
        urlMaestro=t1.getClass().getResource("/umsa/capricornio/gui/reports/FORMULARIO600.jasper");
        this.imprimePDF2(urlMaestro, parameters);
    }
    public void reporteDesierta(int cod_w,int cod_transaccion,int cod_trans_nro,String res_adm,String res_ini,String detalle,String invitados,String fecha_apert,String inf_tec,String inf_adq,String convocatoria,String modalidad)
    {
        Map parameters=new HashMap();
        parameters.put("RES_ADM", res_adm);
        parameters.put("RES_INI", res_ini);
        parameters.put("DETALLE", detalle);
        parameters.put("INVITADOS", invitados);
        parameters.put("FECHA_APERTURA", fecha_apert);
        parameters.put("INF_TEC", inf_tec);
        parameters.put("INF_ADQ", inf_adq);
        parameters.put("NRO_CONVOCATORIA", convocatoria);
        parameters.put("MODALIDAD", modalidad);
        parameters.put("cod_trans", cod_transaccion);
        parameters.put("DIR_DAF",this.dir_daf);
        RepTransaccion t1=new RepTransaccion();
        System.out.println("siii hasta aqui");
        if(cod_w==1)
        {
            urlMaestro=t1.getClass().getResource("/umsa/capricornio/gui/reports/desiertaBien.jasper");
        }
        if(cod_w==6)
        {
            urlMaestro=t1.getClass().getResource("/umsa/capricornio/gui/reports/desiertaServ.jasper");
        }
        if(cod_w==3)
        {
            urlMaestro=t1.getClass().getResource("/umsa/capricornio/gui/reports/desiertaCons.jasper");
        }
        if(cod_w==4)
        {
            urlMaestro=t1.getClass().getResource("/umsa/capricornio/gui/reports/desiertaObra.jasper");
        }
        System.out.println("siii hasta aqui");
        this.imprimePDF2(urlMaestro, parameters);
        
    }
    public void reporteResumen(int mes1,int mes2,int cod_user)
    {
        int cuantia=0;
        int mes=1,i=mes1;
        Map parameters = new HashMap();
        System.out.println("mes1="+mes1+" mes2="+mes2);
        RepTransaccion t1 = new RepTransaccion();
        urlMaestro=t1.getClass().getResource("/umsa/capricornio/gui/reports/RESUMENMENSUAL.jasper");
        while(i<=mes2)
        {
            if(mes==i)
            {
                try{
                    AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                    AdquiWS_PortType puerto = servicio.getAdquiWS();
                    String x,y;
                    if(i<10)
                    {
                        x="01/0"+i+"/2016";
                        y="01/0"+(i+1)+"/2016";
                        System.out.println(x+" siiiii"+y);
                    }
                    else
                    {
                        x="01/"+i+"/2016";
                        y="01/"+(i+1)+"/2016";
                        System.out.println(x+" siiiii"+y);
                    
                    }
                    
                    System.err.println("cod_user: "+cod_user);
                    System.err.println("x: "+x);
                    System.err.println("y: "+y);
//                    System.err.println("y: ");
                    
                    Map[] datos=puerto.obtienetotal(cod_user,x,y, 0, 50000,cuantia+1);
                    if(datos==null)
                    {
                        System.out.println("no hay datos");
                    }
                    else
                        System.err.println("wujuuuuu con datos!!!");
                    
                    System.err.println("B)");
                    System.out.println("aqui "+datos[0].get("COUNT").toString());
                    System.err.println("A)");
                    System.out.println("aqui "+datos[0].get("TOTAL").toString());
                    
                    
                    parameters.put("C"+i, datos[0].get("COUNT").toString());
                    parameters.put("M"+i, datos[0].get("TOTAL").toString());
                }catch(Exception e)
                {
                    System.out.println("esto de aqui es el error mas reciente "+e);
                }
                i++;
                mes++;
            }
            else
            {
                mes++;
            }
        }
        this.imprimePDF2(urlMaestro, parameters);
    }
    public void reporteppto(Date fi,Date ff,int cod_user,int estado)
    {
        Map parameters = new HashMap();
        parameters.put("FECHA_INICIO", fi);
        parameters.put("FECHA_FINAL", ff);
        parameters.put("COD_USER", cod_user);
        parameters.put("ESTADO", estado);        
        RepTransaccion t1 = new RepTransaccion();
    }
    
    public void imprimeinf_comicion(int cod_w,int gestion,int cod_transaccion,int cod_trans_nro,String detalle,String destino,String cargo,String modalidad,String notas,String nota_juridica,String citacion,String fec_cit,String fecha,String hora,String nro_informe,String fecha_inf,String presidente,String vocal1,String vocal2,String secretario,String nota_uni,String nota_ppto,String Resolucion_admi,String monto,String hoja_ruta,String nro,String proveedor)
    {
        System.out.println("aqui ya vemos");
        Map parameters=new HashMap();
        parameters.put("detalle",detalle);
        parameters.put("contratado",proveedor);
        parameters.put("destino",destino);
        parameters.put("cargo",cargo);
        parameters.put("modalidad",modalidad);
        parameters.put("notas",notas);
        parameters.put("nota_jur",nota_juridica);
        parameters.put("nro_cit",citacion);
        parameters.put("fecha_nota",fec_cit);
        parameters.put("fecha",fecha);
        parameters.put("hora",hora);
        parameters.put("nro_informe",nro_informe);
        parameters.put("fecha_informe",fecha_inf);
        parameters.put("presidente",presidente);
        parameters.put("vocal1",vocal1);
        parameters.put("vocal2",vocal2);
        parameters.put("cod_trans",cod_transaccion);
        parameters.put("cod_trans_nro",cod_trans_nro);
        parameters.put("secretario",secretario);
        parameters.put("nota_unidad",nota_uni);
        parameters.put("nota_ppto",nota_ppto);
        parameters.put("res_adm",Resolucion_admi);
        if(proveedor==null)
        {
            parameters.put("desierta", 1);
        }
        else
        {
            parameters.put("desierta", 0);
        }
        parameters.put("MONTO",monto);
        parameters.put("num", nro);
        parameters.put("hoja_ruta",hoja_ruta);
        parameters.put("DIR_DAF",this.dir_daf);
        System.out.println("aqui ya vemos2 "+cod_trans_nro+" esto es cod "+cod_transaccion);
        RepTransaccion t1=new RepTransaccion();
        if(cod_w==1)
        {
            System.out.println("aqui ya vemosss");
            urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/InfComisionBien.jasper");
            urlMaestro1 = t1.getClass().getResource("/umsa/capricornio/gui/reports/RO2.jasper");
            urlMaestro2 = t1.getClass().getResource("/umsa/capricornio/gui/reports/report2.jasper");
            urlMaestro3 = t1.getClass().getResource("/umsa/capricornio/gui/reports/report3.jasper");
            parameters.put("DIR3", urlMaestro3.toString());
            System.out.println("error3");
            parameters.put("DIR1", urlMaestro1.toString());
            System.out.println("error4");
            parameters.put("DIR2", urlMaestro2.toString());
            System.out.println("error5");
        }
        if(cod_w==3)
        {
            System.out.println("aqui ya vemosss1");
            urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/InfComisionCons.jasper");
            urlMaestro2 = t1.getClass().getResource("/umsa/capricornio/gui/reports/report2.jasper");
            parameters.put("DIR2", urlMaestro2.toString());
            System.out.println("error5");
        }
        if(cod_w==6)
        {
            System.out.println("aqui ya vemosss2");
            urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/InfComisionServ.jasper");
            System.out.println("error");
            urlMaestro2 = t1.getClass().getResource("/umsa/capricornio/gui/reports/report2.jasper");
            System.out.println("error1");
            parameters.put("DIR2", urlMaestro2.toString());
            System.out.println("error5");
        }
        if(cod_w==4)
        {
            System.out.println("aqui ya vemosss3");
            urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/InfComisionObra.jasper");
            urlMaestro1 = t1.getClass().getResource("/umsa/capricornio/gui/reports/RO2.jasper");
            urlMaestro2 = t1.getClass().getResource("/umsa/capricornio/gui/reports/report2.jasper");
            urlMaestro3 = t1.getClass().getResource("/umsa/capricornio/gui/reports/report3.jasper");
            parameters.put("DIR3", urlMaestro3.toString());
            System.out.println("error3");
            parameters.put("DIR1", urlMaestro1.toString());
            System.out.println("error4");
            parameters.put("DIR2", urlMaestro2.toString());
            System.out.println("error5");
        }
        System.out.println("error2");
        /*parameters.put("DIR1", urlMaestro1.toString());
        parameters.put("DIR2", urlMaestro2.toString());
        parameters.put("DIR3", urlMaestro3.toString());*/
        /*parameters.put("DIR3", urlMaestro3.toString());
        System.out.println("error3");
        parameters.put("DIR1", urlMaestro1.toString());
        System.out.println("error4");
        parameters.put("DIR2", urlMaestro2.toString());
        System.out.println("error5");*/
        System.out.println("aqui ya vemos3");
        System.out.println(urlMaestro.toString());
        this.imprimePDF2(urlMaestro, parameters);
        System.out.println("aqui ya vemos4");
    }
    
    public void ReporteUnidad(Date fi,Date ff,int cod_user,int cod_rol,int x2)
    {
        Map parameters = new HashMap();
        parameters.put("FECHA_INICIO", fi);
        parameters.put("FECHA_FINAL", ff);
        //int x=18;
        parameters.put("COD_USER", cod_user);
        RepTransaccion t1 = new RepTransaccion();
        if(cod_rol==2)
        {
           urlMaestro2 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompraSA.jasper");
           urlMaestro4 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompraCA.jasper");
           urlMaestro5 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra3.jasper");
           parameters.put("DIR1", urlMaestro2.toString());
           parameters.put("DIR2", urlMaestro4.toString());
           parameters.put("DIR5", urlMaestro5.toString());
           System.out.println("jaja");
        }
        else{
        urlMaestro1 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra.jasper");
        urlMaestro2 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompraS.jasper");
        urlMaestro3 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompraO.jasper");
        urlMaestro4 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompraC.jasper");
        urlMaestro5 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra3.jasper");
        parameters.put("DIR1", urlMaestro1.toString());
        parameters.put("DIR2", urlMaestro2.toString());
        parameters.put("DIR3", urlMaestro3.toString());
        parameters.put("DIR4", urlMaestro4.toString());
        parameters.put("DIR5", urlMaestro5.toString());
        System.out.println("esto es x2 "+x2);
        if(x2==0)
        {
            parameters.put("MONTO_MENOR", 0);
            parameters.put("MONTO_MAYOR", 50000);
        }
        if(x2==2)
        {
            parameters.put("MONTO_MENOR", 0);
            parameters.put("MONTO_MAYOR", 20000);
        }
        if(x2==1)
        {
            parameters.put("MONTO_MENOR", 20000);
            parameters.put("MONTO_MAYOR", 50000);
        }
        System.out.println("jaja2");
        }
        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompraP.jasper");
        this.imprimePDF2(urlMaestro, parameters);
    }
    
    public void ReporteSimplificado(Date fi,Date ff,int cod_user,int cod_rol,int x2,int dm1,int dm2)
    {
        Map parameters = new HashMap();
        parameters.put("FECHA_INICIO", fi);
        parameters.put("FECHA_FINAL", ff);
        //int x=18;
        parameters.put("COD_USER", cod_user);
        parameters.put("DIAS_MENOS", dm1);
        parameters.put("DIAS_MENOS2", dm2);
        System.out.println("fi="+fi+" ff="+ff+" cod_user="+cod_user+" dm1="+dm1+" dm2="+dm2);
        RepTransaccion t1 = new RepTransaccion();
        if(cod_rol==2)
        {
           urlMaestro2 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompraSA.jasper");
           urlMaestro4 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompraCA.jasper");
           urlMaestro5 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra3.jasper");
           parameters.put("DIR1", urlMaestro2.toString());
           parameters.put("DIR2", urlMaestro4.toString());
           parameters.put("DIR5", urlMaestro5.toString());
           System.out.println("jaja");
        }
        else{
        urlMaestro1 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompraSimp.jasper");
        urlMaestro2 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompraSSimp.jasper");
        urlMaestro3 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompraOSimp.jasper");
        urlMaestro4 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompraCSimp.jasper");
        urlMaestro5 = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompra3.jasper");
        parameters.put("DIR1", urlMaestro1.toString());
        parameters.put("DIR2", urlMaestro2.toString());
        parameters.put("DIR3", urlMaestro3.toString());
        parameters.put("DIR4", urlMaestro4.toString());
        parameters.put("DIR5", urlMaestro5.toString());
        System.out.println("esto es x2 "+x2);
        int a=0,b=0;
        if(x2==0)
        {
            parameters.put("MONTO_MENOR", 0);
            parameters.put("MONTO_MAYOR", 50000);
            a=0;
            b=50000;
        }
        if(x2==2)
        {
            parameters.put("MONTO_MENOR", 0);
            parameters.put("MONTO_MAYOR", 20000);
            a=0;
            b=20000;
        }
        if(x2==1)
        {
            parameters.put("MONTO_MENOR", 20000);
            parameters.put("MONTO_MAYOR", 50000);
            a=20000;
            b=50000;
        }
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT);
            String f1=df1.format(fi);
            String f2=df1.format(ff);
            System.out.println("los datos: user->"+cod_user+" fec_ini->"+f1+" fec_fin->"+f2+" a->"+a+" b->"+b);          
            Map[] datos=puerto.getUsuarioRol(cod_user);
            //Map[] datos=puerto.obtienetotal(cod_user,f1,f2,a,b);
            //Map[] datos=puerto.obtienetotal(5,"01/05/2015","01/06/15", 0, 50000);
            //Map[] datos1=puerto.obtienetotal(18,"01/04/2015","01/05/2015",0,50000);
            if(datos==null)
            {
                System.out.println("esto esta raro");
            }
            /*String t=datos[0].get("COUNT").toString();
            System.out.println("t:"+t);
            String t11=datos[0].get("TOTAL").toString();
            System.out.println("t: "+t+" T11:"+t11);*/
            parameters.put("USUARIO", datos[0].get("USUARIO").toString());
        }
        catch(Exception e)
        {
            parameters.put("TOTAL","ERROR");
            System.out.println("esto es un error rayos "+e);
        }
        //parameters.put("TOTAL", t);
        System.out.println("jaja2");
        }
        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReporteCompraPSimp.jasper");
        //urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/RepPrincipalSimp.jasper");
        this.imprimePDF2(urlMaestro, parameters);
    }
    
    public void invitacion(int cod_w,String nota,String detalle,String destino,String nro_propuesta,String fecha,String fecha_reunion,String jefe_adq,String nombre,String casa,String metodo)
    {
        Map parameters = new HashMap();
        parameters.put("NOTA", nota);
        parameters.put("DETALLE", detalle);
        parameters.put("NRO_PROPUESTA", nro_propuesta);
        parameters.put("DESTINO", destino);
        parameters.put("FECHA", fecha);
        parameters.put("JEFE_ADQ", jefe_adq);
        parameters.put("METODO", metodo);
        if(cod_w==4)
        {
            parameters.put("FECHA_REUNION", fecha_reunion);
        }
        if(casa.equals("Sin Nombre Comercial"))
        {
            parameters.put("INVITADO", nombre);
        }
        else
        {
            parameters.put("INVITADO", casa);
        }
        RepTransaccion t1 = new RepTransaccion();
        if(cod_w==1)
        {
            urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/invitacionBien.jasper");
        }
        if(cod_w==6)
        {
            urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/invitacionServ.jasper");
        }
        if(cod_w==3)
        {
            urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/invitacionCons.jasper");
        }
        if(cod_w==4)
        {
            urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/invitacionObra.jasper");
        }
        
        this.imprimePDF(urlMaestro, parameters);
        
    }
    public void reporte15(String hr,String ep,String c,String det,String dest,String obj,String prev,String m,String part,String sc,String cot,String prov,String dias,String cite,String nro_res,int estado,String doc,String fec_ini,String feccha_hr,String fecha_nota)
    {
        Map parameters = new HashMap();
        parameters.put("HOJA_RUTA", hr);
        parameters.put("ENVIADO_POR", ep);
        parameters.put("CARGO", c);
        parameters.put("DETALLE", det);
        parameters.put("DESTINO", dest);
        parameters.put("OBJETIVO", obj);
        parameters.put("PREVENTIVO", prev);
        parameters.put("MONTO", m);
        parameters.put("PARTIDA", part);
        parameters.put("SOL_COMPRA", sc);
        parameters.put("COTIZACION", cot);
        parameters.put("PROVEEDOR", prov);
        parameters.put("DIAS", dias);
        parameters.put("CITE", cite);
        parameters.put("NRO_RES", nro_res);
        parameters.put("DIR_DAF", this.dir_daf);
        parameters.put("ESTADO", estado);
        parameters.put("DOCUMENTOS", doc);
        parameters.put("FECHA", fec_ini);
        parameters.put("FECHA_HR", feccha_hr);
        parameters.put("FECHA_NOTA", fecha_nota);
        RepTransaccion t1 = new RepTransaccion(); 
        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReportMay15Cons.jasper");
        this.imprimePDF(urlMaestro, parameters);
    }
    public void ReporteAdjServ(String det_adc,String res_adm,String fecha_cc,String inf_div_adq,String proveedor,String detalle,String num_resol,String det_conc_prop,String modo_eval,String destino,String cargo,String actividad,String prop_proveedor,String proponentes, String cuce, String monto){
        Map parameters = new HashMap();
        
        //parameters.put("num_res_adm", res_adm);
        parameters.put("INF-TEC", det_adc);
        parameters.put("num_res_adm", res_adm);
        //parameters.put("adc", det_adc);
        parameters.put("fecha_calificacion", fecha_cc);
        parameters.put("inf_div_adqui", inf_div_adq);
        parameters.put("proveedor", proveedor);
        parameters.put("detalle", detalle);
        parameters.put("num_resol", num_resol);
        
        parameters.put("det_conc_prop", det_conc_prop);
        parameters.put("modo_eval", modo_eval);
        parameters.put("destino", destino);
        parameters.put("cargo", cargo);
        parameters.put("actividad", actividad);
        parameters.put("prop_proveedor", prop_proveedor);
        parameters.put("proponentes", proponentes);
        parameters.put("DIR_DAF", this.dir_daf);
        parameters.put("CUCE", cuce);
        System.out.println(cuce+"   sdfjsdfoisdjfoisjdoifjds");
        parameters.put("MONTO", monto);
        
        RepTransaccion t1 = new RepTransaccion(); 
        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ResAdjServ.jasper");
        this.imprimePDF(urlMaestro, parameters);
    }
    public void ReporteAdjBien(String res_adm,String fecha_cc,String inf_div_adq,String proveedor,String detalle,String num_resol,String det_conc_prop,String modo_eval,String destino,String cargo,String actividad,String prop_proveedor,String proponentes, String cuce, String monto, String adc,int cod_trans,int cod_trans_nro){
        Map parameters = new HashMap();
        
        parameters.put("num_res_adm", res_adm);
        parameters.put("inf_tec", adc);
        parameters.put("fecha_calificacion", fecha_cc);
        parameters.put("inf_div_adqui", inf_div_adq);
        parameters.put("proveedor", proveedor);
        parameters.put("detalle", detalle);
        parameters.put("num_resol", num_resol);
        
        parameters.put("det_conc_prop", det_conc_prop);
        parameters.put("modo_eval", modo_eval);
        parameters.put("destino", destino);
        parameters.put("cargo", cargo);
        parameters.put("actividad", actividad);
        parameters.put("prop_proveedor", prop_proveedor);
        parameters.put("proponentes", proponentes);
        parameters.put("DIR_DAF", this.dir_daf);
        parameters.put("CUCE", cuce);
        System.out.println(cuce+"   sdfjsdfoisdjfoisjdoifjds");
        parameters.put("MONTO", monto);
        parameters.put("cod_trans", cod_trans);
        parameters.put("cod_trans_nro", cod_trans_nro);
        System.out.println("yuuuuuu");
        
        RepTransaccion t1 = new RepTransaccion(); 
        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ResAdjBienNuevo.jasper");
        urlMaestro1 = t1.getClass().getResource("/umsa/capricornio/gui/reports/uniendo_reportes.jasper");
        urlMaestro2 = t1.getClass().getResource("/umsa/capricornio/gui/reports/grafico_AB.jasper");
        urlMaestro3 = t1.getClass().getResource("/umsa/capricornio/gui/reports/GRAFICOS_ANPE_BIENES.jasper");
        parameters.put("DIR1",urlMaestro1.toString());
        parameters.put("DIR2",urlMaestro2.toString());
        parameters.put("DIR3",urlMaestro3.toString());
        this.imprimePDF2(urlMaestro, parameters);
    }
    
    public void ReporteAdjObra(String res_adm,String fecha_cc,String inf_div_adq,String proveedor,String detalle,String num_resol,String det_conc_prop,String modo_eval,String destino,String cargo,String actividad,String prop_proveedor,String proponentes, String cuce, String monto,String adc,String a,String b,int cod,int total){
        Map parameters = new HashMap();
        parameters.put("cod", cod);
        parameters.put("num_res_adm", res_adm);
        parameters.put("INF_TEC", adc);
        parameters.put("emitido_por", actividad);
        parameters.put("fecha_calificacion", fecha_cc);
        parameters.put("inf_div_adqui", inf_div_adq);
        parameters.put("proveedor", proveedor);
        parameters.put("detalle", detalle);
        parameters.put("num_resol", num_resol);
        
        parameters.put("det_conc_prop", det_conc_prop);
        parameters.put("modo_eval", modo_eval);
        //parameters.put("fila", a);
        parameters.put("cargo", cargo);
        //parameters.put("cumple", b);
        parameters.put("prop_proveedor", prop_proveedor);
        parameters.put("proponentes", proponentes);
        parameters.put("DIR_DAF", this.dir_daf);
        parameters.put("CUCE", cuce);
        
        //parameters.put("total",total);
        System.out.println(cuce+"   sdfjsdfoisdjfoisjdoifjds");
        parameters.put("MONTO", monto);
        System.out.println(cod);
        System.out.println(res_adm);
        System.out.println(adc);
        System.out.println(actividad);
        System.out.println(fecha_cc);
        System.out.println(inf_div_adq);
        System.out.println(proveedor);
        System.out.println(detalle);
        System.out.println(num_resol);
        System.out.println(det_conc_prop);
        System.out.println(modo_eval);
        System.out.println(cargo);
        System.out.println(prop_proveedor);
        System.out.println(proponentes);
        System.out.println(cuce);
        RepTransaccion t1 = new RepTransaccion(); 
        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ResAdjObra.jasper");
        urlMaestro1 = t1.getClass().getResource("/umsa/capricornio/gui/reports/RO2.jasper");
        urlMaestro2 = t1.getClass().getResource("/umsa/capricornio/gui/reports/report2.jasper");
        urlMaestro3 = t1.getClass().getResource("/umsa/capricornio/gui/reports/report3.jasper");
        /*String path = urlMaestro1.getPath();
        File temp=null,temp1=null,file=null;
        try{
        file = new File("C:\\Users\\javieralex\\Documents\\tempcapri");
        file.mkdirs();
        file.setWritable(true);
        String archivo = file.getCanonicalPath() + "\\RO2.jasper";
        String archivo1 = file.getCanonicalPath() + "\\report2.jasper";
        temp = new File(archivo);
        temp1 = new File(archivo1);
        InputStream is = this.getClass().getResourceAsStream("/umsa/capricornio/gui/reports/RO2.jasper");
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
        catch(Exception e){System.out.println("Problema abriendo el pdf de erfc");}
        try{
            String archivo1 = file.getCanonicalPath() + "\\report2.jasper";
            temp1 = new File(archivo1);
            InputStream is1 = this.getClass().getResourceAsStream("/umsa/capricornio/gui/reports/report2.jasper");
            FileOutputStream archivoDestino1 = new FileOutputStream(temp1);
            FileWriter fw1 = new FileWriter(temp1);
            byte[] buffer1 = new byte[512*1024];
            int nbLectura1;
            while ((nbLectura1 = is1.read(buffer1)) != -1)
                archivoDestino1.write(buffer1, 0, nbLectura1);    
            fw1.close();
            archivoDestino1.close();
            is1.close();
        }catch(Exception e){
        
        }
        System.out.println(temp.getPath()+" orale!!!!");
        System.out.println(temp1.getPath()+" orale!!!!");
        System.out.println(file.getAbsolutePath()+" orales!!!!");*/
        //JOptionPane.showMessageDialog( null, file.getPath());
        //file = file.getAbsoluteFile();
        //ServletContext scontext = (ServletContext)context.getExternalContext().getContext(); 
        //parameters.put("SUBREPORT_DIR", scontext.getRealPath("CAMINHODODIRETORIORELATIVOAQUI/")+"/"); 
        //System.out.println("muestra amigo "+System.getProperty("user.dir")+"\\umsa\\capricornio\\gui\\reports"); 
        parameters.put("DIR", urlMaestro3.toString());
        parameters.put("DIR1", urlMaestro1.toString());
        parameters.put("DIR2", urlMaestro2.toString());
        this.imprimePDF2(urlMaestro, parameters);
        System.out.println(cuce+"   sdfjsdfoisdjfoisjdoifjds");
    }
    
    public void ReporteAdjConsul(String res_adm,String fecha_cc,String inf_div_adq,String proveedor,String detalle,String num_resol,String det_conc_prop,String modo_eval,String destino,String cargo,String actividad,String prop_proveedor,String proponentes, String cuce, String monto){
        Map parameters = new HashMap();
        
        parameters.put("ResAdm", res_adm);
        //parameters.put("adc", det_adc);
        parameters.put("FechaComisionCalf", fecha_cc);
        parameters.put("InfAdq", inf_div_adq);
        parameters.put("Profesional", proveedor);
        parameters.put("Titulo_trans", detalle);
        parameters.put("Num_Resol", num_resol);
        
        parameters.put("ADCAnpe", det_conc_prop);
        parameters.put("InfHP", modo_eval);
        parameters.put("Destino", destino);
        parameters.put("CARGO", cargo);
        parameters.put("NotasAdqInv", actividad);
        //parameters.put("prop_proveedor", prop_proveedor);
        //parameters.put("proponentes", proponentes);
        parameters.put("DIR_DAF", this.dir_daf);
        parameters.put("CUCE", cuce);
        System.out.println(cuce+"   sdfjsdfoisdjfoisjdoifjds");
        parameters.put("MONTO", monto);
        
        
        RepTransaccion t1 = new RepTransaccion(); 
        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ResAdjCons.jasper");
        this.imprimePDF(urlMaestro, parameters);
    }
    public void ReporteContratoGenerico(int cod_transaccion,int cod_w,String cuantia,int sw_servicio,int sw_consultoria,double monto){
        Map parameters = new HashMap();
        RepTransaccion t1 = new RepTransaccion(); 
        
//        parameters.put("cod_transaccion", cod_transaccion);
        System.err.println("-------- el cod_w: "+cod_w+", cuantia: "+cuantia+", sw_servicio: "+sw_servicio+", sw_consultoria: "+sw_consultoria);
        
        switch (cod_w) {
            
            case 1: 
//                this.JL_TITULO.setText("BIENES - "+this.cuantia);
                
                if(cuantia.trim().equals("COMPRA MENOR"))
                    urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_bienes_menor.jasper");
                if(cuantia.trim().equals("ANPE"))
                    urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_bienes.jasper");
                    
                break;
            /*case 2: 
//                this.JL_TITULO.setText("PEDIDO MATERIALES - "+this.cuantia);
                if(cuantia.trim().equals("COMPRA MENOR"))
                    urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_bienes.jasper");
                if(cuantia.trim().equals("ANPE"))
                    urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_bienes.jasper");
                break;*/
            case 3: 
//                this.JL_TITULO.setText("CONSULTORIAS - "+this.cuantia);
                if(cuantia.trim().equals("COMPRA MENOR")){
                    if(sw_consultoria == 1)
                        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_consultoria_linea_menor.jasper");
                    else
                        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_consultoria_producto_menor.jasper");
                }
                    //urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_consultoria_linea.jasper");
                if(cuantia.trim().equals("ANPE")){
                    if(sw_consultoria == 1)
                        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_consultoria_linea.jasper");
                    else if(sw_consultoria == 2)
                        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_consultoria_producto.jasper");
                }
                    //urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_consultoria_linea.jasper");
                break;
            case 4: 
//                this.JL_TITULO.setText("OBRAS - "+this.cuantia);
                if(cuantia.trim().equals("COMPRA MENOR"))
                    urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_bienes_menor.jasper");
                if(cuantia.trim().equals("ANPE"))
                    urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_bienes.jasper");
                break;
           /* case 5: 
//                this.JL_TITULO.setText("COMPRAS MAYORES - "+this.cuantia);
                if(cuantia.trim().equals("COMPRA MENOR"))
                    urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_bienes3.jasper");
                if(cuantia.trim().equals("ANPE"))
                    urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_bienes3.jasper");
                break;*/
            case 6: 
//                this.JL_TITULO.setText("SERVICIOS - "+this.cuantia);
                
                
                    
                if(cuantia.trim().equals("COMPRA MENOR")){
                    if(sw_servicio == 1)
                        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_servicios_generales_menor.jasper");
                    else if(sw_servicio == 2)
                        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_servicios_26990_menor.jasper");
                }
                    
                if(cuantia.trim().equals("ANPE"))
                {
                    if(sw_servicio == 1)
                        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_servicios_generales.jasper");
                    else
                        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_servicios_26990.jasper");
                }
                    
                break;
            default: 
                System.err.println("a caray");
                break;
                  
        }
        
//        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelo_sabs_servicios.jasper");
               
        parameters.put("cod_transaccion", cod_transaccion);
        
        urlMaestro1 = t1.getClass().getResource("/umsa/capricornio/gui/reports/reporte_items.jasper");
        parameters.put("DIR1", urlMaestro1.toString());
        
        //System.err.println(TotalTexto(String.valueOf(monto)));
        parameters.put("monto_num", String.valueOf(monto));
        parameters.put("monto_lit", TotalTexto(String.valueOf(monto)));
        
        System.err.println("vamos a ver : "+urlMaestro);
        
        this.imprimePDF2(urlMaestro, parameters);
    }
    public void Reporte (String ResAdm,String Num_Resol, String Titulo_trans, String NotasAdqInv, String ADCAnpe, String Convocatoria, String FechaComisionCalf, String InfAdq, String InfHP, String Profesional, String Destino, String Actividad,int cod_trans_nro, int cod_w)
    {
        Transaccion trans = new Transaccion();
        trans.setDetalle("Transporte y deposito de remesas de las cajas del Departamento de Tesoro Universitario");
        
        //System.out.println("Probando --> "+prueba);
        RepTransaccion t1 = new RepTransaccion(); 
        Map parameters = new HashMap();
        
        /*
        parameters.put("Num_Resol", "60/2014");
        parameters.put("Titulo_trans", "Consultor(a) por Producto en redes inalámbricas para realizar el desarrollo y mantenimiento de las soluciones WIFI de la UMSA");
        parameters.put("ResAdm", "53/14");
        parameters.put("NotasAdqInv", "Nº384, 385 y 386/14");
        parameters.put("Convocatoria", "Primera");
        parameters.put("FechaComisionCalf", "fecha 14 de febrero");
        parameters.put("InfAdq", "15/14");
        parameters.put("InfHP", "31/14");
        parameters.put("Profesional", "Ing. Oscar Sergio Blass Chambi");
        parameters.put("Destino", "Departamento de Tecnologías de Información y Comunicación");
        parameters.put("Actividad", "Fortalecimiento del WIFI UMSA del Programa del Sistema UMSATIC I, TGN – Coparticipación Tributaria");
        */
        parameters.put("Num_Resol", Num_Resol);
        parameters.put("Titulo_trans", Titulo_trans);
        parameters.put("ResAdm", ResAdm);
        parameters.put("NotasAdqInv", NotasAdqInv);
        parameters.put("ADCAnpe", ADCAnpe);
        parameters.put("Convocatoria", Convocatoria);
        parameters.put("FechaComisionCalf", FechaComisionCalf);
        parameters.put("InfAdq", InfAdq);
        parameters.put("InfHP", InfHP);
        parameters.put("Profesional", Profesional);
        parameters.put("Destino", Destino);
        parameters.put("Actividad", Actividad);
        
        System.out.println("El cod_w es --> "+cod_w);
        switch(cod_w){
            case 6:
                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ResAdjServ.jasper");
                break;
            case 3:
                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ResAdjCons.jasper");
                break;
            case 4:
                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ResAdjObra.jasper");
                break;
            case 7:
                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ResAdjBien.jasper");
                break;
        }
        
        //urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/IngresoMaterial.jasper");
        
         //JRBeanCollectionDataSource ds =new JRBeanCollectionDataSource(aux);  
                               
        JasperReport masterReport = null;
        try { masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);} 
        catch (JRException e) 
            { System.out.println("Error cargando el reporte maestro: " + e.getMessage());
              System.exit(3);
            }
        
        //parameters.put("imagen",urlImage.toString());
        //parameters.put("titulo",titulo);        

        JasperPrint masterPrint = null;
        try {
            System.out.println("Esta vivo weon¡¡");
            masterPrint = JasperFillManager.fillReport(masterReport, parameters, new JREmptyDataSource());
        }
        catch (JRException e) {}  
        
        JasperViewer.viewReport(masterPrint, false);
                
        /*
        final String reportSource = getClass().getClassLoader().getResource(reportName).getPath();
         
        final JasperDesign jd = JRXmlLoader.load(reportSource);
 
        final JasperReport report = JasperCompileManager.compileReport(jd);
 
        final JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
 
        final String reportTarget = reportSource.substring(0,reportSource.lastIndexOf('/')).concat(reportName).concat(".pdf");
         
        JasperExportManager.exportReportToPdfFile(print, reportTarget);
                */
                
    }
    String TotalTexto(String total){
        double m=Double.parseDouble(total);                          
        
        long valor =(long)m;
        double var= m-valor;
        
        DecimalFormat formato_decimal = new DecimalFormat("0.00");        
        String decimal =formato_decimal.format(var);
      
        String montoLetra=NumerosTextuales.NumTextuales(valor);
        
        if ((m>=1000 && m<2000) || (m>=1000000 && m<2000000)){ montoLetra="UN "+montoLetra;}        
        if (var ==0.0) montoLetra=montoLetra+" 00/100";
        else montoLetra=montoLetra+" "+decimal.substring(2, 4)+"/100";
       return montoLetra;
   }/*
   public static void main(String arg[]) {
       System.out.println("Wolasss");
       GetResoluciones x = new  GetResoluciones();
       x.Reporte("DPTO.T.U. Nº115/14", "Depto.Ppto.Nº404/14", "Jefe del Departamento de Tesoro Universitario","Detalle",4,0);
       System.out.println("Wolass2");
   }*/

    
}
