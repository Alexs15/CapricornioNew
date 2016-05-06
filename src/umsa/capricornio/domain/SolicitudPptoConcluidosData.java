/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class SolicitudPptoConcluidosData
{ public String  m_sol_ppto;  
  public String  m_hoja_ruta;  
  public String  m_detalle;
  public String  m_monto;
  public String  m_apert_prog;
  public String  m_fuente;        

  public SolicitudPptoConcluidosData(String sol_ppto, String hoja_ruta,String detalle,String monto,String apert_prog,String fuente) {
    m_sol_ppto=sol_ppto;    
    m_hoja_ruta=hoja_ruta;    
    m_detalle=detalle;
    m_monto=monto;
    m_apert_prog=apert_prog;
    m_fuente=fuente;
  }
   public SolicitudPptoConcluidosData() {
    m_sol_ppto="";    
    m_hoja_ruta="";    
    m_detalle="";
    m_monto="";
    m_apert_prog="";
    m_fuente="";
  }
}