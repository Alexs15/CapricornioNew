/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.gui.transacciones.Adquisiciones.tramites.reporte;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author julian
 */
public class RepModalidad {     
      
    URL urlMaestro,urlImage;
    public void Reporte (List aux,String fec_ini,String fec_fin)
    {          
        RepModalidad t1 = new RepModalidad(); 
        Map parameters = new HashMap();
        
        urlMaestro = t1.getClass().getResource("/umsa/capricornio/gui/reports/Modalidades.jasper");        
        urlImage=t1.getClass().getResource("/umsa/capricornio/gui/images/umsa.jpg");

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
        parameters.put("fec_ini",fec_ini);     
        parameters.put("fec_fin",fec_fin);     

        JasperPrint masterPrint = null;
        try { masterPrint = JasperFillManager.fillReport(masterReport, parameters,ds);}
        catch (JRException e) {}  
        
        JasperViewer.viewReport(masterPrint, false);  
    }
    
}
