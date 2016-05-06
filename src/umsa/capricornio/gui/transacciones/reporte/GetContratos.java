/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.reporte;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
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
public class GetContratos {
    
    URL urlMaestro,urlImage;
    String envia,dns,dnp,destino,num_resolucion,monto_ppto,dir_daf;
    public void arma(int cod_transaccion){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            //System.out.println("Para cod_transaccion: "+cod_transaccion);
            //System.out.println("El cod_transaccion --> "+cod_transaccion+" y el cod_w: "+cod_w);
            Map[] datos = puerto.getResIni(cod_transaccion);
            dir_daf = puerto.getDatoGeneral("DIR_DAF");
            //System.out.println("Dir Daf es: "+dir_daf);
            if(datos!=null){
                
                envia=datos[0].get("ENVIA").toString();
                dns=datos[0].get("DETALLE_NOTA_SOLICITUD").toString();
                dnp=datos[0].get("DETALLE_NOTA_PRESUPUESTO").toString();
                destino=datos[0].get("DESTINO").toString();
                int cod_res_ini=Integer.parseInt(datos[0].get("COD_RES_INI").toString());
                num_resolucion=datos[0].get("NUM_RESOLUCION").toString();
                monto_ppto=datos[0].get("MONTO_PPTO").toString();
                
//                datos=puerto.getDatosGenerales();
//                String dir_daf = datos[0].get("DIR_DAF").toString();
//                System.out.println("Dir Daf es: "+dir_daf);
                
                //this.setModal(false);
                //this.genera_reportes.Reporte(cod_transaccion,this.cod_w,num_resolucion,this.detalle,monto_ppto,envia,dns,dnp,destino,dir_daf);
                //this.setVisible(false);
                
            }
        } catch (Exception e) {
        }
    }
    public void Reporte (
            String a, 
            String b, 
            String c,
            String d,
            String e,
            String f,
            String g,
            String h,
            String i,
            String j,
            String k,
            String l,
            String m,
            String n,
            String o,
            String p,
            String q,
            String r,
            String s,
            String t,
            String u,
            String v,
            String w,
            String x,
            String y,
            String z,
            String a1,
            String b1
    )
    {
        
        Transaccion trans = new Transaccion();
        trans.setDetalle("Transporte y deposito de remesas de las cajas del Departamento de Tesoro Universitario");
//        this.arma(cod_transaccion);
        //System.out.println("Probando --> "+prueba);
        RepTransaccion t1 = new RepTransaccion(); 
        Map parameters = new HashMap();
        parameters.put("razon_social", a);
        parameters.put("nit", b);
        parameters.put("loc_ent_trib", c);
        parameters.put("dist_prov_dep", d);
        parameters.put("mae", e);
        parameters.put("cargo_aut_firma", f);
        parameters.put("ci_mae", g);
        parameters.put("nrs_ci", h);
        parameters.put("dom_entidad", i);
        parameters.put("obj_ctr", j);
        parameters.put("bien", k);
        parameters.put("tipo_contrato", l);
        parameters.put("fecha", m);
        parameters.put("cuce_proc", n);
        parameters.put("ev_cc", o);
        parameters.put("desc_bienes", p);
        parameters.put("ob_entidad", q);
        parameters.put("tipo_garantia", r);
        parameters.put("rs_entidad", s);
        parameters.put("mnt_contrato", t);
        parameters.put("num_pln", u);
        parameters.put("plazo_efecto", v);
        
//        parameters.put("plazo_efecto", w);
        
        parameters.put("lugar_entrega_bien", x);
        parameters.put("responsable_recepcion", y);
        parameters.put("monto_multa", z);
        
        parameters.put("func_firma", a1);
        parameters.put("proveedor", b1);
        //String NotaS = "DPTO.T.U. Nº115/14";
        //String NotaP = "Depto.Ppto.Nº404/14";
        //String Num_Resol = "16/2014";
        //String Num_Resol = "16/2014";
        String Fecha = "04  de Febrero de 2014";
        //String Envia = "Jefe del Departamento de Tesoro Universitario";
        //String Titulo_trans = "Transporte y deposito de remesas de las cajas del Departamento de Tesoro Universitario";
//        String Monto = this.TotalTexto(this.monto_ppto.replace(",", "."));
//        String Monto = null;
        
        
//        parameters.put("NotaS", this.dns);
//        parameters.put("NotaP", this.dnp);
//        parameters.put("Num_Resol", this.num_resolucion);
//        parameters.put("Fecha", Fecha);
//        parameters.put("Envia", this.envia);
//        parameters.put("Titulo_trans", Detalle);
////        parameters.put("Monto", Monto);
//        parameters.put("DIR_DAF", this.dir_daf);
//        
//        parameters.put("Destino", this.destino);
        
//        System.out.println("El cod_w es --> "+cod_w);
        //urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReIniServ.jasper");
        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelocontrato.jasper");
//        switch(cod_w){
////            case 6:
////                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReIniServ.jasper");
////                break;
////            case 1:
////                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReIniBien.jasper");
////                break;
////            case 3:
////                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReIniConsu.jasper");
////                break;
////            case 4:
////                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/ReIniObra.jasper");
////                break;
//            case 7:
//                System.out.println("Entro al 7");
//                urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/modelocontrato.jasper");
//                break;
//        }
        
        //urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/IngresoMaterial.jasper");
        
         //JRBeanCollectionDataSource ds =new JRBeanCollectionDataSource(aux);  
                               
        JasperReport masterReport = null;
        try {
            System.out.println("El urlMaestro es --> "+urlMaestro);
            masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);
        } 
        catch (JRException ex) 
            { System.out.println("Error cargando el reporte maestro: " + ex.getMessage());
              System.exit(3);
            }
        
        //parameters.put("imagen",urlImage.toString());
        //parameters.put("titulo",titulo);        

        JasperPrint masterPrint = null;
        try {
//            System.out.println("Probando de numero a literal --> "+this.TotalTexto("1000000"));
            System.out.println("Esta vivo weon¡¡");
            masterPrint = JasperFillManager.fillReport(masterReport, parameters, new JREmptyDataSource());
        }
        catch (JRException ex) {}  
        
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
   }
   /*public static void main(String arg[]) {
       System.out.println("Wolasss");
       GetResoluciones x = new  GetResoluciones();
       x.Reporte("DPTO.T.U. Nº115/14", "Depto.Ppto.Nº404/14", "Jefe del Departamento de Tesoro Universitario","Detalle","sads",4,0);
       System.out.println("Wolass2");
   }*/
}
