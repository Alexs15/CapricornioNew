/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.gui.transacciones.reporte;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.rpc.ServiceException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import static umsa.capricornio.gui.menu.FrmMenu.usuario;
import umsa.capricornio.utilitarios.herramientas.NumerosTextuales;

import javax.swing.JFrame;
import umsa.capricornio.domain.Transaccion;

/**
 *
 * @author julian
 */
public class RepTransaccion {     
      
    URL urlMaestro,urlImage,firma_usr,firma2,firma_rpa;
    private String usuariox;
    public String generaUsuario(int cod_transaccion){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            return puerto.getNombreUsuario1(String.valueOf(cod_transaccion));
            //System.out.println("El codigo de usuario es: "+cod_usuario);
        } catch (Exception e) {
            System.out.println("El error es: "+e);
            return null;
        }
    }
    public void Reporte2(List aux,String titulo,int cod_tramite,int cod_trans_nro,int cod_almacen,String doc)
    {
        this.usuariox = this.generaUsuario(cod_trans_nro);
        System.out.println("Generando Reporte, del tipo --> "+titulo);
        RepTransaccion t1 = new RepTransaccion(); 
        Map parameters = new HashMap();
        if (cod_tramite==2){
            this.usuariox=usuario;
            if(titulo.equals("FORM. DE ADJUDICACION"))
            {
                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/Notificacion.jasper");
                parameters.put("DOC", doc);
            }
            else
            {
                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/OrdenCompra.jasper");
            }
            try{ AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();                  
                Map[] datos= puerto.getTotal(cod_trans_nro);
                if (datos!=null){
                    //System.out.println("............................ El total es: "+datos[0].get("TOTAL").toString());
                    //System.out.println("............................ El total redondeado es: "+this.Redondear(datos[0].get("TOTAL").toString(),2));
                    parameters.put("TxtTotal",TotalTexto(this.Redondear(datos[0].get("TOTAL").toString(),2)));
                }
                System.out.println(cod_trans_nro);
                Map[] datos1= puerto.getDias(cod_trans_nro);
                if (!datos1[0].get("DIAS").toString().equals("")){
                    System.out.println(datos1[0].get("DIAS").toString());
                    //System.out.println("............................ El total es: "+datos[0].get("TOTAL").toString());
                    //System.out.println("............................ El total redondeado es: "+this.Redondear(datos[0].get("TOTAL").toString(),2));
                    int tt=Integer.parseInt(datos1[0].get("DIAS").toString());
                    System.err.println("eto nya "+tt);
                    parameters.put("dias", tt);
                }
                else
                { 
                    Map[] dd=puerto.getEst(cod_trans_nro);
                    if(dd[0].get("ESTADO").toString().equals("PPTO") || dd[0].get("ESTADO").toString().equals("ADQ") || dd[0].get("ESTADO").toString().equals("B"))
                    {System.err.println("eto nya 2");
                    parameters.put("dias", 0);}
                    else
                    {
                        System.err.println("eto nya 3");
                        parameters.put("dias", 15);
                    }
                }
            }
            catch (RemoteException e){System.out.println(e);
            }
            catch (ServiceException e){ System.out.println(e);}
        }
        urlImage=t1.getClass().getResource("/umsa/capricornio/gui/images/umsa.jpg");
        
        
        try {
            
            System.out.println("el cod_trans_nro es: "+cod_trans_nro);
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            String ubi_rpa = puerto.getDatosGenerales2(cod_almacen)[0].get("FIRMA_RPA").toString();
            String jefe_adqui = puerto.getDatosGenerales2(cod_almacen)[0].get("JADQUI").toString();
            String rpa = puerto.getDatosGenerales2(cod_almacen)[0].get("RPA").toString();
            parameters.put("jefe_adqui", jefe_adqui);
            parameters.put("rpa", rpa);
             String ubi_usr;
            if (cod_tramite==2){
                ubi_usr = puerto.getFirmaUsuario(cod_trans_nro)[0].get("FIRMA").toString();
            }
            else{
                ubi_usr = puerto.getFirmaUsuario2(cod_trans_nro)[0].get("FIRMA").toString();
            }
                
            
            //        firma_rpa=t1.getClass().getResource("/umsa/capricornio/gui/images/firma_MonicaDiaz.jpg");
            //firma_rpa=t1.getClass().getResource("/../../../firmas/2015-rpa-3.jpg");
            
            if(ubi_rpa.trim().length()!=0){
                firma_rpa = new URL("http://200.7.160.182"+ubi_rpa);
                parameters.put("firma_rpa",firma_rpa.toString());
            }
            
            if(ubi_usr.trim().length()!=0){
                firma_usr = new URL("http://200.7.160.182"+ubi_usr);
                parameters.put("firma_usr",firma_usr.toString());
            }
            
  //        parameters.put("firma2",firma2.toString());
            
            
            
            
//            firma_rpa= new URL("http://200.7.160.25/firmas/2015-rpa-3.jpg");
//        firma_rpa=t1.getClass().getResource("");
//        firma_rpa = "http://200.7.160.25/prueba/";
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepTransaccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception e){
            
        }
        System.out.println("----->>>>  Wujuuu la ruta de la firma es: "+firma_rpa);
        System.out.println("----->>>>  Wujuuu la ruta de la otra firma es: "+firma_usr);

        // Recuperamos el fichero fuente el xml para la compilacion interna
        /*File rep = new File(urlMaestro.getFile());
        JasperDesign jd=JRXmlLoader.load(rep)); 
        JasperReport report = JasperCompileManager.compileReport(jd);  
        JasperPrint masterPrint = null;
        try { masterPrint = JasperFillManager.fillReport(report,parameters, ds); } 
        catch (JRException e) { }              
        JasperViewer.viewReport(masterPrint, false);*/
        
        JRBeanCollectionDataSource ds =new JRBeanCollectionDataSource(aux);  
                               
        JasperReport masterReport = null;
        try { masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);} 
        catch (JRException e) 
            { System.out.println("Error cargando el reporte maestro: " + e.getMessage());
              System.exit(3);
            }
        
        parameters.put("imagen",urlImage.toString());
        
        parameters.put("titulo",titulo);
        //parameters.put("titulo",titulo);
        parameters.put("usuario",usuariox);

        JasperPrint masterPrint = null;
        try {
            masterPrint = JasperFillManager.fillReport(masterReport, parameters,ds);
        }
        catch (JRException e) {}  
        
        //JasperViewer.viewReport(masterPrint, false);
        
        JFrame jframe = new JFrame();
        //jpm=JasperFillManager.fillReport(reporte, param,conexion);
        ///Con este códgo llamo a mi clase Rportevista 
        ReporteVista jrViewer = new ReporteVista(masterPrint); 
        jrViewer.setGuardarEnabled(true,masterPrint);
        jframe.add(jrViewer);
        //jframe.setLocation(frmSACI_maecfngimprDlg.intlocat ionX,frmSACI_maecfngimprDlg.intlocationY);
        jframe.setTitle("REPOTE");
        //jframe.setIconImage(icono.getImage());    
        //jframe.setSize(frmSACI_maecfngimprDlg.intTamanoX,f rmSACI_maecfngimprDlg.intTamanoY);
        jframe.setSize(1100,900);
        jframe.setVisible(true);
    }
    public void Reporte (List aux,String titulo,int cod_tramite,int cod_trans_nro,int cod_almacen)
    {
        this.usuariox = this.generaUsuario(cod_trans_nro);
        System.out.println("Generando Reporte, del tipo --> "+titulo);
        RepTransaccion t1 = new RepTransaccion(); 
        Map parameters = new HashMap();
        if (cod_tramite==1){
            String vec[]=titulo.split("#");
            int a=Integer.parseInt(vec[0]);
            String lug=vec[1];
            String cod_transaccion=vec[2];
            if(lug.equals("") || lug==null)
            {
                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/SolicitudCompra.jasper");
            }
            else
            {
                if(a==1)
                {
                    urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/NuevoSC.jasper"); 
                }
                else
                {
                    urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/NuevoSCA.jasper"); 
                }
            }
            List list1=new ArrayList();
            try{
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();
                //System.out.println("cod_trans: "+Integer.parseInt(cod_transaccion)+" estado: "+cod_estado+" cod_Tramite cod: "+1);
                Map[] datos=puerto.getOtrosDoc(Integer.parseInt(cod_transaccion));
                if(datos==null)
                {
                    System.out.println("esta vacio rayos");
                    parameters.put("sindatos2", "NO EXISTEN OTROS DOCUMENTOS");
                }
                else{
                    for(int q=0;q<datos.length;q++)
                    {
                        Transaccion trans1 = new Transaccion();
                        trans1.setDescripcion_(datos[q].get("DESCRIPCION").toString());
                        list1.add(trans1);
                    }
                    parameters.put("ds2", list1);
                }
            }catch(Exception e)
            {
                System.err.println("errorrrrrrrrrrr "+e.getMessage());
            }
            List list2=new ArrayList();
            try{
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();
                //System.out.println("cod_trans: "+Integer.parseInt(cod_transaccion)+" estado: "+cod_estado+" cod_Tramite cod: "+1);
                Map[] datos=puerto.getOtrosDoc1(Integer.parseInt(cod_transaccion));
                if(datos==null)
                {
                    System.out.println("esta vacio rayos");
                    parameters.put("sindatos1", "NO EXISTEN OTRAS PROFORMAS");
                }
                else{
                    for(int q=0;q<datos.length;q++)
                    {
                        Transaccion trans1 = new Transaccion();
                        trans1.setEmpresa(datos[q].get("DESCRIPCION").toString());
                        if(datos[q].get("TERMINOS_REF").toString().equals(""))
                        {
                            
                        }
                        else
                        {
                            trans1.setProforma("X");
                        }
                        
                        trans1.setFec_proforma(datos[q].get("FECHA_DOC").toString());
                        list2.add(trans1);
                    }
                    parameters.put("ds3", list2);
                }
            }catch(Exception e)
            {
                System.err.println("errorrrrrrrrrrr2 "+e.getMessage());
            }
            //urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/SolicitudCompra.jasper"); 
            //System.out.println("Entro a la opción 1 y urlmaestro es: "+urlMaestro);
            /*try{ AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();                  
                Map[] datos= puerto.getTotal(cod_trans_nro);
                if (datos!=null)
                    parameters.put("TxtTotal",TotalTexto(datos[0].get("TOTAL").toString()));
                else
                    System.out.println("upsi Vacio¡¡¡");
            }
            catch (RemoteException e){System.out.println(e);
            }
            catch (ServiceException e){ System.out.println(e);}*/
        }
        if (cod_tramite==2){
            String vec[]=titulo.split("#");
            if(vec[1].equals("1"))
            {
                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/NuevoOC.jasper");
            }
            else
            {
                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/OrdenCompra.jasper");
            }
            this.usuariox=usuario;
                //urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/OrdenCompra.jasper");
            try{ AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();                  
                Map[] datos= puerto.getTotal(cod_trans_nro);
                if (datos!=null){
                    //System.out.println("............................ El total es: "+datos[0].get("TOTAL").toString());
                    //System.out.println("............................ El total redondeado es: "+this.Redondear(datos[0].get("TOTAL").toString(),2));
                    parameters.put("TxtTotal",TotalTexto(this.Redondear(datos[0].get("TOTAL").toString(),2)));
                }
                System.out.println(cod_trans_nro);
                Map[] datos1= puerto.getDias(cod_trans_nro);
                if (!datos1[0].get("DIAS").toString().equals("")){
                    System.out.println(datos1[0].get("DIAS").toString());
                    //System.out.println("............................ El total es: "+datos[0].get("TOTAL").toString());
                    //System.out.println("............................ El total redondeado es: "+this.Redondear(datos[0].get("TOTAL").toString(),2));
                    int tt=Integer.parseInt(datos1[0].get("DIAS").toString());
                    String HABILES=datos1[0].get("HABILES").toString();
                    System.out.println("asdasdasdasdasdasd "+HABILES);
                    System.err.println("eto nya "+tt);
                    parameters.put("dias", tt);
                    if(HABILES.equals(""))
                    {
                        parameters.put("habiles","dias calendario");
                    }
                    else
                    {
                        parameters.put("habiles",HABILES);
                    }
                    
                }
                else
                { 
                    Map[] dd=puerto.getEst(cod_trans_nro);
                    if(dd[0].get("ESTADO").toString().equals("PPTO") || dd[0].get("ESTADO").toString().equals("ADQ") || dd[0].get("ESTADO").toString().equals("B"))
                    {System.err.println("eto nya 2");
                    parameters.put("dias", 0);}
                    else
                    {
                        System.err.println("eto nya 3");
                        parameters.put("dias", 15);
                    }
                }
            }
            catch (RemoteException e){System.out.println(e);
            }
            catch (ServiceException e){ System.out.println(e);}
        }
        if (cod_tramite==3)
            urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/IngresoMaterial.jasper");
        if (cod_tramite==4)
            urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/PedidoMateriales.jasper");
        urlImage=t1.getClass().getResource("/umsa/capricornio/gui/images/umsa.jpg");
        
        firma2=t1.getClass().getResource("/umsa/capricornio/gui/images/liliana.jpg");
        try {
            
            System.out.println("el cod_trans_nro es: "+cod_trans_nro);
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            String ubi_rpa = puerto.getDatosGenerales2(cod_almacen)[0].get("FIRMA_RPA").toString();
            String jefe_adqui = puerto.getDatosGenerales2(cod_almacen)[0].get("JADQUI").toString();
            String rpa = puerto.getDatosGenerales2(cod_almacen)[0].get("RPA").toString();
            parameters.put("jefe_adqui", jefe_adqui);
            parameters.put("rpa", rpa);
             String ubi_usr;
            if (cod_tramite==2){
                ubi_usr = puerto.getFirmaUsuario(cod_trans_nro)[0].get("FIRMA").toString();
            }
            else{
                ubi_usr = puerto.getFirmaUsuario2(cod_trans_nro)[0].get("FIRMA").toString();
            }
                
            
            //        firma_rpa=t1.getClass().getResource("/umsa/capricornio/gui/images/firma_MonicaDiaz.jpg");
            //firma_rpa=t1.getClass().getResource("/../../../firmas/2015-rpa-3.jpg");
            
            if(ubi_rpa.trim().length()!=0){
                firma_rpa = new URL("http://200.7.160.182"+ubi_rpa);
                parameters.put("firma_rpa",firma_rpa.toString());
            }
            
            if(ubi_usr.trim().length()!=0){
                firma_usr = new URL("http://200.7.160.182"+ubi_usr);
                parameters.put("firma_usr",firma_usr.toString());
            }
            
  //        parameters.put("firma2",firma2.toString());
            
            
            
            
//            firma_rpa= new URL("http://200.7.160.25/firmas/2015-rpa-3.jpg");
//        firma_rpa=t1.getClass().getResource("");
//        firma_rpa = "http://200.7.160.25/prueba/";
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepTransaccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception e){
            
        }
        
        System.out.println("----->>>>  Wujuuu la ruta de la firma es: "+firma_rpa);
        System.out.println("----->>>>  Wujuuu la ruta de la otra firma es: "+firma_usr);

        // Recuperamos el fichero fuente el xml para la compilacion interna
        /*File rep = new File(urlMaestro.getFile());
        JasperDesign jd=JRXmlLoader.load(rep)); 
        JasperReport report = JasperCompileManager.compileReport(jd);  
        JasperPrint masterPrint = null;
        try { masterPrint = JasperFillManager.fillReport(report,parameters, ds); } 
        catch (JRException e) { }              
        JasperViewer.viewReport(masterPrint, false);*/
        
        JRBeanCollectionDataSource ds =new JRBeanCollectionDataSource(aux);  
                               
        JasperReport masterReport = null;
        System.out.println("es el urlmaestro "+urlMaestro);
        try { 
            masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);
        } 
        catch (JRException e) 
            { System.out.println("Error cargando el reporte maestro: " + e.getMessage());
              System.exit(3);
            }
        
        parameters.put("imagen",urlImage.toString());
        
        parameters.put("titulo",titulo);
        //parameters.put("titulo",titulo);
        parameters.put("usuario",usuariox);

        JasperPrint masterPrint = null;
        try { masterPrint = JasperFillManager.fillReport(masterReport, parameters,ds);}
        catch (JRException e) {}  
        
        //JasperViewer.viewReport(masterPrint, false);  
        JFrame jframe = new JFrame();
        //jpm=JasperFillManager.fillReport(reporte, param,conexion);
        ///Con este códgo llamo a mi clase Rportevista 
        ReporteVista jrViewer = new ReporteVista(masterPrint); 
        jrViewer.setGuardarEnabled(true,masterPrint);
        jframe.add(jrViewer);
        //jframe.setLocation(frmSACI_maecfngimprDlg.intlocat ionX,frmSACI_maecfngimprDlg.intlocationY);
        jframe.setTitle("REPOTE");
        //jframe.setIconImage(icono.getImage());
        //jframe.setSize(frmSACI_maecfngimprDlg.intTamanoX,f rmSACI_maecfngimprDlg.intTamanoY);
        jframe.setSize(1100,900);
        jframe.setVisible(true);
    }
    public String Redondear(String numero, int pow)
    {
           Double num_deci = Math.pow(10, pow);
           return String.valueOf(Math.rint(Double.parseDouble(numero)*num_deci)/num_deci);
    }
    String TotalTexto(String total){       
       double m=Double.parseDouble(total);                          
                       
        long valor =(long)m;
        double var= m-valor;
        
        DecimalFormat formato_decimal = new DecimalFormat("0.00");        
        String decimal = formato_decimal.format(var);
      
        String montoLetra = NumerosTextuales.NumTextuales(valor);
        
        if ((m>=1000 && m<2000) || (m>=1000000 && m<2000000)){ montoLetra="UN "+montoLetra;}        
        if (var ==0.0) montoLetra=montoLetra+" 00/100";
        else montoLetra=montoLetra+" "+decimal.substring(2, 4)+"/100";
       return montoLetra;
   }
}
