/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author UMSA-JES
 */
public class Preventivo 
{
    public String  m_programa;  
    public String  m_proyecto;  
    public String  m_actividad;
    public String  m_partida;
    public String  m_importe;
    public String  m_preventivo;
    public String  m_total;
    public String  m_resumen;
    //  public String  m_fuente;        
    public Preventivo(String m_programa, String m_proyecto, String m_actividad, String m_partida, String m_importe, String m_preventivo, String m_total,String m_resumen) {
        this.m_programa = m_programa;
        this.m_proyecto = m_proyecto;
        this.m_actividad = m_actividad;
        this.m_partida = m_partida;
        this.m_importe = m_importe;
        this.m_preventivo = m_preventivo;
        this.m_total = m_total;
        this.m_resumen = m_resumen;
    }
  
   public Preventivo() {
    m_programa="";    
    m_proyecto="";    
    m_actividad="";
    m_partida="";
    m_importe="";
    m_preventivo = "";
    m_total = "";
    m_resumen="";
//    m_fuente="";
  }
}
